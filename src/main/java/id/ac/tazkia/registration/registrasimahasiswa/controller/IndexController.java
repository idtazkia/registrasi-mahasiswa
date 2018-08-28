package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PeriodeDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private PeriodeDao  periodeDao;

    @Autowired
    private HasilTestDao hasilTestDao;

    @Value("classpath:sample/pei.pdf")
    private Resource brosurPei;

    @Value("classpath:sample/mua.pdf")
    private Resource brosurMua;

    @Value("classpath:sample/ai.pdf")
    private Resource brosurAi;

    @Value("classpath:sample/bmi.pdf")
    private Resource brosurBmi;

    @Value("classpath:sample/ei.pdf")
    private Resource brosurEi;

    @Value("classpath:sample/FA L_O Isi Book all Tazkia.pdf")
    private Resource bukuEi;

    @GetMapping("/")
    public String defaultPage(@RequestParam(required = false)String search,String cari, Model m, @Qualifier("tpa")Pageable tpaPage, @Qualifier("smart") Pageable smartPage){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            List<JenisTest> jpaTpa = Arrays.asList(JenisTest.JPA, JenisTest.TPA);
            m.addAttribute("daftarHasil", hasilTestDao.cariByJenisTestTpaJPaDanPendaftar(jpaTpa, "%"+search.toLowerCase()+"%", tpaPage));
            System.out.println("hasil pencarian : "+ search);
        } else {
            m.addAttribute("daftarHasil", hasilTestDao.findByJenisTestIn(tpaPage, JenisTest.TPA, JenisTest.JPA));
        }

        if(StringUtils.hasText(cari)) {
            m.addAttribute("cari", cari);
            List<JenisTest> smartTest = Arrays.asList(JenisTest.SMART_TEST);
            m.addAttribute("daftarSmart", hasilTestDao.cariByJenisTestSmartTestDanPendaftar(smartTest, "%"+cari.toLowerCase()+"%", smartPage));
        } else {
            m.addAttribute("daftarSmart", hasilTestDao.findByJenisTestIn(smartPage, JenisTest.SMART_TEST));
        }
        return "index";
    }



    @GetMapping("/brosur/pei")
    public void downloadBrosurPei(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Pendidikan Ekonomi Syariah. pdf");
        FileCopyUtils.copy(brosurPei.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @GetMapping("/brosur/mua")
    public void downloadBrosurMua(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Hukum Ekonomi Syariah. pdf");
        FileCopyUtils.copy(brosurMua.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @GetMapping("/brosur/ai")
    public void downloadBrosurAi(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Akuntansi Syariah. pdf");
        FileCopyUtils.copy(brosurAi.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @GetMapping("/brosur/bmi")
    public void downloadBrosurBmi(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Manajemen Bisnis Syariah. pdf");
        FileCopyUtils.copy(brosurBmi.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @GetMapping("/brosur/ei")
    public void downloadBrosurEi(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Ilmu Ekonomi Syariah. pdf");
        FileCopyUtils.copy(brosurEi.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @GetMapping("/buku/ei")
    public void downloadBukuEi(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=FA L_O Isi Book all Tazkia.pdf. pdf");
        FileCopyUtils.copy(bukuEi.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }


    @ModelAttribute("daftarPeriode")
    public Iterable<Periode> daftarPeriode(){
        return periodeDao.findAll();
    }

}
