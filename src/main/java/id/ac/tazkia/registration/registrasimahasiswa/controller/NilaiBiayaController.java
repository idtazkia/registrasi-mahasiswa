package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.NilaiBiayaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NilaiBiayaController {
    @Autowired private NilaiBiayaDao nb;

    @GetMapping("/biaya/nilai/list")
    public void daftarNilaiBiaya(Model m){
        m.addAttribute("daftarNilai", nb.findAll());
    }
}
