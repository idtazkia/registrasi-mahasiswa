package id.ac.tazkia.registration.registrasimahasiswa.controller;


import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
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
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    PendaftarDao pendaftarDao;
    @Autowired
    DetailPendaftarDao detailPendaftarDao;
    @Autowired
    PembayaranDao pembayaranDao;
    @Autowired
    HasilTestDao hasilTestDao;
    @Autowired
    UserDao userDao;

    @GetMapping("/home")
    public void home(Model model, Authentication currentUser, Pageable page){
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
        if (u.getRole().getName() == "pendaftar") {

            Pendaftar p = pendaftarDao.findByUser(u);
            logger.debug("Nama Pendaftar : " + p.getNama());
            if (p == null) {
                logger.warn("Pendaftar not found for username {} ", username);
                return;
            }

            Pembayaran pembayaran = pembayaranDao.findByTagihanPendaftarIdAndTagihanJenisBiayaId(p.getId(), "002");
            model.addAttribute("pembayaran", pembayaran);
            System.out.println(pembayaran);
        }else {
            model.addAttribute("cPendaftar", pendaftarDao.countPendaftarByProgramStudiNotNullAndStatusTrue());
            model.addAttribute("cDetail", detailPendaftarDao.countDetailPendaftarByPendaftarNotNull());
            model.addAttribute("cDaftar", pembayaranDao.countPembayaranByTagihanJenisBiayaId("002"));
            model.addAttribute("cHasil", hasilTestDao.countHasilTestByPendaftarProgramStudiNotNullAndPendaftarStatusTrue());
            model.addAttribute("cHasilSmart", hasilTestDao.countHasilTestByPendaftarProgramStudiIsNullAndPendaftarStatusTrue());
        }
    }
}
