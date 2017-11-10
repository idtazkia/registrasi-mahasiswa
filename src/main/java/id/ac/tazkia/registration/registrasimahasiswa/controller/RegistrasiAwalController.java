package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAwalDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAwal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class RegistrasiAwalController {
    @Autowired
    private RegistrasiAwalDao ra;


    @RequestMapping(value = "/registrasi/pendaftar", method = RequestMethod.GET)
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new RegistrasiAwal());
    }

    @RequestMapping(value = "/registrasi/pendaftar", method = RequestMethod.POST)
    public String prosesForm(@Valid RegistrasiAwal p, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/pendaftar";
        }
        ra.save(p);
        return "redirect:/selesai";

    }



}

