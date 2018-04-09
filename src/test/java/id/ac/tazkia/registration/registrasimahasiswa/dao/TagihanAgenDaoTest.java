package id.ac.tazkia.registration.registrasimahasiswa.dao;


import id.ac.tazkia.registration.registrasimahasiswa.dto.RekapTagihanAgen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagihanAgenDaoTest {

    @Autowired private PendaftarAgenDao pendaftarAgenDao;

    @Test
    public void tagihanTest(){
        Agen a = new Agen();
        a.setId("a154eabe-9c95-43a2-adc1-46818d1c6f1a");
        LocalDateTime mulai = LocalDateTime.of(2018,4,1,0,0,0);
        LocalDateTime sampai = LocalDateTime.now();
        List<RekapTagihanAgen> hasil = pendaftarAgenDao.rekapTagihan(a, mulai, sampai);
        for (RekapTagihanAgen r : hasil) {
            System.out.println("Tanggal : "+r.getTanggal());
            System.out.println("Jumlah : "+r.getJumlah());
            System.out.println("Nilai : "+r.getNilai());
        }
    }
}
