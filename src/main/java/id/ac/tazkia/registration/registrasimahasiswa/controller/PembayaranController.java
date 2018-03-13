package id.ac.tazkia.registration.registrasimahasiswa.controller;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

@Controller
public class PembayaranController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PembayaranController.class);

    @Autowired
    private PembayaranDao pembayaranDao;
    @Autowired
    private TagihanDao tagihanDao;
    @Autowired
    private BankDao bankDao;
    @Autowired
    private UserDao userDao;
    @Value("${upload.folder}")
    private String uploadFolder;
    @Autowired
    private RegistrasiService registrasiService;

    @ModelAttribute("daftarBank")
    public Iterable<Bank> daftarBank() {
        return bankDao.findAll();
    }

    @Value("classpath:keterangan-lulus.odt")
    private Resource templateSuratKeterangan;

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;

    @RequestMapping(value = "/biaya/pembayaran/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = true) String id,
                              @RequestParam(required = false) String error,
                              Model m) {
        //defaultnya, isi dengan object baru
        Pembayaran p = new Pembayaran();
        m.addAttribute("bayar", p);
        m.addAttribute("error", error);

        Tagihan t = tagihanDao.findOne(id);
        if (t != null) {
            p.setNilai(t.getNilai());
            p.setTagihan(t);
        }
    }

    @RequestMapping(value = "/biaya/pembayaran/form", method = RequestMethod.POST)
    public String prosesForm(@ModelAttribute @Valid Pembayaran pembayaran, BindingResult errors,
                             MultipartFile fileBukti) throws Exception {

        if (errors.hasErrors()) {
            LOGGER.debug("Error upload bukti pembayaran : {}", errors.toString());
            return "redirect:/biaya/pembayaran/form?error=Invalid&id=" + pembayaran.getTagihan().getId();
        }

        String idPeserta = pembayaran.getTagihan().getPendaftar().getId();

        String namaFile = fileBukti.getName();
        String jenisFile = fileBukti.getContentType();
        String namaAsli = fileBukti.getOriginalFilename();
        Long ukuran = fileBukti.getSize();

        LOGGER.debug("Nama File : {}", namaFile);
        LOGGER.debug("Jenis File : {}", jenisFile);
        LOGGER.debug("Nama Asli File : {}", namaAsli);
        LOGGER.debug("Ukuran File : {}", ukuran);

//        memisahkan extensi
        String extension = "";

        int i = namaAsli.lastIndexOf('.');
        int p = Math.max(namaAsli.lastIndexOf('/'), namaAsli.lastIndexOf('\\'));

        if (i > p) {
            extension = namaAsli.substring(i + 1);
        }

        String idFile = UUID.randomUUID().toString();
        String lokasiUpload = uploadFolder + File.separator + idPeserta;
        LOGGER.debug("Lokasi upload : {}", lokasiUpload);
        new File(lokasiUpload).mkdirs();
        File tujuan = new File(lokasiUpload + File.separator + idFile + "." + extension);
        pembayaran.setBuktiPembayaran(idFile + "." + extension);
        fileBukti.transferTo(tujuan);
        LOGGER.debug("File sudah dicopy ke : {}", tujuan.getAbsolutePath());

        Tagihan tagihan = pembayaran.getTagihan();
        tagihan.setLunas(true);
        tagihanDao.save(tagihan);

        registrasiService.aktivasiUser(tagihan.getPendaftar());

        LOGGER.debug("Bank : {}", pembayaran.getBank());

        pembayaranDao.save(pembayaran);
        return "redirect:/registrasi/list";
    }

    @RequestMapping("/biaya/pembayaran/list")
    public void list(@RequestParam(value = "id", required = false) String idTagihan, Model m, Pageable page) {
        if (StringUtils.hasText(idTagihan)) {
            m.addAttribute("nama", idTagihan);
            Tagihan t = tagihanDao.findOne(idTagihan);
            m.addAttribute("pembayaran", pembayaranDao.findByTagihan(t));
        } else {
            m.addAttribute("pembayaran", pembayaranDao.findAll(page));
        }

    }


    @GetMapping("/pembayaran/{pembayaran}/bukti/")
    public ResponseEntity<byte[]> tampilkanBuktiPembayaran(@PathVariable Pembayaran pembayaran) throws Exception {
        String lokasiFile = uploadFolder + File.separator + pembayaran.getTagihan().getPendaftar().getId()
                + File.separator + pembayaran.getBuktiPembayaran();
        LOGGER.debug("Lokasi file bukti : {}", lokasiFile);

        try {
            HttpHeaders headers = new HttpHeaders();
            if (pembayaran.getBuktiPembayaran().toLowerCase().endsWith("jpeg") || pembayaran.getBuktiPembayaran().toLowerCase().endsWith("jpg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (pembayaran.getBuktiPembayaran().toLowerCase().endsWith("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            byte[] data = Files.readAllBytes(Paths.get(lokasiFile));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //list
    @GetMapping("/registrasi/pembayaran/list")
    public void daftarPembayaran(@RequestParam(required = false) String pendaftar, Model m, Pageable page) {
        if (StringUtils.hasText(pendaftar)) {
            m.addAttribute("pendaftar", pendaftar);
            m.addAttribute("daftarPembayaran", pembayaranDao.cariByPendaftar("%" + pendaftar.toLowerCase() + "%", page));
        } else {
            m.addAttribute("daftarPembayaran", pembayaranDao.findAll(page));
        }
    }
/////

//Surat keterangan

    @GetMapping("/suratKeterangan")
    public void suratKeterangan(@RequestParam(name = "id") Pendaftar pendaftar,
                                HttpServletResponse response) {
        try {
            // 0. Setup converter
            Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

            // 1. Load template dari file
            InputStream in = templateSuratKeterangan.getInputStream();

            // 2. Inisialisasi template engine, menentukan sintaks penulisan variabel
            IXDocReport report = XDocReportRegistry.getRegistry().
                    loadReport(in, TemplateEngineKind.Freemarker);

            // 3. Context object, untuk mengisi variabel
            DetailPendaftar d = detailPendaftarDao.findByPendaftar(pendaftar);

            IContext ctx = report.createContext();
            ctx.put("nama", d.getPendaftar().getNama());
            ctx.put("ttl", d.getTtl());
            ctx.put("asalSekolah", d.getAsalSekolah());
            ctx.put("namaAyah", d.getNamaAyah());
            ctx.put("alamat", d.getAlamatRumah());
            ctx.put("programStudi", d.getPendaftar().getProgramStudi().getNama());

            Locale indonesia = new Locale("id", "id");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", indonesia);

            LocalDate tanggal =
                    LocalDate.now(ZoneId.systemDefault());


            String tanggalSekarang = tanggal.format(formatter);

            ctx.put("tanggalSekarang", tanggalSekarang);

            response.setHeader("Content-Disposition", "attachment;filename=Surat-Keterangan.pdf");
            OutputStream out = response.getOutputStream();
            report.convert(ctx, options, out);
            out.flush();
        } catch (Exception err) {
            LOGGER.error(err.getMessage(), err);
        }
    }

    //
//FORM NIMKO
    @Value("classpath:sample/Form NIRM.xlsx")
    private Resource formNimko;

    @GetMapping("/formNimko")
    public void downloadContohFileTagihan(HttpServletResponse response) throws Exception {
        response.setContentType("text/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=Form NIRM.xlsx");
        FileCopyUtils.copy(formNimko.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }
//

}

