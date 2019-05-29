package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Autowired private DetailPendaftarDao detailPendaftarDao;
    @Autowired private TagihanDao tagihanDao;

    @GetMapping("/registrasi/list")
    public void daftarPendaftaran(@RequestParam(required = false)String search, Model m,
                                  @PageableDefault(size = 10, sort = "nomorRegistrasi", direction = Sort.Direction.DESC) Pageable page){


        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarPendaftaran", pendaftarDao
                    .findByNomorRegistrasiContainingAndProgramStudiNotNullAndStatusTrueOrNamaContainingIgnoreCaseAndProgramStudiNotNullAndStatusTrueOrderByNomorRegistrasi(search,search,page));
            m.addAttribute("asa", detailPendaftarDao.findAll());
            m.addAttribute("reset", tagihanDao.findAll());

        } else {
            m.addAttribute("daftarPendaftaran", pendaftarDao.findByProgramStudiNotNullAndStatusTrue(page));
            m.addAttribute("asa", detailPendaftarDao.findAll());
            m.addAttribute("resendPassword", tagihanDao.findAll());

        }
    }

    //tampikan form
    @RequestMapping(value = "/registrasi/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
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
        KabupatenKota kk = kabupatenKotaDao.findById(registrasi.getIdKabupatenKota()).get();
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findById(registrasi.getProgramStudi()).get();
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
        response.getWriter().println("No,Nomor Registrasi,Nama,Kota/Kab Sekolah,Asal Sekolah,No Hp,Email,Program Studi,Pemberi Rekomendasi,Nama Perekomendasi");

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
            response.getWriter().print(p.getKabupatenKota().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaAsalSekolah());
            response.getWriter().print(",");
            response.getWriter().print(p.getNoHp());
            response.getWriter().print(",");
            response.getWriter().print(p.getEmail());
            response.getWriter().print(",");
            response.getWriter().print(p.getProgramStudi().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getPemberiRekomendasi());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaPerekomendasi());
            response.getWriter().println();
        }

           response.getWriter().flush();
    }

    @GetMapping("/registrasi/hahah")
    public void detailPendaftar(Pendaftar pendaftar, Model m,
                                HttpServletResponse response){
        DetailPendaftar d = detailPendaftarDao.findByPendaftar(pendaftar);
        LOGGER.debug("Nomor Registrasi :"+ pendaftar.getNomorRegistrasi());
        m.addAttribute("detail", d);

    }

    @RequestMapping(value = "/registrasi/update", method = RequestMethod.GET)
    public String updateForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("pendaftar", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
            if (p != null){
                m.addAttribute("pendaftar", p);
                KabupatenKota kabupatenKota = kabupatenKotaDao.findById(p.getKabupatenKota().getId()).get();
                m.addAttribute("kokab", kabupatenKota);
            }

        }
        return "registrasi/update";
    }

    @RequestMapping(value = "/registrasi/update", method = RequestMethod.POST)
    public String prosesForm(@Valid Pendaftar p, BindingResult errors){
        if(errors.hasErrors()){
            return "registrasi/update";
        }
        pendaftarDao.save(p);
        return "redirect:list";
    }

    @RequestMapping("/registrasi/hapus")
    public  String hapus(@RequestParam("id") Pendaftar pendaftar ){
        pendaftar.setStatus(false);
        pendaftarDao.save(pendaftar);
        return "redirect:list";
    }

    @GetMapping("/registrasi/csv/nonAktif")
    public void rekapPendaftarCsvNonAktif(HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=Pendaftar.csv");
        response.setContentType("text/csv");
        response.getWriter().println("No,Nomor Registrasi,Nama,Kota/Kab Sekolah,Asal Sekolah,No Hp,Email,Program Studi,Pemberi Rekomendasi,Nama Perekomendasi");

        Iterable<Pendaftar> dataPendaftar = pendaftarDao.findByProgramStudiNotNullAndStatusFalse();

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
            response.getWriter().print(p.getKabupatenKota().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaAsalSekolah());
            response.getWriter().print(",");
            response.getWriter().print(p.getNoHp());
            response.getWriter().print(",");
            response.getWriter().print(p.getEmail());
            response.getWriter().print(",");
            response.getWriter().print(p.getProgramStudi().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getPemberiRekomendasi());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaPerekomendasi());
            response.getWriter().println();
        }

        response.getWriter().flush();
    }


    @GetMapping("/registrasi/restore")
    public void restorependaftar(@RequestParam(required = false)String search, Model m,
                                  @PageableDefault(size = 10, sort = "nomorRegistrasi", direction = Sort.Direction.DESC) Pageable page){


        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarPendaftaran", pendaftarDao
                    .findByNomorRegistrasiContainingAndProgramStudiNotNullAndStatusFalseOrNamaContainingIgnoreCaseAndProgramStudiNotNullAndStatusFalseOrderByNomorRegistrasi(search,search,page));
            m.addAttribute("asa", detailPendaftarDao.findAll());
            m.addAttribute("reset", tagihanDao.findAll());

        } else {
            m.addAttribute("daftarPendaftaran", pendaftarDao.findByProgramStudiNotNullAndStatusFalse(page));
            m.addAttribute("asa", detailPendaftarDao.findAll());
            m.addAttribute("resendPassword", tagihanDao.findAll());

        }
    }

    @RequestMapping("/registrasi/restorePendaftar")
    public  String restore(@RequestParam("id") Pendaftar pendaftar ){
        pendaftar.setStatus(true);
        pendaftarDao.save(pendaftar);
        return "redirect:/registrasi/restore";
    }


