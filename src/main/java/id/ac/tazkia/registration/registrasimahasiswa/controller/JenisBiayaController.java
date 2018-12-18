package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.JenisBiayaDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
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
public class JenisBiayaController {
    @Autowired
    private JenisBiayaDao jb;

    @RequestMapping("/biaya/jenis/list")
    public void daftarJenisBiaya(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarBiaya", jb.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarBiaya", jb.findAll(page));
        }
    }

//Hapus Data
    @RequestMapping("/biaya/jenis/hapus")
    public  String hapus(@RequestParam("id") JenisBiaya id ){
        jb.delete(id);
        return "redirect:list";
    }
//

//tampikan form
    @RequestMapping(value = "/biaya/jenis/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("jenis", new JenisBiaya());

        if (id != null && !id.isEmpty()){
            JenisBiaya p= jb.findById(id).get();
            if (p != null){
                m.addAttribute("jenis", p);
            }
        }
        return "/biaya/jenis/form";
    }
////
//simpan
    @RequestMapping(value = "/biaya/jenis/form", method = RequestMethod.POST)
    public String prosesForm(@Valid JenisBiaya p, BindingResult errors){
        if(errors.hasErrors()){
            return "/biaya/jenis/form";
        }
        jb.save(p);
        return "redirect:list";
    }

////
}
