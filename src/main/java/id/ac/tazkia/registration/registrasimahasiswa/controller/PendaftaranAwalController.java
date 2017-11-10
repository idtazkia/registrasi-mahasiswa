package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAwalDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAwal;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Sekolah;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PendaftaranAwalController {
    @Autowired
    private RegistrasiAwalDao pd;

    @RequestMapping("/pendaftaran_awal/list")
    public void daftarPendaftaran(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarPendaftaran", pd.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarPendaftaran", pd.findAll(page));
        }
    }
}
