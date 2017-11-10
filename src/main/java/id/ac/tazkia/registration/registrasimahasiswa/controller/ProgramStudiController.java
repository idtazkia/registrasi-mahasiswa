package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.ProgramStudiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ProgramStudiController {
    @Autowired
    private ProgramStudiDao sd;

    //list programstudi
    @RequestMapping("/programstudi/list")
    public void  daftarProgram(Model m){
        m.addAttribute("daftarProgram", sd.findAll());

    }

}
