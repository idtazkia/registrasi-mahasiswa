package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.KabupatenKotaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.ProgramStudiDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RegistrasiDetail;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class GenerateNimController{
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired
    private DetailPendaftarDao detailPendaftarDao;
    @Autowired
    private PendaftarDao pendaftarDao;
    @Autowired
    private ProgramStudiDao programStudiDao;
    @Autowired
    private RegistrasiService registrasiService;
    @Autowired
    KabupatenKotaDao kabupatenKotaDao;

    @ModelAttribute("daftarProdi")
    public Iterable<ProgramStudi> daftarProdi(){
        return programStudiDao.findAll();
    }


    @RequestMapping(value = "/registrasi/nim/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = true) String id,
                              Model m){
        //defaultnya, isi dengan object baru
        Pendaftar p = pendaftarDao.findOne(id);
        DetailPendaftar d = detailPendaftarDao.findByPendaftar(p);

        RegistrasiDetail detail = new RegistrasiDetail();
        BeanUtils.copyProperties(p,detail);
        detail.setDetailPendaftar(d);
        detail.setKokab(p.getKabupatenKota().getNama());
        detail.setProvinsi(p.getKabupatenKota().getProvinsi().getNama());
        detail.setPendaftar(p.getId());
        detail.setAsalSekolah(p.getNamaAsalSekolah());
        if (d != null) {
            detail.setTtl(d.getTtl());
            detail.setJenisKelamin(d.getJenisKelamin());
            detail.setGolonganDarah(d.getGolonganDarah());
            detail.setNoKtp(d.getNoKtp());
            detail.setAlamatRumah(d.getAlamatRumah());
            detail.setProvinsi(d.getProvinsi());
            detail.setKokab(d.getKokab());
            detail.setKodePos(d.getKodePos());
            detail.setNoHp(d.getNoHp());
            detail.setEmail(d.getEmail());
            detail.setJurusanSekolah(d.getJurusanSekolah());
            detail.setNisn(d.getNisn());
            detail.setTahunLulusSekolah(d.getTahunLulusSekolah());
            detail.setPekerjaanPribadi(d.getPekerjaanPribadi());
            detail.setPenghasilanPribadi(d.getPenghasilanPribadi());
            detail.setStatusSipil(d.getStatusSipil());
            detail.setNamaAyah(d.getNamaAyah());
            detail.setAgamaAyah(d.getAgamaAyah());
            detail.setPendidikanAyah(d.getPendidikanAyah());
            detail.setPekerjaanAyah(d.getPekerjaanAyah());
            detail.setNamaIbu(d.getNamaIbu());
            detail.setAgamaIbu(d.getAgamaIbu());
            detail.setPendidikanIbu(d.getPendidikanIbu());
            detail.setPekerjaanIbu(d.getPekerjaanIbu());
            detail.setAlamatOrangtua(d.getAlamatOrangtua());
            detail.setKokabOrangtua(d.getKokabOrangtua());
            detail.setNohpOrangtua(d.getNohpOrangtua());
            detail.setEmailOrangtua(d.getEmailOrangtua());
            detail.setPenghasilanOrangtua(d.getPenghasilanOrangtua());
            detail.setJumlahTanggungan(d.getJumlahTanggungan());
            detail.setRencanaBiaya(d.getRencanaBiaya());
            detail.setJenisTest(d.getJenisTest());
            detail.setNim(d.getNim());
        }
        m.addAttribute("detail",detail);



    }


    @PostMapping (value = "/registrasi/nim/form")
    public String prosesForm(@ModelAttribute @Valid RegistrasiDetail registrasiDetail, @RequestParam(required = false) Pendaftar pendaftar, BindingResult errors,
                             RedirectAttributes redirectAttributes){


            Pendaftar p = pendaftarDao.findOne(registrasiDetail.getPendaftar());


            System.out.println("Nama :"+ p.getNama());
            p.setId(registrasiDetail.getPendaftar());
            p.setNomorRegistrasi(p.getNomorRegistrasi());
            p.setNama(registrasiDetail.getNama());
            p.setNamaAsalSekolah(registrasiDetail.getAsalSekolah());
            p.setNoHp(registrasiDetail.getNoHp());
            p.setKabupatenKota(p.getKabupatenKota());
            p.setProgramStudi(registrasiDetail.getProgramStudi());
            p.setPemberiRekomendasi(p.getPemberiRekomendasi());
            p.setNamaPerekomendasi(p.getNamaPerekomendasi());
            p.setUser(p.getUser());
            p.setKonsentrasi(p.getKonsentrasi());
            pendaftarDao.save(p);

        DetailPendaftar detailPendaftar = new DetailPendaftar();
//simpan
        logger.debug("NIM AWAL : " + registrasiDetail.getNim());

        if (!StringUtils.hasText(registrasiDetail.getNim())) {
            String formatNim = "18" + "1" + p.getProgramStudi().getKodeSimak();
            registrasiDetail.setNim(registrasiService.generateNim(formatNim));
            System.out.println("Nim :" + registrasiDetail.getNim());
        } else {
            registrasiDetail.setNim(registrasiDetail.getNim());
        }

        if (registrasiDetail.getDetailPendaftar() != null){
            DetailPendaftar dp = detailPendaftarDao.findByPendaftar(p);
            BeanUtils.copyProperties(registrasiDetail,dp);
            dp.setPendaftar(p);
            dp.setJenisTest(registrasiDetail.getJenisTest());
            detailPendaftarDao.save(dp);
            redirectAttributes.addFlashAttribute("detail", dp);

        }
        if (registrasiDetail.getDetailPendaftar() == null) {
            BeanUtils.copyProperties(registrasiDetail, detailPendaftar);
            detailPendaftar.setPendaftar(p);
            detailPendaftar.setJenisTest(registrasiDetail.getJenisTest());

            detailPendaftarDao.save(detailPendaftar);

            redirectAttributes.addFlashAttribute("detail", detailPendaftar);
        }
        return "redirect:selesai";
    }

    @GetMapping("/registrasi/nim/selesai")
    public void selesaiReset(){ }

    @GetMapping("/registrasi/nim/list")
    public void daftarDetail(@RequestParam(required = false) String search, Model m, Pageable page) {
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarDetail", detailPendaftarDao.findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseAndNimNotNullOrderByPendaftarNomorRegistrasi(search, search, page));
        } else {
            m.addAttribute("daftarDetail", detailPendaftarDao.findByNimNotNull(page));
        }
    }
}
