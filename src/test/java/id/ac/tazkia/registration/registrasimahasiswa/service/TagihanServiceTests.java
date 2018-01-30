package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagihanServiceTests {

    @Autowired private TagihanService tagihanService;

    @Test
    public void testBiayaPendaftaranProdi(){
        Pendaftar p = new Pendaftar();
        ProgramStudi ps = new ProgramStudi();
        ps.setId("007");
        p.setProgramStudi(ps);
        BigDecimal pendaftaran = new BigDecimal("500000.00");
        Assert.assertEquals(pendaftaran, tagihanService.hitungBiayaPendaftaran(p));

        Pendaftar p2 = new Pendaftar();
        ProgramStudi ps2 = new ProgramStudi();
        ps2.setId("001");
        p2.setProgramStudi(ps2);
        BigDecimal pendaftaran2 = new BigDecimal("300000.00");
        Assert.assertEquals(pendaftaran2, tagihanService.hitungBiayaPendaftaran(p2));

    }
}
