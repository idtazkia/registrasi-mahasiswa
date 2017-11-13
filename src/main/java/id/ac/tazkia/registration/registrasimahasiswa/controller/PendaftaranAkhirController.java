package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAkhirDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PendaftaranAkhirController {
    @Autowired
    private RegistrasiAkhirDao pd;

    @RequestMapping("/pendaftaran_akhir/list")
    public void daftarPendaftaran(Model m){
        m.addAttribute("daftarPendaftaranakhir", pd.findAll());
    }

    @RequestMapping("/selesai")
    public  void  selesai(){
        
    }
}
