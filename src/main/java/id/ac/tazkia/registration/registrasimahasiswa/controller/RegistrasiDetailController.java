package id.ac.tazkia.registration.registrasimahasiswa.controller;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.NimDto;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RegistrasiDetail;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.NotifikasiService;
import id.ac.tazkia.registration.registrasimahasiswa.service.TagihanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class RegistrasiDetailController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private UserDao userDao;
    @Autowired private HasilTestDao hasilTestDao;
    @Autowired private TagihanService tagihanService;
    @Autowired private PendidikanDao pendidikanDao;
    @Autowired private PenghasilanDao penghasilanDao;
    @Autowired private PekerjaanDao pekerjaanDao;

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;
    @Autowired
    private NotifikasiService notifikasiService;
    @Autowired
    private ProgramStudiDao programStudiDao;

    @Value("classpath:kartu-ujian-tpa.odt")
    private Resource templateKartuUjian;

     @Value("classpath:kartu-ujian-pasca.odt")
    private Resource templateKartuUjianPasca;

    @GetMapping("/api/nim")
    @ResponseBody
    public Iterable<NimDto> findByNimDanStatus(@RequestParam(required = false) StatusTagihan status){
        return detailPendaftarDao.cariNimDtoByStatus(status);

    }

    @GetMapping("/api/update/nim")
    @ResponseBody
    public Iterable<NimDto> updateNim(@RequestParam(required = false) String nim, Pageable page){
        List<DetailPendaftar> detailPendaftars =  detailPendaftarDao.findByNimAndStatus(nim, StatusTagihan.N);
        List<NimDto> nimDtos = new ArrayList<>();
        BeanUtils.copyProperties(nimDtos, detailPendaftars);


        for (DetailPendaftar detailPendaftar : detailPendaftars) {
            detailPendaftar.setStatus(StatusTagihan.Y);
            detailPendaftarDao.save(detailPendaftar);
            NimDto nimDto = new NimDto();
            nimDto.setNama(detailPendaftar.getPendaftar().getNama());
            nimDto.setNim(detailPendaftar.getNim());
            nimDto.setJenisKelamin(detailPendaftar.getJenisKelamin());
            nimDto.setStatus(detailPendaftar.getStatus());
            nimDtos.add(nimDto);
        }
        return nimDtos;

    }

    @ModelAttribute("pendidikanOrtu")
    public Iterable<Pendidikan> pendidikanOrtu(){return pendidikanDao.findAll();}

    @ModelAttribute("pekerjaanOrtu")
    public Iterable<Pekerjaan> pekerjaanOrtu(){return pekerjaanDao.findAll();}

    @ModelAttribute("penghasilanOrtu")
    public Iterable<Penghasilan> penghasilanOrtu(){return penghasilanDao.findAll();}

    @GetMapping("/registrasi/detail/sukses")
    public void sukses(){
    }

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
        detail.setNoHp(p.getNoHp());
        detail.setKabupatenKota(p.getKabupatenKota().getNama());
        model.addAttribute("registrasi", detail);

        DetailPendaftar d = detailPendaftarDao.findByPendaftar(p);
        logger.debug("Nomor Registrasi :"+ p.getNomorRegistrasi());
        if (d != null){
            model.addAttribute("detail", d);
        }
    }

    @PostMapping(value = "/registrasi/detail/form")
    public String prosesForm(@RequestParam(value = "pendaftar") String pendaftar, @ModelAttribute @Valid DetailPendaftar dp, BindingResult errors){
        if(errors.hasErrors()){
            logger.debug("Error Validasi Form : {}", errors.toString());
            return "/registrasi/detail/form";
        }

        Pendaftar pen = pendaftarDao.findById(pendaftar).get();
        logger.debug("Pendaftarnya = " + pen.getNomorRegistrasi());
        HasilTest h = hasilTestDao.findByPendaftar(dp.getPendaftar());
//setProgramStudi(S2)
        ProgramStudi pr07 = programStudiDao.findById("007").get();
        ProgramStudi pr09 = programStudiDao.findById("009").get();
        ProgramStudi pr08 = programStudiDao.findById("008").get();
        Boolean pen1 = pen.getProgramStudi().equals(pr07);
        Boolean pen2 = pen.getProgramStudi().equals(pr08);
        Boolean pen3 = pen.getProgramStudi().equals(pr09);
        Boolean cekPodi = pen1 == true || pen2 == true || pen3 == true;
//
//Cek Jenis Test
        Boolean tpa = JenisTest.TPA.equals(dp.getJenisTest());
        Boolean jpa = JenisTest.JPA.equals(dp.getJenisTest());
        Boolean jenisTest = JenisTest.TPA.equals(dp.getJenisTest()) || JenisTest.JPA.equals(dp.getJenisTest());
//
        if (dp.getId() == null && cekPodi.equals(Boolean.TRUE) && jenisTest.equals(Boolean.TRUE)) {
            logger.debug("ProgramStudi Pasca  : "+ dp.getPendaftar().getProgramStudi().getNama());
            notifikasiService.kirimNotifikasiKartuUjianPasca(dp);
        } else if (h != null && dp.getId() == null && cekPodi.equals(Boolean.FALSE)) {
            logger.debug("Kirim notifikasi hasil test");
            notifikasiService.kirimNotifikasiHasilTest(h);
            //tagihanService.createTagihanDaftarUlang(pe, h, h.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        } else if (dp.getId() == null && tpa.equals(Boolean.TRUE) && cekPodi.equals(Boolean.FALSE)) {
            logger.debug("kirim kartu ujian TPA");
            notifikasiService.kirimNotifikasiKartuUjian(dp);
        } else if (dp.getId() == null && jpa.equals(Boolean.TRUE) && cekPodi.equals(Boolean.FALSE)) {
            logger.debug("kirim perintah JPA");
            notifikasiService.kirimNotifikasiJpa(dp);
        }


//simpan
        detailPendaftarDao.save(dp);
        return "redirect:/registrasi/detail/sukses";

    }


