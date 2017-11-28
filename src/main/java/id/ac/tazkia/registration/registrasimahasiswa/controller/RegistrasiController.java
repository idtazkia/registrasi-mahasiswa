package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.NotifikasiService;
import id.ac.tazkia.registration.registrasimahasiswa.service.RunningNumberService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class RegistrasiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiController.class);

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RoleDao roleDao;
    @Autowired private UserDao userDao;
    @Autowired private UserPasswordDao userPasswordDao;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private KabupatenKotaDao kabupatenKotaDao;
    @Autowired private RunningNumberService runningNumberService;
    @Autowired private NotifikasiService notifikasiService;
    @Autowired private ProgramStudiDao programStudiDao;

    @GetMapping("/registrasi/list")
    public void daftarPendaftaran(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarPendaftaran", pendaftarDao.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarPendaftaran", pendaftarDao.findAll(page));
        }
    }

    @GetMapping(value = "/registrasi/form")
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new Registrasi());
    }

    @PostMapping(value = "/registrasi/form")
    public String prosesForm(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        Pendaftar p = new Pendaftar();

        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findOne(registrasi.getIdKabupatenKota());
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        // load program studi
        ProgramStudi prodi = programStudiDao.findOne(registrasi.getProgramStudiPilihan());
        if(prodi == null){
            errors.reject("programStudiPilihan", "Program studi tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "/registrasi/form";
        }

        p.setKabupatenKota(kk);
        p.setProgramStudi(prodi);

        String nomorPendaftar = generateNomorRegistrasi();
        BeanUtils.copyProperties(registrasi, p);
        p.setNomorRegistrasi(nomorPendaftar);

        Role rolePendaftar = roleDao.findOne(AppConstants.ID_ROLE_PENDAFTAR);

        User user = new User();
        user.setUsername(nomorPendaftar);
        user.setActive(false);
        user.setRole(rolePendaftar);
        userDao.save(user);

        UserPassword up = new UserPassword();
        up.setUser(user);
        up.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(6)));
        userPasswordDao.save(up);

        p.setUser(user);
        pendaftarDao.save(p);
        notifikasiService.kirimNotifikasiRegistrasi(p);
        return "redirect:/selesai";

    }


    @GetMapping("/selesai")
    public  void  selesai(){ }

    private String generateNomorRegistrasi(){
        String tanggalSekarang = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        RunningNumber terbaru = runningNumberService.generate(tanggalSekarang);

        LOGGER.debug("Tanggal Sekarang : {}", tanggalSekarang);
        LOGGER.debug("Nomer Terakhir : {}", terbaru.getNomerTerakhir());

        String nomorRegistrasi = tanggalSekarang + String.format("%04d", terbaru.getNomerTerakhir());
        LOGGER.debug("Nomor Registrasi : {}", nomorRegistrasi);

        return nomorRegistrasi;
    }

}

