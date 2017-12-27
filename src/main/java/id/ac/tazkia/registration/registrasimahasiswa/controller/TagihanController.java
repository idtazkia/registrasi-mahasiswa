package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.JenisBiayaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired
    private UserDao userDao;
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


//Tagihan Pendaftar

    @RequestMapping("/registrasi/tagihan/list")
    public void tagihanPendaftar(ModelMap model, Authentication currentUser){
        logger.debug("Authentication class : {}",currentUser.getClass().getName());

        if(currentUser == null){
            logger.warn("Current user is null");
            return;
        }

        String username = ((UserDetails)currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        logger.debug("User ID : {}", u.getId());
        if(u == null){
            logger.warn("Username {} not found in database ", username);
            return;
        }

        Pendaftar p = pendaftarDao.findByUser(u);
        logger.debug("Nama Pendaftar : "+p.getNama());
        if(p == null){
            logger.warn("Pendaftar not found for username {} ", username);
            return;
        }

        model.addAttribute("tagihan",tagihanDao.findByPendaftarOrderByTanggalTagihan(p));
    }
}