//VIEW DETAIL PENDAFTAR

    @GetMapping("/registrasi/detail/view")
    public void  viewDetail(@RequestParam(value = "id",required = false)String idPendaftar, Model m, Pageable page){
        if(StringUtils.hasText(idPendaftar)) {
            m.addAttribute("pendaftar", idPendaftar);
            Pendaftar p = pendaftarDao.findById(idPendaftar).get();
            m.addAttribute("view", detailPendaftarDao.findByPendaftar(p));
        } else {
            m.addAttribute("view", detailPendaftarDao.findAll(page));
        }
    }



    @GetMapping("/kartu")
    public void kartuUjian(@RequestParam(name = "id") Pendaftar pendaftar,
                           HttpServletResponse response){
        try {
            // 0. Setup converter
            Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

            // 1. Load template dari file
            InputStream in = templateKartuUjian.getInputStream();

            // 2. Inisialisasi template engine, menentukan sintaks penulisan variabel
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            // 3. Context object, untuk mengisi variabel

            IContext ctx = report.createContext();
            ctx.put("nomor", pendaftar.getNomorRegistrasi());
            ctx.put("nama", pendaftar.getNama());
            ctx.put("prodi", pendaftar.getProgramStudi().getNama());
            ctx.put("program", "Reguler");
            ctx.put("hp", "08159551299");

            Locale indonesia = new Locale("id", "id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", indonesia);

            LocalDate mingguKeduaBulanDepan =
                    LocalDate.now(ZoneId.systemDefault())
                            .plusMonths(1)
                            .with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY))
                            .plusWeeks(1);

            LocalDate berlaku = mingguKeduaBulanDepan.plusWeeks(2);

//            String tanggalUjian = mingguKeduaBulanDepan.format(formatter);
            String tanggalUjian = "Minggu, 25\n" +
                    "Agustus 2019";
//            String tanggalBerlaku = berlaku.format(formatter);
            String tanggalBerlaku = "08 September 2019";

            ctx.put("tanggalUjian", tanggalUjian);
            ctx.put("berlaku", tanggalBerlaku);

            response.setHeader("Content-Disposition", "attachment;filename=kartu-ujian.pdf");
            OutputStream out = response.getOutputStream();
            report.convert(ctx, options, out);
            out.flush();
        } catch (Exception err){
            logger.error(err.getMessage(), err);
        }
    }

    @GetMapping("/kartuPasca")
    public void kartuUjianPasca(@RequestParam(name = "id") Pendaftar pendaftar,
                           HttpServletResponse response){
        try {
            // 0. Setup converter
            Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

            // 1. Load template dari file
            InputStream in = templateKartuUjianPasca.getInputStream();

            // 2. Inisialisasi template engine, menentukan sintaks penulisan variabel
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            // 3. Context object, untuk mengisi variabel

            IContext ctx = report.createContext();
            ctx.put("nomor", pendaftar.getNomorRegistrasi());
            ctx.put("nama", pendaftar.getNama());
            ctx.put("prodi", pendaftar.getProgramStudi().getNama());
            ctx.put("program", "Reguler");
            ctx.put("hp", "08159551299");
            ctx.put("sekarang", LocalDate.now());

            response.setHeader("Content-Disposition", "attachment;filename=kartu_ujian_pasca.pdf");
            OutputStream out = response.getOutputStream();
            report.convert(ctx, options, out);
            out.flush();
        } catch (Exception err){
            logger.error(err.getMessage(), err);
        }
    }

