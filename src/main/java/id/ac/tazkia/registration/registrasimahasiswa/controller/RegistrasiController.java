package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.KabupatenKotaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.ProgramStudiDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class RegistrasiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiController.class);

    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private KabupatenKotaDao kabupatenKotaDao;
    @Autowired private RegistrasiService registrasiService;
    @Autowired private ProgramStudiDao programStudiDao;

    @GetMapping("/registrasi/list")
    public void daftarPendaftaran(@RequestParam(required = false)String search, Model m,
                                  @PageableDefault(size = 10, sort = "nomorRegistrasi", direction = Sort.Direction.DESC) Pageable page){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarPendaftaran", pendaftarDao
                    .findByNomorRegistrasiContainingOrNamaContainingIgnoreCaseOrderByNomorRegistrasi(search,search,page));
        } else {
            m.addAttribute("daftarPendaftaran", pendaftarDao.findByProgramStudiNotNull(page));
        }
    }

    //tampikan form
    @RequestMapping(value = "/registrasi/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findOne(id);
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "registrasi/form";
    }
////

    @PostMapping(value = "/registrasi/form")
    public String prosesForm(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
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

        registrasiService.prosesPendaftaran(registrasi, prodi, kk);

        return "redirect:/selesai";
    }

    @ModelAttribute("daftarProdi")
    public Iterable<ProgramStudi> daftarProdi(){
        return programStudiDao.findAll();
    }


    @GetMapping("/selesai")
    public  void  selesai(){ }


    @GetMapping("/registrasi/csv")
    public void rekapPendaftarCsv(HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=Pendaftar.csv");
        response.setContentType("text/csv");
        response.getWriter().println("No,Nomor Registrasi,Nama,Kota/Kab Sekolah,Asal Sekolah,No Hp,Email,Program Studi");

        Iterable<Pendaftar> dataPendaftar = pendaftarDao.findByProgramStudiNotNull();

        Integer baris = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (Pendaftar p : dataPendaftar) {
            baris++;
            response.getWriter().print(baris);
            response.getWriter().print(",");
            response.getWriter().print(p.getNomorRegistrasi());
            response.getWriter().print(",");
            response.getWriter().print(p.getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getKabupatenKota());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaAsalSekolah());
            response.getWriter().print(",");
            response.getWriter().print(p.getNoHp());
            response.getWriter().print(",");
            response.getWriter().print(p.getEmail());
            response.getWriter().print(",");
            response.getWriter().print(p.getProgramStudi().getNama());
            response.getWriter().println();
        }

           response.getWriter().flush();
    }
}

