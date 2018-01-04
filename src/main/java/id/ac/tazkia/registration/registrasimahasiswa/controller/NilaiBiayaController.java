package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.NilaiBiayaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NilaiBiayaController {
    @Autowired private NilaiBiayaDao nb;

    @GetMapping("/biaya/nilai/list")
    public void daftarNilaiBiaya(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarNilai", nb.findByJenisBiayaContainingIgnoreCaseOrderByJenisBiaya(nama, page));
        } else {
            m.addAttribute("daftarNilai", nb.findAll(page));
        }
    }
}
