package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.NilaiBiayaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturRequest;
import id.ac.tazkia.registration.registrasimahasiswa.dto.PembayaranTagihan;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanRequest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service @Transactional
public class TagihanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagihanService.class);
    private static final DateTimeFormatter FORMATTER_ISO_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${tagihan.id.registrasi}") private String idTagihanRegistrasi;

    @Autowired private NilaiBiayaDao nilaiBiayaDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private PembayaranDao pembayaranDao;
    @Autowired private KafkaSender kafkaSender;

    private JenisBiaya pendaftaran;
    private ProgramStudi programStudi;

    public TagihanService(){
        pendaftaran = new JenisBiaya();
        programStudi = new ProgramStudi();
        pendaftaran.setId(AppConstants.JENIS_BIAYA_PENDAFTARAN);
    }

    public void prosesTagihanPendaftaran(Pendaftar p){
        DebiturRequest request = DebiturRequest.builder()
                .email(p.getEmail())
                .nama(p.getNama())
                .noHp(p.getNoHp())
                .nomorDebitur(p.getNomorRegistrasi())
                .build();

        kafkaSender.requestCreateDebitur(request);

    }

    public void createTagihanRegistrasi(Pendaftar p) {
        TagihanRequest tagihanRequest = TagihanRequest.builder()
                .jenisTagihan(idTagihanRegistrasi)
                .nilaiTagihan(hitungBiayaPendaftaran(p))
                .debitur(p.getNomorRegistrasi())
                .keterangan("Pembayaran Registrasi Mahasiswa Baru STEI Tazkia 2018")
                .tanggalJatuhTempo(Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        kafkaSender.requestCreateTagihan(tagihanRequest);

    }

    public void prosesPembayaran(Tagihan tagihan, PembayaranTagihan pt) {
        tagihan.setLunas(true);

        Pembayaran pembayaran = new Pembayaran();
        pembayaran.setTagihan(tagihan);
        pembayaran.setNilai(pt.getNilaiPembayaran());
        pembayaran.setWaktuPembayaran(LocalDateTime.parse(pt.getWaktuPembayaran(), FORMATTER_ISO_DATE_TIME));

        Bank bank = new Bank();
        bank.setId(pt.getBank());
        pembayaran.setBank(bank);
        pembayaran.setBuktiPembayaran(pt.getReferensiPembayaran());

        tagihanDao.save(tagihan);
        pembayaranDao.save(pembayaran);
        LOGGER.debug("Pembayaran untuk tagihan {} berhasil disimpan", pt.getNomorTagihan());

    }

    public BigDecimal hitungBiayaPendaftaran(Pendaftar p){
        p.getProgramStudi();
        Page<NilaiBiaya> biaya = nilaiBiayaDao.findByJenisBiayaAndProgramStudi(pendaftaran, p.getProgramStudi(), new PageRequest(0,1));
        if(!biaya.hasContent()){
            return BigDecimal.ZERO;
        }
        return biaya.getContent().get(0).getNilai();
    }
}
