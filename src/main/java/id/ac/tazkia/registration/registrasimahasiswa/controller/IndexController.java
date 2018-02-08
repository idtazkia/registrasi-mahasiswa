package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.SmartTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private HasilTestDao hasilTestDao;
    @Autowired
    private SmartTestDao smartTestDao;

    @GetMapping("/")
    public String defaultPage(@RequestParam(required = false)String search,String cari, Model m, @Qualifier("tpa")Pageable tpa, @Qualifier("smart") Pageable smart){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarHasil", hasilTestDao.findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(search,search, tpa));
            m.addAttribute("daftarSmart", smartTestDao.findByNamaContainingOrSekolahContainingIgnoreCaseOrderByNama(search,search, smart));
        } else {
            m.addAttribute("daftarHasil", hasilTestDao.findAll(tpa));
            m.addAttribute("daftarSmart", smartTestDao.findAll(smart));
        }
        if(StringUtils.hasText(cari)) {
            m.addAttribute("cari", cari);
            m.addAttribute("daftarSmart", smartTestDao.findByNamaContainingOrSekolahContainingIgnoreCaseOrderByNama(cari,cari, smart));
        } else {
            m.addAttribute("daftarSmart", smartTestDao.findAll(smart));
        }
        return "index";
    }

}
