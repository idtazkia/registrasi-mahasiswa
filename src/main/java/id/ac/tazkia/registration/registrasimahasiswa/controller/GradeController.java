package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.GradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GradeController {
    @Autowired
    private GradeDao go;

    @RequestMapping("/grade/list")
    public void daftarGrade(Model m){
        m.addAttribute("daftarGrade", go.findAll());
    }
}
