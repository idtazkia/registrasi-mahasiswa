package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrasiDetailController {

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;

    @GetMapping("/registrasi/detail/list")
    public void daftarPendaftaran(Model m){
        m.addAttribute("daftarPendaftaranakhir", detailPendaftarDao.findAll());
    }


    @GetMapping(value = "/registrasi/detail/form")
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new DetailPendaftar());
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
