package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class NilaiBiayaController {
    @Autowired private NilaiBiayaDao nilaiBiayaDao;
    @Autowired private JenisBiayaDao jenisBiayaDao;
    @Autowired private GradeDao gradeDao;
    @Autowired private ProgramStudiDao programStudiDao;
    @Autowired private PeriodeDao periodeDao;

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
    public void formNilaiBiaya(@RequestParam(value = "id", required = false) String id,
                               Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("nilai", new NilaiBiaya());

        if (id != null && !id.isEmpty()){
            NilaiBiaya nb= nilaiBiayaDao.findOne(id);
            if (nb != null){
                m.addAttribute("nilai", nb);
            }
        }
    }


    @RequestMapping(value = "/biaya/nilai/form", method = RequestMethod.POST)
    public String prosesForm(@Valid NilaiBiaya nilaiBiaya, BindingResult errors){
        if(errors.hasErrors()){
            return "/biaya/nilai/form";
        }
        nilaiBiayaDao.save(nilaiBiaya);
        return "redirect:list";
    }

}
