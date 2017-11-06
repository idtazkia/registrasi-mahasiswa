package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAkhirDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAkhir;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAwal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/registrasi")
public class RegistrasiAkhirController {

    @Autowired
    private RegistrasiAkhirDao ra;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new RegistrasiAkhir());

        if (id != null && !id.isEmpty()){
            RegistrasiAkhir p= ra.findOne(id);
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "/registrasi/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String prosesForm(@Valid RegistrasiAkhir p, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/form";
        }
        ra.save(p);
        return "redirect:form";

    }


    @GetMapping("/home")
    public void home(){}

}
