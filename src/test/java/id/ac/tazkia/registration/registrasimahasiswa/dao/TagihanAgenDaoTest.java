package id.ac.tazkia.registration.registrasimahasiswa.dao;


import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.StatusTagihan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagihanAgenDaoTest {

    @Autowired private PendaftarAgenDao pendaftarAgenDao;

    @Test
    public void tagihanTest(){
        Agen a = new Agen();
        a.setId("0e5642d8-421a-4c53-9a16-b45755f6d7a3");
        LocalDateTime mulai = LocalDateTime.of(2018,4,1,0,0,0);
        LocalDateTime sampai = LocalDateTime.now();
        Long jumlahPendaftar = pendaftarAgenDao.jumlahTagihanAgen(a, mulai, sampai, StatusTagihan.BELUM_DITAGIH);
        BigDecimal nilaiTagihan =
                new BigDecimal(jumlahPendaftar)
                        .multiply(BigDecimal.valueOf(200000));
        System.out.println("Agen :" + a.getNamaCabang() );
        System.out.println("Jumlah Pendaftar : "+ jumlahPendaftar);
        System.out.println("Nilai Tagihan :" + nilaiTagihan);
        }
}
