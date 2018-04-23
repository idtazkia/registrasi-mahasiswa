package id.ac.tazkia.registration.registrasimahasiswa.controller.agen;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.controller.RegistrasiDetailController;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.AgenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class RegistrasiAgenController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);
    private static final DateTimeFormatter FORMATTER_ISO_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private UserDao userDao;
    @Autowired private AgenDao agenDao;
    @Autowired private KabupatenKotaDao kabupatenKotaDao;
    @Autowired private ProgramStudiDao programStudiDao;
    @Autowired private AgenService agenService;
    @Autowired private PendaftarAgenDao pendaftarAgenDao;

    @ModelAttribute("daftarProdi")
    public Iterable<ProgramStudi> daftarProdi(){
        return programStudiDao.findAll();
    }


    @GetMapping("agen/pendaftar/form")
    public void formDaftarViaAgen(Model model, Authentication currentUser){
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

        Agen p = agenDao.findByUser(u);
        logger.debug("Nama Cabang : "+p.getNamaCabang());
        if(p == null){
            logger.warn("Nama Cabang not found for username {} ", username);
            return;
        }

        Registrasi detail = new Registrasi();
        detail.setNamaPerekomendasi(p.getNamaCabang());
        model.addAttribute("registrasi", detail);

    }

    @PostMapping("/agen/pendaftar/form")
    public String prosesFrom(@ModelAttribute @Valid Registrasi registrasi,Authentication currentUser, BindingResult errors, SessionStatus status){
        logger.debug("Authentication class : {}",currentUser.getClass().getName());

        if(currentUser == null){
            logger.warn("Current user is null");
        }

        String username = ((UserDetails)currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        logger.debug("User ID : {}", u.getId());
        if(u == null){
            logger.warn("Username {} not found in database ", username);
        }

        Agen p = agenDao.findByUser(u);
        logger.debug("Nama Cabang : "+p.getNamaCabang());
        if(p == null){
            logger.warn("Nama Cabang not found for username {} ", username);
        }


        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findOne(registrasi.getIdKabupatenKota());
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findOne(registrasi.getProgramStudi());
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "registrasi/form";
        }

        registrasi.setPemberiRekomendasi(AppConstants.PENDAFTAR_AGEN);

        agenService.prosesDaftar(registrasi, prodi, kk);

        Pendaftar pendaftar = pendaftarDao.findByNomorRegistrasi(registrasi.getNomorRegistrasi());
        PendaftarAgen pa = new PendaftarAgen();
        pa.setAgen(p);
        pa.setPendaftar(pendaftar);
        pa.setTanggal(LocalDateTime.now());
        pa.setStatusTagihan(StatusTagihan.BELUM_DITAGIH);
        pendaftarAgenDao.save(pa);



        return "redirect:/home";
    }

//List
        @RequestMapping("/agen/pendaftar/listAgen")
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

            model.addAttribute("daftarPendaftaran", pendaftarAgenDao.findByAgenOrderByTanggal(agen, page));
            model.addAttribute("agen", agen);

        }

        @GetMapping ("/agen/pendaftar/list")
        public ModelMap listTagihanAgen(@RequestParam(value = "agen")Agen agen, Pageable page){

            return new ModelMap()
                    .addAttribute("daftarPendaftaran", pendaftarAgenDao.findByAgenOrderByTanggal(agen, page))
                    .addAttribute("agen", agen);

        }

}
