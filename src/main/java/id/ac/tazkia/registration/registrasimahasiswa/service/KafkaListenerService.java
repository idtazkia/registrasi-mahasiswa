package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturResponse;
import id.ac.tazkia.registration.registrasimahasiswa.dto.PembayaranTagihan;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanResponse;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;

@Service @Transactional
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);

    @Value("${tagihan.id.registrasi}") private String idTagihanRegistrasi;
    @Value("${tagihan.id.daftarUlang}") private String idTagihanDaftarUlang;

    @Autowired private ObjectMapper objectMapper;
    @Autowired private TagihanService tagihanService;
    @Autowired private RegistrasiService registrasiService;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private DetailPendaftarDao detailPendaftarDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private PembayaranDao pembayaranDao;
    @Autowired private NotifikasiService notifikasiService;

    @KafkaListener(topics = "${kafka.topic.debitur.response}", group = "${spring.kafka.consumer.group-id}")
    public void handleDebiturResponse(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            DebiturResponse response = objectMapper.readValue(message, DebiturResponse.class);
            if (!response.getSukses()) {
                LOGGER.warn("Create debitur gagal : {}", response.getData());
                return;
            }
            Pendaftar p = pendaftarDao.findByNomorRegistrasi(response.getNomorDebitur());
            if (p == null) {
                LOGGER.warn("Pendaftar dengan nomor registrasi {} tidak ditemukan", response.getNomorDebitur());
                return;
            }
            tagihanService.createTagihanRegistrasi(p);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.response}", group = "${spring.kafka.consumer.group-id}")
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

            insertTagihanRegistrasi(response);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.payment}", group = "${spring.kafka.consumer.group-id}")
    public void handlePayment(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            PembayaranTagihan pt = objectMapper.readValue(message, PembayaranTagihan.class);

            Tagihan tagihan = tagihanDao.findByNomorTagihan(pt.getNomorTagihan());
            if (tagihan == null) {
                LOGGER.warn("Tagihan dengan nomor {} tidak ditemukan", pt.getNomorTagihan());
                return;
            }

            tagihanService.prosesPembayaran(tagihan, pt);

            if (tagihan.getJenisBiaya().getId() == AppConstants.JENIS_BIAYA_DAFTAR_ULANG){
                DetailPendaftar dp = detailPendaftarDao.findByPendaftar(tagihan.getPendaftar());
                if(dp == null){
                    LOGGER.warn("Tagihan dengan nomor {} tidak memiliki data detail pendaftar", pt.getNomorTagihan());
                    return;
                }
                notifikasiService.kirimNotifikasiKeteranganLulus(dp);
            }else {
                registrasiService.aktivasiUser(tagihan.getPendaftar());
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

}
