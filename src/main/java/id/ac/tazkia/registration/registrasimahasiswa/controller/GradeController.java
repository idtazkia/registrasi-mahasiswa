package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.GradeDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class GradeController {
    @Autowired
    private GradeDao go;

//list
    @RequestMapping("/grade/list")
    public void daftarGrade(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarGrade", go.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarGrade", go.findAll(page));
        }
    }
//
//Hapus Data
@RequestMapping("/grade/hapus")
    public  String hapus(@RequestParam("id") Grade id ){
        go.delete(id);
        return "redirect:list";
    }
//
//tampikan form
    @RequestMapping(value = "/grade/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("grade", new Grade());

        if (id != null && !id.isEmpty()){
            Grade p= go.findById(id).get();
            if (p != null){
                m.addAttribute("grade", p);
            }
        }
        return "grade/form";
    }
////

//simpan
    @RequestMapping(value = "/grade/form", method = RequestMethod.POST)
    public String prosesForm(@Valid Grade p, BindingResult errors){
        if(errors.hasErrors()){
            return "/grade/form";
        }
        go.save(p);
        return "redirect:list";
    }

////
}
