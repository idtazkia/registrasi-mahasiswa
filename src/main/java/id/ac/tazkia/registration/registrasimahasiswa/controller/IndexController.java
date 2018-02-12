package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private HasilTestDao hasilTestDao;

    @GetMapping("/")
    public String defaultPage(@RequestParam(required = false)String search,String cari, Model m, @Qualifier("tpa")Pageable tpaPage, @Qualifier("smart") Pageable smartPage){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            List<JenisTest> jpaTpa = Arrays.asList(JenisTest.JPA, JenisTest.TPA);
            m.addAttribute("daftarHasil", hasilTestDao.findByJenisTestInOrPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(jpaTpa, search,search, tpaPage));
        } else {
            m.addAttribute("daftarHasil", hasilTestDao.findByJenisTestIn(tpaPage, JenisTest.TPA, JenisTest.JPA));
        }

        if(StringUtils.hasText(cari)) {
            m.addAttribute("cari", cari);
            List<JenisTest> smartTest = Arrays.asList(JenisTest.SMART_TEST);
            m.addAttribute("daftarSmart", hasilTestDao.findByJenisTestInOrPendaftarNamaContainingOrPendaftarNamaAsalSekolahContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(smartTest,cari,cari, smartPage));
        } else {
            m.addAttribute("daftarSmart", hasilTestDao.findByJenisTestIn(smartPage, JenisTest.SMART_TEST));
        }
        return "index";
    }

}
