package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturResponse;
import id.ac.tazkia.registration.registrasimahasiswa.dto.PembayaranTagihan;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanResponse;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service @Transactional
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    private static final List<String> JENIS_TAGIHAN_SPMB = new ArrayList<>();

    @Value("${tagihan.id.registrasi}") private String idTagihanRegistrasi;
    @Value("${tagihan.id.daftarUlang}") private String idTagihanDaftarUlang;
    @Value("${tagihan.id.agen}") private String idTagihanAgen;

    @Autowired private ObjectMapper objectMapper;
    @Autowired private TagihanService tagihanService;
    @Autowired private RegistrasiService registrasiService;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private DetailPendaftarDao detailPendaftarDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private PembayaranDao pembayaranDao;
    @Autowired private NotifikasiService notifikasiService;
    @Autowired private AgenDao agenDao;
    @Autowired private TagihanAgenDao tagihanAgenDao;
    @Autowired private ProgramStudiDao programStudiDao;

    @PostConstruct
    public void inisialisasi(){
        JENIS_TAGIHAN_SPMB.add(idTagihanRegistrasi);
        JENIS_TAGIHAN_SPMB.add(idTagihanDaftarUlang);
    }

    @KafkaListener(topics = "${kafka.topic.debitur.response}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleDebiturResponse(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            DebiturResponse response = objectMapper.readValue(message, DebiturResponse.class);
            if (!response.getSukses()) {
                LOGGER.warn("Create debitur gagal : {}", response.getData());
                return;
            }

            String nomorDebitur = response.getNomorDebitur();
            if (nomorDebitur.startsWith(String.valueOf(9))) {
                // create tagihan agen
                Agen a = agenDao.findByKode(response.getNomorDebitur());
                tagihanService.createTagihanAgen(a);
            } else {
                Pendaftar p = pendaftarDao.findByNomorRegistrasi(response.getNomorDebitur());
                if (p == null) {
                    LOGGER.warn("Pendaftar dengan nomor registrasi {} tidak ditemukan", response.getNomorDebitur());
                    return;
                } else if (!AppConstants.PENDAFTAR_AGEN.equals(p.getPemberiRekomendasi())) {
                    tagihanService.createTagihanRegistrasi(p);
                }
            }
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.response}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleTagihanResponse(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            TagihanResponse response = objectMapper.readValue(message, TagihanResponse.class);
            if (!response.getSukses()) {
                LOGGER.warn("Create tagihan gagal : {}", response.getDebitur());
                return;
            }

            LOGGER.debug("Create tagihan untuk pendaftar {} sukses dengan nomor {}",
                    response.getDebitur(), response.getNomorTagihan());

            if (response.getDebitur().startsWith(String.valueOf(9))) {
                insertTagihanAgen(response);
            } else {
                insertTagihanRegistrasi(response);
            }
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.payment}", groupId = "${spring.kafka.consumer.group-id}")
    public void handlePayment(String message) {
        try {
            PembayaranTagihan pt = objectMapper.readValue(message, PembayaranTagihan.class);
            if (!JENIS_TAGIHAN_SPMB.contains(pt.getJenisTagihan())) {
                LOGGER.debug("Bukan Tagihan SPMB");
                return;
            }
            LOGGER.debug("Terima message : {}", message);

            Tagihan tagihan = tagihanDao.findByNomorTagihan(pt.getNomorTagihan());

            TagihanAgen tagihanAgen = tagihanAgenDao.findByNomorTagihan(pt.getNomorTagihan());

            if (tagihan == null) {
                LOGGER.warn("Tagihan Pendaftar tidak ditemukan");

                //Cek di Tagihan Agen
                if (tagihanAgen == null){
                    LOGGER.warn("Tagihan Agen dengan nomor {} tidak ditemukan", pt.getNomorTagihan());
                }

                tagihanService.prosesPembayaranAgen(tagihanAgen, pt);
                return;

            }
//Cek Prodi
            ProgramStudi pr07 = programStudiDao.findById("007").get();
            ProgramStudi pr09 = programStudiDao.findById("009").get();
            ProgramStudi pr08 = programStudiDao.findById("008").get();
            Boolean pen1 = tagihan.getPendaftar().getProgramStudi().equals(pr07);
            Boolean pen2 = tagihan.getPendaftar().getProgramStudi().equals(pr08);
            Boolean pen3 = tagihan.getPendaftar().getProgramStudi().equals(pr09);
            Boolean cekPodi = pen1 == true || pen2 == true || pen3 == true;
                if (tagihanAgen == null) {
                    if (tagihan == null){
                        LOGGER.warn("Tagihan Pendaftar dengan nomor {} tidak ditemukan", pt.getNomorTagihan());
                    }
                    tagihanService.prosesPembayaran(tagihan, pt);

                    System.out.println("jenis tagihan : " + tagihan.getJenisBiaya().getId());

                    if (tagihan.getJenisBiaya().getId().equals(AppConstants.JENIS_BIAYA_DAFTAR_ULANG)) {
                        DetailPendaftar dp = detailPendaftarDao.findByPendaftar(tagihan.getPendaftar());
                        if (dp == null) {
                            LOGGER.warn("Tagihan dengan nomor {} tidak memiliki data detail pendaftar", pt.getNomorTagihan());
                            return;
                        }
//suketPasca
                        if (cekPodi == true) {
                            LOGGER.debug("Program Studi Pasca :" + tagihan.getPendaftar().getProgramStudi().getNama());
                            notifikasiService.kirimNotifikasiKeteranganLulusPasca(dp);
                        }else{
//suketS1
                            LOGGER.debug("Program Studi S1  :" + tagihan.getPendaftar().getProgramStudi().getNama());
                            notifikasiService.kirimNotifikasiKeteranganLulus(dp);
                        }
                    } else {
                        registrasiService.aktivasiUser(tagihan.getPendaftar());
                    }
                }

        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }


    private void insertTagihanRegistrasi(TagihanResponse tagihanResponse) {
        Pendaftar pendaftar = pendaftarDao.findByNomorRegistrasi(tagihanResponse.getDebitur());
        if (pendaftar == null) {
            LOGGER.warn("Pendaftar dengan nomor registrasi {} tidak ditemukan", tagihanResponse.getDebitur());
        }

        Tagihan tagihan = new Tagihan();
        tagihan.setPendaftar(pendaftar);
        tagihan.setNomorTagihan(tagihanResponse.getNomorTagihan());
        tagihan.setLunas(false);
        tagihan.setTanggalTagihan(tagihanResponse.getTanggalTagihan().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tagihan.setNilai(tagihanResponse.getNilaiTagihan());
        tagihan.setKeterangan(tagihanResponse.getKeterangan());

        JenisBiaya jenisBiaya = new JenisBiaya();
        if(idTagihanRegistrasi.equals(tagihanResponse.getJenisTagihan())) {
            jenisBiaya.setId(AppConstants.JENIS_BIAYA_PENDAFTARAN);
        } else if(idTagihanDaftarUlang.equals(tagihanResponse.getJenisTagihan())){
            jenisBiaya.setId(AppConstants.JENIS_BIAYA_DAFTAR_ULANG);
        } else {
            LOGGER.error("Jenis Biaya {} belum terdaftar", tagihanResponse.getJenisTagihan());
            return;
        }
        tagihan.setJenisBiaya(jenisBiaya);

        tagihanDao.save(tagihan);
    }

    private void insertTagihanAgen(TagihanResponse tagihanResponse) {
        Agen agen = agenDao.findByKode(tagihanResponse.getDebitur());
        if (agen == null) {
            LOGGER.warn("Agen dengan Kode {} tidak ditemukan", tagihanResponse.getDebitur());
        }

        TagihanAgen tagihanAgen = new TagihanAgen();
        tagihanAgen.setAgen(agen);
        tagihanAgen.setNomorTagihan(tagihanResponse.getNomorTagihan());
        tagihanAgen.setLunas(false);
        tagihanAgen.setTanggalTagihan(tagihanResponse.getTanggalTagihan().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tagihanAgen.setNilai(tagihanResponse.getNilaiTagihan());
        tagihanAgen.setKeterangan(tagihanResponse.getKeterangan());

        JenisBiaya jenisBiaya = new JenisBiaya();
        jenisBiaya.setId(AppConstants.JENIS_BIAYA_TAGIHAN_AGEN);

        tagihanAgen.setJenisBiaya(jenisBiaya);
        tagihanAgenDao.save(tagihanAgen);

    }


}
