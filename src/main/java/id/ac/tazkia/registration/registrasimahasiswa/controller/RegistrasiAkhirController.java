package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAkhirDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class RegistrasiAkhirController {

    @Autowired
    private RegistrasiAkhirDao ra;

    @RequestMapping(value = "/registrasi/detail_pendaftaran/form", method = RequestMethod.GET)
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new DetailPendaftar());
    }

    @RequestMapping(value = "/registrasi/detail_pendaftaran/form", method = RequestMethod.POST)
    public String prosesForm(@Valid DetailPendaftar p, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/detail_pendaftaran/form";
        }
        ra.save(p);
        return "redirect:/selesai";

    }


}