//Laporan

    @GetMapping("/registrasi/detail/csv")
    public void rekapPendaftarCsv(HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=Detail_Pendaftar.csv");
        response.setContentType("text/csv");
        response.getWriter().println("No,Nomor Registrasi,Nim,Nama,Kota/Kab Sekolah,Asal Sekolah,No Hp,Email,Program Studi,Konsentrasi,Alamat,Tempat Lahir,Tanggal Lahir," +
                "Warga Negara,Jenis Kelamin,Nama Ayah,Pekerjaan Ayah,Nama Ibu,Pekerjaan Ibu,Nomor Hp Orang Tua, Email Orang Tua, Penghasilan Orang Tua, Jumlah Tanggungan, Rencana Biaya");

        Iterable<DetailPendaftar> dataPendaftar = detailPendaftarDao.findAll();

        Integer baris = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (DetailPendaftar p : dataPendaftar) {
            baris++;
            response.getWriter().print(baris);
            response.getWriter().print(",");
            response.getWriter().print(p.getPendaftar().getNomorRegistrasi());
            response.getWriter().print(",");
            if (p.getNim() != null) {
                response.getWriter().print(p.getNim());
            } else {
                response.getWriter().print("");
            }
            response.getWriter().print(",");
            response.getWriter().print(p.getPendaftar().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getPendaftar().getKabupatenKota().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getAsalSekolah());
            response.getWriter().print(",");
            response.getWriter().print(p.getNoHp());
            response.getWriter().print(",");
            response.getWriter().print(p.getEmail());
            response.getWriter().print(",");
            response.getWriter().print(p.getPendaftar().getProgramStudi().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getPendaftar().getKonsentrasi());
            response.getWriter().print(",");
            response.getWriter().print(p.getAlamatRumah());
            response.getWriter().print(",");
            response.getWriter().print(p.getTempatLahir());
            response.getWriter().print(",");
            response.getWriter().print(p.getTanggalLahir());
            response.getWriter().print(",");
            response.getWriter().print(p.getStatusSipil());
            response.getWriter().print(",");
            response.getWriter().print(p.getJenisKelamin());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaAyah());
            response.getWriter().print(",");
            response.getWriter().print(p.getPekerjaanAyah().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getNamaIbu());
            response.getWriter().print(",");
            response.getWriter().print(p.getPekerjaanIbu().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getNohpOrangtua());
            response.getWriter().print(",");
            response.getWriter().print(p.getEmailOrangtua());
            response.getWriter().print(",");
            response.getWriter().print(p.getPenghasilanOrangtua().getNama());
            response.getWriter().print(",");
            response.getWriter().print(p.getJumlahTanggungan());
            response.getWriter().print(",");
            response.getWriter().print(p.getRencanaBiaya());

            response.getWriter().println();
        }

        response.getWriter().flush();
    }


    @PostMapping("/registrasi/detail/notifikasi")
    public String processNotifikasiForm(@RequestParam DetailPendaftar p){
    if (p == null) {
          logger.warn("Notifikasi detail null");
          return "redirect:list";
        }

        HasilTest h = hasilTestDao.findByPendaftar(p.getPendaftar());

            System.out.println("Id Detail : "+ p.getId());
            System.out.println("Jenis Test : "+ p.getJenisTest());

            if (h != null && p.getJenisTest().equals(JenisTest.SMART_TEST)){
                System.out.println(p.getJenisTest());
                Pendaftar pe = h.getPendaftar();
                notifikasiService.kirimNotifikasiHasilTest(h);
//                tagihanService.createTagihanDaftarUlang(pe, h, h.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }else {
                if (p.getJenisTest().equals(JenisTest.JPA)) {
                    System.out.println(p.getJenisTest());
                    notifikasiService.kirimNotifikasiJpa(p);
                } else if (p.getJenisTest().equals(JenisTest.TPA)) {
                    System.out.println(p.getJenisTest());
                    notifikasiService.kirimNotifikasiKartuUjian(p);
                }
            }

            return "redirect:/registrasi/list";
    }


}
