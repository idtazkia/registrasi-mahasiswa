package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RegistrasiDetail;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class RegistrasiDetailController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private UserDao userDao;

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;

    @GetMapping("/registrasi/detail/list")
    public void daftarPendaftaran(Model m){
        m.addAttribute("daftarPendaftaranakhir", detailPendaftarDao.findAll());
    }


    @GetMapping(value = "/registrasi/detail/form")
    public void tampilkanForm(Model model, Authentication currentUser){
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

        RegistrasiDetail detail = new RegistrasiDetail();
        detail.setNama(p.getNama());
        detail.setPendaftar(p.getId());
        detail.setEmail(p.getEmail());
        detail.setAsalSekolah(p.getNamaAsalSekolah());
        detail.setKabupatenKota(p.getKabupatenKota().getNama());
        model.addAttribute("registrasi", detail);

        DetailPendaftar d = detailPendaftarDao.findByPendaftar(p);
        logger.debug("Nomor Registrasi :"+ p.getNomorRegistrasi());
        if (d != null){
            model.addAttribute("detail", d);
        }
    }

    @PostMapping(value = "/registrasi/detail/form")
    public String prosesForm(@ModelAttribute @Valid DetailPendaftar p, BindingResult errors){
        if(errors.hasErrors()){
            logger.debug("Error Validasi Form : {}", errors.toString());
            return "/registrasi/detail/form";
        }
        detailPendaftarDao.save(p);
        return "redirect:/home";

    }


//VIEW DETAIL PENDAFTAR

    @GetMapping("/registrasi/detail/view")
    public void  viewDetail(@RequestParam(value = "id",required = false)String idPendaftar, Model m, Pageable page){
        if(StringUtils.hasText(idPendaftar)) {
            m.addAttribute("pendaftar", idPendaftar);
            Pendaftar p = pendaftarDao.findOne(idPendaftar);
            m.addAttribute("view", detailPendaftarDao.findByPendaftar(p));
        } else {
            m.addAttribute("view", detailPendaftarDao.findAll(page));
        }
    }

}
