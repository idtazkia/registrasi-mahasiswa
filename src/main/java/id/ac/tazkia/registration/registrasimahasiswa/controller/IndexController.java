package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private HasilTestDao hasilTestDao;

    @GetMapping("/")
    public String defaultPage(@RequestParam(required = false)String search, Model m, Pageable page){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarHasil", hasilTestDao.findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(search,search, page));
        } else {
            m.addAttribute("daftarHasil", hasilTestDao.findAll(page));
        }
        return "index";
    }

}
