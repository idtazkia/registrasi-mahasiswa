package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class NilaiBiayaController {
    @Autowired private NilaiBiayaDao nilaiBiayaDao;
    @Autowired private JenisBiayaDao jenisBiayaDao;
    @Autowired private GradeDao gradeDao;
    @Autowired private ProgramStudiDao programStudiDao;
    @Autowired private PeriodeDao periodeDao;
    @Autowired private UserDao userDao;


    private static final Logger LOGGER = LoggerFactory.getLogger(NilaiBiayaController.class);

    @GetMapping("/biaya/nilai/list")
    public void daftarNilaiBiaya(@RequestParam(required = false)JenisBiaya jenisBiaya,
                                 @RequestParam(required = false)Periode periode,
                                 @RequestParam(required = false)ProgramStudi prodi,
                                 Model m, Pageable page){
        if(jenisBiaya != null && periode != null && prodi != null) {
            m.addAttribute("prodi", prodi);
            m.addAttribute("jenisBiaya", jenisBiaya);
            m.addAttribute("periode", periode);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByProgramStudiAndJenisBiayaAndPeriode(prodi,jenisBiaya,periode, page));
        } else if(jenisBiaya != null && periode != null && prodi == null) {
            m.addAttribute("jenisBiaya", jenisBiaya);
            m.addAttribute("periode", periode);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByJenisBiayaAndPeriode(jenisBiaya,periode, page));
        } else if(jenisBiaya != null && periode == null && prodi != null) {
            m.addAttribute("prodi", prodi);
            m.addAttribute("jenisBiaya", jenisBiaya);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByJenisBiayaAndProgramStudi(jenisBiaya,prodi, page));
        } else if(jenisBiaya == null && periode != null && prodi != null) {
            m.addAttribute("prodi", prodi);
            m.addAttribute("periode", periode);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByPeriodeAndProgramStudi(periode,prodi, page));
        } else if(jenisBiaya == null && periode == null && prodi != null) {
            m.addAttribute("prodi", prodi);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByProgramStudi(prodi, page));
        } else if(jenisBiaya == null && periode != null && prodi == null) {
            m.addAttribute("periode", periode);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByPeriode(periode, page));
        } else if(jenisBiaya != null && periode == null && prodi == null) {
            m.addAttribute("jenisBiaya", jenisBiaya);
            m.addAttribute("daftarNilai", nilaiBiayaDao.findByJenisBiaya(jenisBiaya, page));
        } else {
            m.addAttribute("daftarNilai", nilaiBiayaDao.findAll(page));
        }
    }

    @ModelAttribute("daftarJenis")
    public Iterable<JenisBiaya> daftarJenis(){
        return jenisBiayaDao.findAll();
    }
    @ModelAttribute("daftarGrade")
    public Iterable<Grade> daftarGrade(){
        return gradeDao.findAll();
    }
    @ModelAttribute("daftarProdi")
    public Iterable<ProgramStudi> daftarProdi(){
        return programStudiDao.findAll();
    }
    @ModelAttribute("daftarPeriode")
    public Iterable<Periode> daftarPeriode(){
        return periodeDao.findAll();
    }


    @GetMapping("/biaya/nilai/form")
    public String form(Model model, Authentication currentUser, @RequestParam(required = false)String id){

        model.addAttribute("nilai", new NilaiBiaya());
        LOGGER.debug("Authentication class : {}", currentUser.getClass().getName());
        if (currentUser == null) {
            LOGGER.warn("Current user is null");
        }
        String username = ((UserDetails) currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        LOGGER.debug("User ID : {}", u.getId());
        if (u == null) {
            LOGGER.warn("Username {} not found in database ", username);
        }
        if (id != null && !id.isEmpty()) {
            NilaiBiaya nilaiBiaya = nilaiBiayaDao.findOne(id);
            if (nilaiBiaya != null) {
                nilaiBiaya.setUserEdit(u);
                nilaiBiaya.setTanggalEdit(LocalDateTime.now());
                model.addAttribute("nilai", nilaiBiaya);
            }
        }
        return "biaya/nilai/form";
    }


    @RequestMapping(value = "/biaya/nilai/form", method = RequestMethod.POST)
    public String prosesNilai(@Valid NilaiBiaya nilaiBiaya,
                              BindingResult error,@RequestParam(required = false) NilaiBiaya id,
                              Authentication currentUser){
        LOGGER.debug("Authentication class : {}", currentUser.getClass().getName());
        if (currentUser == null) {
            LOGGER.warn("Current user is null");
        }
        String username = ((UserDetails) currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        LOGGER.debug("User ID : {}", u.getId());
        if (u == null) {
            LOGGER.warn("Username {} not found in database ", username);
        }
        if (id != null){
            nilaiBiaya.setTanggalEdit(LocalDateTime.now());
            nilaiBiaya.setUserEdit(u);
        }
        nilaiBiayaDao.save(nilaiBiaya);
        return "redirect:/biaya/nilai/list";
    }

}
