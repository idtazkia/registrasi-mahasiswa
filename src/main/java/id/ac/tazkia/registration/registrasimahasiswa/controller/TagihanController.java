package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.HasilTestDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.TagihanService;
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
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.util.ArrayList;
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
    @Autowired
    private HasilTestDao hasilTestDao;
    @Autowired
    private PeriodeDao periodeDao;
    @Autowired
    private TagihanService tagihanService;
    @Autowired
    private NilaiBiayaDao nilaiBiayaDao;
    @Autowired
    private ProgramStudiDao programStudiDao;
    @Autowired
    private GradeDao gradeDao;

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
        return tagihanDao.findByPendaftarOrderByTanggalTagihan(pendaftarDao.findById(pendaftar).get());
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

    @RequestMapping(value = "/biaya/tagihan/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = true) String id,
                              @RequestParam(required = false) String error,Pageable page,
                              Model m){

        Pendaftar p = pendaftarDao.findById(id).get();
        m.addAttribute("pendaftar", p);

        HasilTest d = hasilTestDao.findByPendaftar(p);
        List<Periode> periode = periodeDao.cariPeriodeUntukTanggal(d.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            HasilTestDto hasilTestDto = new HasilTestDto();

            logger.debug("Jumlah data : {}", periode.size());

        for (Periode periode1 : periode) {
            hasilTestDto.setPeriode(periode1);
            hasilTestDto.setId(d.getId());
            hasilTestDto.setPendaftar(d.getPendaftar());
            hasilTestDto.setJenisTest(d.getJenisTest());
            hasilTestDto.setGrade(d.getGrade());
            logger.debug(hasilTestDto.getId() + " " + hasilTestDto.getPeriode());

            JenisBiaya jenisBiaya = jb.findById(AppConstants.JENIS_BIAYA_DAFTAR_ULANG).get();
            ProgramStudi programStudi = programStudiDao.findById(hasilTestDto.getPendaftar().getProgramStudi().getId()).get();
            Periode pr = periodeDao.findById(hasilTestDto.getPeriode().getId()).get();
            Grade gd = gradeDao.findById(hasilTestDto.getGrade().getId()).get();
            List<NilaiBiaya> nilaiBiaya = nilaiBiayaDao.findByProgramStudiAndJenisBiayaAndPeriodeAndGrade(programStudi,jenisBiaya,pr, gd);
            for (NilaiBiaya nilaiBiaya1 : nilaiBiaya) {
                hasilTestDto.setNilaiBiaya(nilaiBiaya1);
                System.out.println("Nilai Biaya :"+ nilaiBiaya);
            }
            if (d != null){
                m.addAttribute("hasil", hasilTestDto);
            }
        }
        logger.debug("Nomor Registrasi :"+ p.getNomorRegistrasi());

        JenisBiaya jenisBiaya = jb.findById(AppConstants.JENIS_BIAYA_DAFTAR_ULANG).get();
        ProgramStudi programStudi = programStudiDao.findById(p.getProgramStudi().getId()).get();
        m.addAttribute("daftarNilai", nilaiBiayaDao.findByJenisBiayaAndProgramStudi(jenisBiaya,programStudi, page));

    }


    @RequestMapping(value = "/biaya/tagihan/form", method = RequestMethod.POST)
    public String prosesForm(@Valid HasilTestDto hasilTestDto,
                             BindingResult errors){

        HasilTest hasilTest = hasilTestDao.findByPendaftar(hasilTestDto.getPendaftar());
        hasilTest.setKeterangan(hasilTestDto.getKeterangan());
        hasilTestDao.save(hasilTest);

        tagihanService.createTagihanDaftarUlang(hasilTest.getPendaftar(),hasilTestDto.getNilai());

        return "redirect:/registrasi/hasil/list";
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
