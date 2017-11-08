package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAwalDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAwal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/registrasi")
public class RegistrasiAwalController {
    @Autowired
    private RegistrasiAwalDao ra;

    @RequestMapping(value = "/formregistrasiawal", method = RequestMethod.GET)
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new RegistrasiAwal());
    }

    @RequestMapping(value = "/formregistrasiawal", method = RequestMethod.POST)
    public String prosesForm(@Valid RegistrasiAwal p, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/formregistrasiawal";
        }
        ra.save(p);
        return "redirect:formregistrasiawal";

    }

}
