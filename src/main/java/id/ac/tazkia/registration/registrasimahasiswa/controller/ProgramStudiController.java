package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.ProgramStudiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProgramStudiController {
    @Autowired
    private ProgramStudiDao sd;

    //list programstudi
    @RequestMapping("/programstudi/list")
    public void  daftarProgram(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarProgram", sd.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarProgram", sd.findAll(page));
        }
    }
}
