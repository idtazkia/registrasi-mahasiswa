package id.ac.tazkia.registration.registrasimahasiswa.controller.agen;

import id.ac.tazkia.registration.registrasimahasiswa.controller.RegistrasiDetailController;
import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanAgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagihanAgenController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired private TagihanAgenDao tagihanAgenDao;
    @Autowired private AgenDao agenDao;
    @Autowired private UserDao userDao;


    @GetMapping ("/agen/tagihan/list")
    public ModelMap listTagihanAgen(@RequestParam(value = "agen")Agen agen, Pageable page){

        return new ModelMap()
                .addAttribute("daftarTagihan", tagihanAgenDao.findByAgenOrderByTanggalTagihan(agen, page))
                .addAttribute("agen", agen);

    }

//Tagihan Di Agen

    @RequestMapping("/agen/tagihan/listAgen")
    public void tagihanAgen(ModelMap model, Authentication currentUser, Pageable page){
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

        Agen agen = agenDao.findByUser(u);
        logger.debug("Nama Cabang : "+agen.getNamaCabang());
        if(agen == null){
            logger.warn("Pendaftar not found for username {} ", username);
            return;
        }

             model.addAttribute("daftarTagihan", tagihanAgenDao.findByAgenOrderByTanggalTagihan(agen, page));
             model.addAttribute("agen", agen);

    }

}

