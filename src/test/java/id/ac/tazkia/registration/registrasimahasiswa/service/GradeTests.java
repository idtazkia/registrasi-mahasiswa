package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeTests {
    @Autowired private RegistrasiService registrasiService;

    @Test
    public void hitungGrade(){
        Grade hasil = registrasiService.hitungGrade(new BigDecimal(80));
        Assert.assertNotNull("Grade tidak boleh null", hasil);
        System.out.println(hasil.getNama());
    }
}
