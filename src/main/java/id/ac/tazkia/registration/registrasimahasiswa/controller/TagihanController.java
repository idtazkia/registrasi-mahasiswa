package id.ac.tazkia.registration.registrasimahasiswa.controller;

import com.sun.org.apache.bcel.internal.generic.NEW;
import id.ac.tazkia.registration.registrasimahasiswa.dao.JenisBiayaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class TagihanController {


    @Autowired
    private PendaftarDao pendaftarDao;
    @Autowired
    private TagihanDao tagihanDao;
    @Autowired
    private JenisBiayaDao jb;

    @ModelAttribute("daftarjenisBiaya")
    public Iterable<JenisBiaya> daftarjenisBiaya(){
        return jb.findAll();
    }


    @GetMapping("/api/pendaftar")
    @ResponseBody
    public Page<Pendaftar> findByName(@RequestParam(required = false) String nomorRegistrasi, Pageable page) {
        if (!StringUtils.hasText(nomorRegistrasi)) {
            return pendaftarDao.findAll(page);
        }
        return pendaftarDao.findByNomorRegistrasiContainingIgnoreCaseOrderByNomorRegistrasi(nomorRegistrasi, page);
    }

    @GetMapping("/api/pendaftar/{pendaftar}/tagihan")
    @ResponseBody
    public List<Tagihan> findByPendaftar(@PathVariable String pendaftar) {
        return tagihanDao.findByPendaftarOrderByTanggalTagihan(pendaftarDao.findOne(pendaftar));
    }

    @RequestMapping("/tagihan/list")
    public ModelMap findByTagihanPendaftar(@RequestParam Pendaftar pendaftar, Pageable page){
        ModelMap mm = new ModelMap();
        mm.addAttribute("daftarTagihan",tagihanDao.findByPendaftarOrderByTanggalTagihan(pendaftar, page));
        mm.addAttribute("pendaftar", pendaftar);
        return mm;



    }


    @RequestMapping("/biaya/tagihan/list")
    public void listTagihan() {
    }

//    @RequestMapping("/biaya/tagihan/form")
//    public void formTagihan() {
//    }

    @RequestMapping(value = "/biaya/tagihan/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("tagihan", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findOne(id);
            if (p != null){
                m.addAttribute("tagihan", p);
            }
        }
        return "/biaya/tagihan/form";
    }

    @RequestMapping(value = "/biaya/tagihan/form", method = RequestMethod.POST)
    public String prosesForm(@Valid Tagihan t, BindingResult errors){
        if(errors.hasErrors()){
            return "/biaya/tagihan/form";
        }
        tagihanDao.save(t);
        return "redirect:list";
    }

}
