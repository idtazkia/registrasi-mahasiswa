package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.NilaiBiayaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturRequest;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanRequest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.NilaiBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service @Transactional
public class TagihanService {

    @Value("${tagihan.id.registrasi}") private String idTagihanRegistrasi;

    @Autowired private NilaiBiayaDao nilaiBiayaDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private KafkaSender kafkaSender;

    private JenisBiaya pendaftaran;

    public TagihanService(){
        pendaftaran = new JenisBiaya();
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
        BigDecimal kodeUnik = new BigDecimal(p.getNomorRegistrasi().substring(p.getNomorRegistrasi().length() - 3));
        BigDecimal biayaPendaftaran = hitungBiayaPendaftaran();
        BigDecimal tagihanPendaftaran = biayaPendaftaran.add(kodeUnik);

        TagihanRequest tagihanRequest = TagihanRequest.builder()
                .jenisTagihan(idTagihanRegistrasi)
                .nilaiTagihan(tagihanPendaftaran)
                .debitur(p.getNomorRegistrasi())
                .keterangan("Pembayaran Registrasi Mahasiswa Baru STEI Tazkia 2018")
                .tanggalJatuhTempo(Date.from(LocalDate.now().plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();

        kafkaSender.requestCreateTagihan(tagihanRequest);
    }

    public BigDecimal hitungBiayaPendaftaran(){
        Page<NilaiBiaya> biaya = nilaiBiayaDao.findByJenisBiaya(pendaftaran, new PageRequest(0,1));
        if(!biaya.hasContent()){
            return BigDecimal.ZERO;
        }
        return biaya.getContent().get(0).getNilai();
    }
}
