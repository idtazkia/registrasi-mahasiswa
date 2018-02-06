package id.ac.tazkia.registration.registrasimahasiswa.controller;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RegistrasiDetail;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import id.ac.tazkia.registration.registrasimahasiswa.service.NotifikasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

@Controller
public class RegistrasiDetailController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private UserDao userDao;

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;
    @Autowired
    private NotifikasiService notifikasiService;

    @Value("classpath:kartu-ujian-tpa.odt")
    private Resource templateKartuUjian;


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

// kirim kartu hanya pada waktu isi data pertama kali
// kalau update data tidak perlu kirim kartu lagi
        if(p.getId() == null) {
            notifikasiService.kirimNotifikasiKartuUjian(p);
        }
//simpan
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

            String tanggalUjian = mingguKeduaBulanDepan.format(formatter);
            String tanggalBerlaku = berlaku.format(formatter);

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

}
