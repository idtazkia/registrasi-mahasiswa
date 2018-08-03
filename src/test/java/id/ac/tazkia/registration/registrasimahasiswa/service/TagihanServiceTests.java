package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TagihanServiceTests {

    @Autowired private TagihanService tagihanService;

//    Date input = new Date();
//    Instant instant = input.toInstant();
//    ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
//    LocalDate date = zdt.toLocalDate();

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
//Test Biaya Daftar Ulang
    @Test
    public void testBiayaDaftarUlang() throws ParseException {
        Pendaftar p = new Pendaftar();
        ProgramStudi ps = new ProgramStudi();
        HasilTest h = new HasilTest();
        Grade g= new Grade();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalTest = formatter.parse("2018-01-01");
        LocalDate date = tanggalTest.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        ps.setId("002");
        p.setProgramStudi(ps);
        h.setTanggalTest(tanggalTest);
        g.setId("001");
        h.setGrade(g);

        System.out.println("Tanggal Test :"+date);
        BigDecimal daftarUlang = new BigDecimal("6800000.00");
        Assert.assertEquals(daftarUlang, tagihanService.hitungBiayaDaftarUlang(p,h,date));
        System.out.printf("biaya : "+ daftarUlang);


    }

//Test Biaya Daftar Ulang Diskon UP
    @Test
    public void testBiayaDaftarUlangDiskonUp() throws ParseException {
        Pendaftar p = new Pendaftar();
        ProgramStudi ps = new ProgramStudi();
        HasilTest h = new HasilTest();
        Grade g= new Grade();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalTest = formatter.parse("2018-08-01");
        LocalDate date = tanggalTest.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        ps.setId("001");
        p.setProgramStudi(ps);
        h.setTanggalTest(tanggalTest);
        g.setId("001");
        h.setGrade(g);

        System.out.println("Tanggal Test :"+date);
        BigDecimal daftarUlang = new BigDecimal("8300000.00");
        Assert.assertEquals(daftarUlang, tagihanService.hitungBiayaDUdiskonUp(p,h,date));
        System.out.printf("biaya : "+ daftarUlang);


    }
}
