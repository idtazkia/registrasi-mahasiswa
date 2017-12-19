package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResetPasswordController {

    @Autowired  private RegistrasiService registrasiService;

    @RequestMapping(value = "/registrasi/reset/form", method = RequestMethod.GET)
    public void resetForm(@RequestParam(required = false)Pendaftar pendaftar, ModelMap m) {
        m.addAttribute("pendaftar", pendaftar);

    }

    @PostMapping(value = "/registrasi/reset/form")
    public String prosesForm(@RequestParam Pendaftar pendaftar, RedirectAttributes redirectAttributes){
        String passwordBaru = registrasiService.resetPassword(pendaftar);
        redirectAttributes.addFlashAttribute("pendaftar", pendaftar);
        redirectAttributes.addFlashAttribute("passwordBaru", passwordBaru);
        return "redirect:selesai";
    }

    @GetMapping("/registrasi/reset/selesai")
    public void selesaiReset(){ }
}

