package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAwalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PendaftaranAwalController {
    @Autowired
    private RegistrasiAwalDao pd;

    @RequestMapping("/pendaftaran_awal/list")
    public void daftarPendaftaran(Model m){
        m.addAttribute("daftarPendaftaran", pd.findAll());
    }
}
