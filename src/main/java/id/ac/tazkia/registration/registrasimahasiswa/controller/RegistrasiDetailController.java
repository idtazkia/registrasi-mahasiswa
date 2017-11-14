package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrasiDetailController {

    @Autowired private PendaftarDao pendaftarDao;

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;

    @GetMapping("/registrasi/detail/list")
    public void daftarPendaftaran(Model m){
        m.addAttribute("daftarPendaftaranakhir", detailPendaftarDao.findAll());
    }


    @GetMapping(value = "/registrasi/{id}/form")
    public void tampilkanForm(Model model, @PathVariable String id){
        Pendaftar p = pendaftarDao.findOne(id);
        DetailPendaftar dp = new DetailPendaftar();
        dp.setPendaftar(p);
        model.addAttribute("registrasi", dp);
    }

    @PostMapping(value = "/registrasi/detail/form")
    public String prosesForm(@Valid DetailPendaftar p, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/detail/form";
        }
        detailPendaftarDao.save(p);
        return "redirect:/selesai";

    }
}
