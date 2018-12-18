package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.ProgramStudiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
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
public class ProgramStudiController {
    @Autowired
    private ProgramStudiDao sd;


    //Hapus Data
    @RequestMapping("/programstudi/hapus")
    public  String hapus(@RequestParam("id") ProgramStudi id ){
        sd.delete(id);
        return "redirect:list";
    }
    //

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
    //



    //tampikan form
    @RequestMapping(value = "/programstudi/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("prodi", new ProgramStudi());

        if (id != null && !id.isEmpty()){
            ProgramStudi p= sd.findById(id).get();
            if (p != null){
                m.addAttribute("prodi", p);
            }
        }
        return "/programstudi/form";
    }
    ////
    //simpan
    @RequestMapping(value = "/programstudi/form", method = RequestMethod.POST)
    public String prosesForm(@Valid ProgramStudi p, BindingResult errors){
        if(errors.hasErrors()){
            return "/programstudi/form";
        }
        sd.save(p);
        return "redirect:list";
    }
    ////
}
