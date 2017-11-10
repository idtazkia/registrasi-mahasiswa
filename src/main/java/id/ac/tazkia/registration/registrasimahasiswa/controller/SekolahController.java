package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.SekolahDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Sekolah;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SekolahController {
    @Autowired
    private SekolahDao sd;

////
    @GetMapping("/api/sekolah")
    @ResponseBody
    public Page<Sekolah> findByName(@RequestParam(required = false) String nama, Pageable page){
        if(!StringUtils.hasText(nama)) {
            return sd.findAll(page);
        }
        return sd.findByNamaContainingIgnoreCaseOrderByNama(nama, page);
    }


//list sekolah
    @RequestMapping("/sekolah/list")
    public void  daftarSekolah(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarSekolah", sd.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarSekolah", sd.findAll(page));
        }

    }
///
//tampikan form
    @RequestMapping(value = "/sekolah/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("sekolah", new Sekolah());

        if (id != null && !id.isEmpty()){
            Sekolah p= sd.findOne(id);
            if (p != null){
                m.addAttribute("sekolah", p);
            }
        }
        return "/sekolah/form";
    }
////
//simpan
    @RequestMapping(value = "/sekolah/form", method = RequestMethod.POST)
    public String prosesForm(@Valid Sekolah p, BindingResult errors){
        if(errors.hasErrors()){
            return "/sekolah/form";
        }
        sd.save(p);
        return "redirect:list";
    }
////

}
