package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.KabupatenKotaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.RegistrasiAwalDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RunningNumber;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.service.RunningNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class RegistrasiAwalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiAwalController.class);
    @Autowired
    private RegistrasiAwalDao ra;

    @Autowired private KabupatenKotaDao kabupatenKotaDao;

    @Autowired private RunningNumberService runningNumberService;


    @RequestMapping(value = "/registrasi/pendaftar", method = RequestMethod.GET)
    public void tampilkanForm(Model model){
        model.addAttribute("registrasi", new Registrasi());
    }

    @RequestMapping(value = "/registrasi/pendaftar", method = RequestMethod.POST)
    public String prosesForm(@ModelAttribute @Valid Registrasi registrasi, BindingResult errors, SessionStatus status){
        Pendaftar p = new Pendaftar();

        // load kabupaten kota
        KabupatenKota kk = kabupatenKotaDao.findOne(registrasi.getIdKabupatenKota());
        if(kk == null){
            errors.reject("idKabupatenKota", "Data kabupaten tidak ada dalam database");
        }

        if(errors.hasErrors()){
            return "/registrasi/pendaftar";
        }

        p.setKabupatenKota(kk);

        String nomorPendaftar = generateNomorRegistrasi();
        BeanUtils.copyProperties(registrasi, p);
        p.setNomorRegistrasi(nomorPendaftar);

        ra.save(p);
        return "redirect:/selesai";

    }

    private String generateNomorRegistrasi(){
        String tanggalSekarang = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        RunningNumber terbaru = runningNumberService.generate(tanggalSekarang);

        LOGGER.debug("Tanggal Sekarang : {}", tanggalSekarang);
        LOGGER.debug("Nomer Terakhir : {}", terbaru.getNomerTerakhir());

        String nomorRegistrasi = tanggalSekarang + String.format("%04d", terbaru.getNomerTerakhir());
        LOGGER.debug("Nomor Registrasi : {}", nomorRegistrasi);

        return nomorRegistrasi;
    }

}