//Link Iklan Registrasi
    @RequestMapping(value = "/registrasi/formfb", method = RequestMethod.GET)
    public String FormFb(@RequestParam(value = "id", required = false) String id,
                         Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "registrasi/formfb";
    }

    @PostMapping(value = "/registrasi/formfb")
    public String prosesFormFb(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findById(registrasi.getIdKabupatenKota()).get();
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findById(registrasi.getProgramStudi()).get();
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "registrasi/formfb";
        }

        registrasi.setPemberiRekomendasi("medsos");
        registrasi.setNamaPerekomendasi("Fecebook");
        registrasiService.prosesPendaftaran(registrasi, prodi, kk);

        return "redirect:/selesai";
    }

    @RequestMapping(value = "/registrasi/formig", method = RequestMethod.GET)
    public String FormIg(@RequestParam(value = "id", required = false) String id,
                         Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "registrasi/formig";
    }

    @PostMapping(value = "/registrasi/formig")
    public String prosesFormIg(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findById(registrasi.getIdKabupatenKota()).get();
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findById(registrasi.getProgramStudi()).get();
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "registrasi/formig";
        }

        registrasi.setPemberiRekomendasi("medsos");
        registrasi.setNamaPerekomendasi("Instagram");
        registrasiService.prosesPendaftaran(registrasi, prodi, kk);

        return "redirect:/selesai";
    }

    @RequestMapping(value = "/registrasi/formgg", method = RequestMethod.GET)
    public String FormGg(@RequestParam(value = "id", required = false) String id,
                         Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "registrasi/formgg";
    }

    @PostMapping(value = "/registrasi/formgg")
    public String prosesFormGg(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findById(registrasi.getIdKabupatenKota()).get();
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findById(registrasi.getProgramStudi()).get();
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "registrasi/formgg";
        }

        registrasi.setPemberiRekomendasi("medsos");
        registrasi.setNamaPerekomendasi("Google");
        registrasiService.prosesPendaftaran(registrasi, prodi, kk);

        return "redirect:/selesai";
    }



    @RequestMapping(value = "/registrasi/mustami", method = RequestMethod.GET)
    public String FormMustami(@RequestParam(value = "id", required = false) String id,
                         Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("registrasi", new Pendaftar());

        if (id != null && !id.isEmpty()){
            Pendaftar p= pendaftarDao.findById(id).get();
            if (p != null){
                m.addAttribute("registrasi", p);
            }
        }
        return "registrasi/mustami";
    }

    @PostMapping(value = "/registrasi/mustami")
    public String prosesFormMustami(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findById(registrasi.getIdKabupatenKota()).get();
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findById(registrasi.getProgramStudi()).get();
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "registrasi/mustami";
        }

        registrasi.setPemberiRekomendasi("lainnya");
        registrasi.setNamaPerekomendasi("Mustami' / Air Santri");
        registrasiService.prosesPendaftaran(registrasi, prodi, kk);

        return "redirect:/selesai";
    }
//
}

