package id.ac.tazkia.registration.registrasimahasiswa.service;

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
    public void testBiayaPendaftaran(){
        BigDecimal pendaftaran = new BigDecimal("300000.00");
        Assert.assertEquals(pendaftaran, tagihanService.hitungBiayaPendaftaran());
    }
}
