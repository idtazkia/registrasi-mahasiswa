package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.AgenDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.service.AgenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AgenController {
    @Autowired private AgenDao agenDao;
    @Autowired private AgenService agenService;

    //tampikan form
    @RequestMapping(value = "/agenHumas/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("agen", new Agen());

        if (id != null && !id.isEmpty()){
            Agen p= agenDao.findOne(id);
            if (p != null){
                m.addAttribute("agen", p);
            }
        }
        return "/agenHumas/form";
    }
////

    @PostMapping("/agenHumas/form")
    public String formAgen(@ModelAttribute @Valid AgenDto agenDto,@RequestParam String password,@RequestParam String username ,BindingResult errors){

        if(errors.hasErrors()){
            return "agenHumas/form";
        }

        agenService.prosesPendaftaranAgen(agenDto, password, username);

        return "redirect:/home";
    }
}
