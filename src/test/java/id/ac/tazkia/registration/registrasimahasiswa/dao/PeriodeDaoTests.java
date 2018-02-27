package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PeriodeDaoTests {

    @Autowired private PeriodeDao periodeDao;

    @Test
    public void testCariPeriodeByTanggalTest() throws  Exception{
        LocalDate tanggalPeriode1 = LocalDate.parse("2018-01-01");
        LocalDate tanggalPeriodeInvalid = LocalDate.parse("2019-01-01");

        Assert.assertTrue(periodeDao.cariPeriodeUntukTanggal(tanggalPeriodeInvalid).isEmpty());
        List<Periode> daftarPeriode1 = periodeDao.cariPeriodeUntukTanggal(tanggalPeriode1);
        Assert.assertFalse(daftarPeriode1.isEmpty());
        Assert.assertTrue(daftarPeriode1.size() == 1);
        Periode p1 = daftarPeriode1.get(0);
        Assert.assertEquals("Batch 1 2017-2018", p1.getNama());

    }


}
