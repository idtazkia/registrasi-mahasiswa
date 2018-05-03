package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service @Transactional
public class TagihanService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagihanService.class);
    private static final DateTimeFormatter FORMATTER_ISO_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${tagihan.id.registrasi}") private String idTagihanRegistrasi;
    @Value("${tagihan.id.daftarUlang}") private String idTagihanDaftarUlang;
    @Value("${tagihan.id.agen}") private String idTagihanAgen;
    @Value("${nilai.tagihan.agen}") private BigDecimal nilaiTagihanAgen;


    @Autowired private NilaiBiayaDao nilaiBiayaDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private PembayaranDao pembayaranDao;
    @Autowired private KafkaSender kafkaSender;
    @Autowired private PeriodeDao periodeDao;
    @Autowired private AgenDao agenDao;
    @Autowired private PendaftarAgenDao pendaftarAgenDao;
    @Autowired private PembayaranAgenDao pembayaranAgenDao;
    @Autowired private TagihanAgenDao tagihanAgenDao;

    private JenisBiaya pendaftaran;
    private JenisBiaya daftarUlang;
    private ProgramStudi programStudi;

    public TagihanService(){
        pendaftaran = new JenisBiaya();
        programStudi = new ProgramStudi();
        pendaftaran.setId(AppConstants.JENIS_BIAYA_PENDAFTARAN);

//DaftarUlang
        daftarUlang = new JenisBiaya();
        daftarUlang.setId(AppConstants.JENIS_BIAYA_DAFTAR_ULANG);
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
                .kodeBiaya(p.getProgramStudi().getKodeBiaya())
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


    public void createTagihanDaftarUlang(Pendaftar p, HasilTest h,@RequestParam (required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tanggalTest){

        TagihanRequest tagihanRequest = TagihanRequest.builder()

                .jenisTagihan(idTagihanDaftarUlang)
                .nilaiTagihan(hitungBiayaDaftarUlang(p, h, tanggalTest))
                .debitur(p.getNomorRegistrasi())
                .keterangan("Pembayaran Daftar Ulang Mahasiswa Baru STEI Tazkia 2018")
                .tanggalJatuhTempo(Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        kafkaSender.requestCreateTagihan(tagihanRequest);

    }


    public BigDecimal hitungBiayaDaftarUlang(Pendaftar p, HasilTest h,
                                             @RequestParam (required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tanggalTest){
        List<Periode> daftarPeriode = periodeDao.cariPeriodeUntukTanggal(tanggalTest);

        p.getProgramStudi();
        Page<NilaiBiaya> biaya = nilaiBiayaDao.findByJenisBiayaAndProgramStudiAndGradeAndPeriode(daftarUlang, p.getProgramStudi(), h.getGrade(),daftarPeriode, new PageRequest(0,1));
        if(!biaya.hasContent()){
            return BigDecimal.ZERO;
        }
        return biaya.getContent().get(0).getNilai();
    }

    @Scheduled(cron = "${jadwal.tagihan.agen}")
    public void prosesTagihanAgen() {
        Iterable<Agen> semuaAgen = agenDao.findAll();
        for (Agen agen : semuaAgen) {
            LOGGER.debug("Memproses tagihan agen {}", agen.getNamaCabang());
            createDebitur(agen);
        }
    }

    public void createDebitur(Agen agen){
        DebiturRequest request = DebiturRequest.builder()
                .email(agen.getEmail())
                .nama(agen.getNamaCabang())
                .noHp(agen.getNoHp())
                .nomorDebitur(agen.getKode())
                .build();
        kafkaSender.requestCreateDebitur(request);

    }


    public void createTagihanAgen(Agen agen) {
        LocalDateTime sampai = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime mulai = sampai.minusDays(7);

        Long jumlahPendaftar = pendaftarAgenDao.jumlahTagihanAgen(agen, mulai, sampai, StatusTagihan.BELUM_DITAGIH);
        System.out.println("Jumlah pendaftar ="+ jumlahPendaftar);

        if (jumlahPendaftar == null){
         LOGGER.debug("Belum ada pendaftar untuk agen {}" + agen.getNamaCabang());
        }else {
            BigDecimal nilaiTagihan =
                    new BigDecimal(jumlahPendaftar)
                            .multiply(nilaiTagihanAgen);

            TagihanRequest tagihanRequest = TagihanRequest.builder()
                    .jenisTagihan(idTagihanAgen)
                    .nilaiTagihan(nilaiTagihan)
                    .debitur(agen.getKode())
                    .keterangan("Pembayaran Registrasi Mahasiswa Baru STEI Tazkia 2018 Via Agen Pendaftar")
                    .tanggalJatuhTempo(Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .build();

            kafkaSender.requestCreateTagihan(tagihanRequest);

            pendaftarAgenDao.updateStatusTagihan(agen, mulai, sampai, StatusTagihan.BELUM_DIBAYAR);
        }
    }

    public void prosesPembayaranAgen(TagihanAgen tagihanAgen, PembayaranTagihan pt) {

        LocalDateTime sampai = tagihanAgen.getTanggalTagihan().atStartOfDay();
        LocalDateTime mulai = sampai.minusDays(7);

        tagihanAgen.setLunas(true);

        PembayaranAgen pembayaranAgen = new PembayaranAgen();
        pembayaranAgen.setTagihanAgen(tagihanAgen);
        pembayaranAgen.setNilai(pt.getNilaiPembayaran());
        pembayaranAgen.setWaktuPembayaran(LocalDateTime.parse(pt.getWaktuPembayaran(), FORMATTER_ISO_DATE_TIME));

        Bank bank = new Bank();
        bank.setId(pt.getBank());
        pembayaranAgen.setBank(bank);
        pembayaranAgen.setBuktiPembayaran(pt.getReferensiPembayaran());

        tagihanAgenDao.save(tagihanAgen);
        pembayaranAgenDao.save(pembayaranAgen);
        pendaftarAgenDao.updateStatusTagihan(tagihanAgen.getAgen(), mulai, sampai, StatusTagihan.LUNAS);

        LOGGER.debug("Pembayaran untuk Tagihan Agen {} berhasil disimpan", pt.getNomorTagihan());

    }
    

}
