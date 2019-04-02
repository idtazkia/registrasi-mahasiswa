package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.dto.UploadError;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class SmartTestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagihanController.class);

    @Value("classpath:sample/HASIL SMARTTEST .csv")
    private Resource contohFileSmartTest;

    @Autowired
    private RegistrasiService registrasiService;
    @Autowired
    private PendaftarDao pendaftarDao;
    @Autowired
    private HasilTestDao hasilTestDao;
    @Autowired
    private UserDao userDao;


    @GetMapping("/contoh/smartTest")
    public void downloadContohFileTagihan(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=contoh-HASIL SMARTTEST.csv");
        FileCopyUtils.copy(contohFileSmartTest.getInputStream(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    @RequestMapping(value = "/registrasi/smart/form", method = RequestMethod.GET)
    public String tampilkanForm(){
        return "registrasi/smart/form";
    }

    @PostMapping("/registrasi/smart/form")
    public String processFormUpload(@RequestParam Sekolah idSekolah, KabupatenKota kabupatenKota, @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalTest,
                                    @RequestParam(required = false) Boolean pakaiHeader,
                                    MultipartFile fileSmartTest,
                                    RedirectAttributes redirectAttrs, Authentication currentUser){
        LOGGER.debug("Authentication class : {}",currentUser.getClass().getName());

        if(currentUser == null){
            LOGGER.warn("Current user is null");
        }

        String username = ((UserDetails)currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        LOGGER.debug("User ID : {}", u.getId());
        if(u == null){
            LOGGER.warn("Username {} not found in database ", username);
        }

        LOGGER.debug("Asal Sekolah : {}",idSekolah);
        LOGGER.debug("Kabupaten Kota Sekolah : {}",kabupatenKota);
        LOGGER.debug("Tanggal Test : {}",tanggalTest);
        LOGGER.debug("Pakai Header : {}",pakaiHeader);
        LOGGER.debug("Nama File : {}",fileSmartTest.getName());
        LOGGER.debug("Ukuran File : {}",fileSmartTest.getSize());

        List<UploadError> errors = new ArrayList<>();
        Integer baris = 0;

        if(idSekolah == null){
            errors.add(new UploadError(baris, "Asal sekolah harus diisi", ""));
            redirectAttrs
                    .addFlashAttribute("jumlahBaris", 0)
                    .addFlashAttribute("jumlahSukses", 0)
                    .addFlashAttribute("jumlahError", errors.size())
                    .addFlashAttribute("errors", errors);
            return "redirect:hasil";
        }
        if(kabupatenKota == null){
            errors.add(new UploadError(baris, "Kabupaten/Kota harus diisi", ""));
            redirectAttrs
                    .addFlashAttribute("jumlahBaris", 0)
                    .addFlashAttribute("jumlahSukses", 0)
                    .addFlashAttribute("jumlahError", errors.size())
                    .addFlashAttribute("errors", errors);
            return "redirect:hasil";
        }
        if(tanggalTest == null){
            errors.add(new UploadError(baris, "Tanggal test harus diisi", ""));
            redirectAttrs
                    .addFlashAttribute("jumlahBaris", 0)
                    .addFlashAttribute("jumlahSukses", 0)
                    .addFlashAttribute("jumlahError", errors.size())
                    .addFlashAttribute("errors", errors);
            return "redirect:hasil";
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileSmartTest.getInputStream()));
            String content;

            if((pakaiHeader != null && pakaiHeader)){
                content = reader.readLine();
            }

            while ((content = reader.readLine()) != null) {
                baris++;
                String[] data = content.split(",");
                if (data.length != 4) {
                    errors.add(new UploadError(baris, "Format data salah", content));
                    continue;
                }

                // bikin dan save pendaftar
                Pendaftar p = new Pendaftar();
                p.setNama(data[0]);
                p.setNoHp(data[1]);
                p.setEmail(data[2]);
                p.setKabupatenKota(kabupatenKota);
                p.setNamaAsalSekolah(idSekolah.getNama());
                p.setNomorRegistrasi(registrasiService.generateNomorRegistrasi());
                pendaftarDao.save(p);

                // bikin dan save hasil test
                HasilTest hasilTest = new HasilTest();
                hasilTest.setPendaftar(p);
                hasilTest.setJenisTest(JenisTest.SMART_TEST);
                hasilTest.setTanggalTest(tanggalTest);
                hasilTest.setUser(u);
                hasilTest.setKeterangan("Smart Test");

                try {
                    hasilTest.setNilai(new BigDecimal(data[3]));
                } catch (NumberFormatException ex) {
                    errors.add(new UploadError(baris, "Format nilai  salah", content));
                    continue;
                }

                Grade hasil = registrasiService.hitungGrade(new BigDecimal(data[3]));
                LOGGER.debug("ID GRADE :"+ hasil.getId());

                hasilTest.setGrade(hasil);

                hasilTestDao.save(hasilTest);


            }
        } catch (Exception err){
            LOGGER.warn(err.getMessage(), err);
            errors.add(new UploadError(0, "Format file salah", ""));
        }

        redirectAttrs
                .addFlashAttribute("jumlahBaris", baris)
                .addFlashAttribute("jumlahSukses", baris - errors.size())
                .addFlashAttribute("jumlahError", errors.size())
                .addFlashAttribute("errors", errors);

        return "redirect:hasil";
    }

    @GetMapping("/registrasi/smart/hasil")
    public void hasilFormUpload(){}


    @GetMapping("/registrasi/smart/list")
    public String defaultPage(@RequestParam(required = false)String search, String cari, Model m, Pageable smartPage){

        if(StringUtils.hasText(cari)) {
            m.addAttribute("cari", cari);
            List<JenisTest> smartTest = Arrays.asList(JenisTest.SMART_TEST);
            m.addAttribute("daftarSmart", hasilTestDao.cariByJenisTestSmartTestDanPendaftar(smartTest, "%"+cari.toLowerCase()+"%", smartPage));
        } else {
            m.addAttribute("daftarSmart", hasilTestDao.findByJenisTestIn(smartPage, JenisTest.SMART_TEST));
        }
        return "registrasi/smart/list";
    }
}

