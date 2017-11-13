package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.JenisBiayaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JenisBiayaController {
    @Autowired
    private JenisBiayaDao jb;

    @RequestMapping("/jenis_biaya/list")
    public void daftarJenisBiaya(Model m){
        m.addAttribute("daftarBiaya", jb.findAll());
    }

}
