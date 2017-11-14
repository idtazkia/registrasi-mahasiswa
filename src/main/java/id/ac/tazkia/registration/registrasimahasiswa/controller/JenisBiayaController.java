package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.JenisBiayaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JenisBiayaController {
    @Autowired
    private JenisBiayaDao jb;

    @RequestMapping("/biaya/jenis/list")
    public void daftarJenisBiaya(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarBiaya", jb.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarBiaya", jb.findAll(page));
        }
    }

}
