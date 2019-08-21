package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RegistrasiDetail;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;

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
    @Autowired private PendidikanDao pendidikanDao;
    @Autowired private PenghasilanDao penghasilanDao;
    @Autowired private PekerjaanDao pekerjaanDao;


    @ModelAttribute("pendidikanOrtu")
    public Iterable<Pendidikan> pendidikanOrtu(){return pendidikanDao.findAll();}

    @ModelAttribute("pekerjaanOrtu")
    public Iterable<Pekerjaan> pekerjaanOrtu(){return pekerjaanDao.findAll();}

    @ModelAttribute("penghasilanOrtu")
    public Iterable<Penghasilan> penghasilanOrtu(){return penghasilanDao.findAll();}

    @ModelAttribute("daftarProdi")
    public Iterable<ProgramStudi> daftarProdi(){
        return programStudiDao.findAll();
    }


    @RequestMapping(value = "/registrasi/nim/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = true) String id,
                              Model m){
        //defaultnya, isi dengan object baru
        Pendaftar p = pendaftarDao.findById(id).get();
        DetailPendaftar d = detailPendaftarDao.findByPendaftar(p);

        RegistrasiDetail detail = new RegistrasiDetail();
        BeanUtils.copyProperties(p,detail);
        detail.setDetailPendaftar(d);
        detail.setKokab(p.getKabupatenKota().getNama());
        detail.setProvinsi(p.getKabupatenKota().getProvinsi().getNama());
        detail.setPendaftar(p.getId());
        detail.setAsalSekolah(p.getNamaAsalSekolah());
        if (d != null) {
            detail.setTempatLahir(d.getTempatLahir());
            detail.setTanggalLahir(d.getTanggalLahir());
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
            detail.setProdiLama(d.getPendaftar().getProgramStudi());
        }
        m.addAttribute("detail",detail);


    }


    @PostMapping (value = "/registrasi/nim/form")
    public String prosesForm(@ModelAttribute @Valid RegistrasiDetail registrasiDetail,
                             @RequestParam(required = false) ProgramStudi prodiLama, BindingResult errors,
                             RedirectAttributes redirectAttributes){


            Pendaftar p = pendaftarDao.findById(registrasiDetail.getPendaftar()).get();


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
        System.out.println("prodi" + p.getProgramStudi());
        DetailPendaftar dep = detailPendaftarDao.findByPendaftar(p);
        System.out.println("prodi lama :" + prodiLama);
        System.out.println("prodi Baru :" + p.getProgramStudi());

        if (prodiLama != null) {
            if (prodiLama.getId() != p.getProgramStudi().getId()) {
                registrasiDetail.setNim(null);
                dep.setNim(registrasiDetail.getNim());
                dep.setStatus(StatusTagihan.N);
                System.out.println("SET NIM : " + registrasiDetail.getNim());
                detailPendaftarDao.save(dep);
            }
        }

        pendaftarDao.save(p);

        DetailPendaftar detailPendaftar = new DetailPendaftar();
//simpan
        logger.debug("NIM AWAL : " + registrasiDetail.getNim());

        if (!StringUtils.hasText(registrasiDetail.getNim())) {
            String formatNim = "19" + "1" + p.getProgramStudi().getKodeSimak();
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
            dp.setPendidikanAyah(registrasiDetail.getPendidikanAyah());
            dp.setPekerjaanAyah(registrasiDetail.getPekerjaanAyah());
            dp.setPendidikanIbu(registrasiDetail.getPendidikanIbu());
            dp.setPekerjaanIbu(registrasiDetail.getPekerjaanIbu());
            dp.setPenghasilanOrangtua(registrasiDetail.getPenghasilanOrangtua());
            dp.setStatus(StatusTagihan.N);
            detailPendaftarDao.save(dp);
            redirectAttributes.addFlashAttribute("detail", dp);

        }
        if (registrasiDetail.getDetailPendaftar() == null) {
            BeanUtils.copyProperties(registrasiDetail, detailPendaftar);
            detailPendaftar.setPendaftar(p);
            detailPendaftar.setJenisTest(registrasiDetail.getJenisTest());
            detailPendaftar.setPendidikanAyah(registrasiDetail.getPendidikanAyah());
            detailPendaftar.setPekerjaanAyah(registrasiDetail.getPekerjaanAyah());
            detailPendaftar.setPendidikanIbu(registrasiDetail.getPendidikanIbu());
            detailPendaftar.setPekerjaanIbu(registrasiDetail.getPekerjaanIbu());
            detailPendaftar.setPenghasilanOrangtua(registrasiDetail.getPenghasilanOrangtua());
            detailPendaftar.setStatus(StatusTagihan.N);
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
            m.addAttribute("daftarDetail", detailPendaftarDao.findByPendaftarNomorRegistrasiContainingOrNimContainingOrPendaftarNamaContainingIgnoreCaseAndNimNotNullOrderByPendaftarNomorRegistrasi(search,search, search, page));
        } else {
            m.addAttribute("daftarDetail", detailPendaftarDao.findByNimNotNull(page));
        }
    }


    @GetMapping("/simak/detail/csv")
    public void rekapPendaftarCsv(HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=Data_Pendaftar_Simak.csv");
        response.setContentType("text/csv");
        response.getWriter().println("no,nim,nama,program studi, kode prodi, jenis kelamin, agama, alamat, asal sekolah," +
                " provinsi, kokab, kode pos, email, no hp, no ktp, nisn, tempat lahir, tanggal lahir, status sipil, negara, tahun lulus, nama ayah, " +
                "agama ayah, pendidikan ayah, nama ibu, agama ibu, pendidikan ibu, email ortu, nohp ortu");

        Iterable<DetailPendaftar> dataPendaftar = detailPendaftarDao.findAll();

        Integer baris = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (DetailPendaftar p : dataPendaftar) {
            if (p.getNim() != null) {
                baris++;
                response.getWriter().print(baris);
                response.getWriter().print(",");
                response.getWriter().print(p.getNim());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getNama());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getProgramStudi().getNama());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getProgramStudi().getKodeSimak());
                response.getWriter().print(",");
                response.getWriter().print(p.getJenisKelamin());
                response.getWriter().print(",");
                response.getWriter().print(p.getAgamaAyah());
                response.getWriter().print(",");
                response.getWriter().print(p.getAlamatRumah());
                response.getWriter().print(",");
                response.getWriter().print(p.getAsalSekolah());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getKabupatenKota().getProvinsi().getId());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getKabupatenKota().getId());
                response.getWriter().print(",");
                response.getWriter().print(p.getKodePos());
                response.getWriter().print(",");
                response.getWriter().print(p.getEmail());
                response.getWriter().print(",");
                response.getWriter().print(p.getNoHp());
                response.getWriter().print(",");
                response.getWriter().print(p.getNoKtp());
                response.getWriter().print(",");
                response.getWriter().print(p.getNisn());
                response.getWriter().print(",");
                response.getWriter().print(p.getTempatLahir());
                response.getWriter().print(",");
                response.getWriter().print(p.getTanggalLahir());
                response.getWriter().print(",");
                response.getWriter().print(p.getStatusSipil());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendaftar().getNegara());
                response.getWriter().print(",");
                response.getWriter().print(p.getTahunLulusSekolah());
                response.getWriter().print(",");
                response.getWriter().print(p.getNamaAyah());
                response.getWriter().print(",");
                response.getWriter().print(p.getAgamaAyah());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendidikanAyah());
                response.getWriter().print(",");
                response.getWriter().print(p.getNamaIbu());
                response.getWriter().print(",");
                response.getWriter().print(p.getAgamaIbu());
                response.getWriter().print(",");
                response.getWriter().print(p.getPendidikanIbu());
                response.getWriter().print(",");
                response.getWriter().print(p.getEmailOrangtua());
                response.getWriter().print(",");
                response.getWriter().print(p.getNohpOrangtua());

                response.getWriter().println();
            }
        }

        response.getWriter().flush();
    }



    @GetMapping("/simak/xlsx")
    public void rekapSimakXlsx(HttpServletResponse response) throws Exception {

        String[] columns = {"no","nim","nama","program studi","kode prodi", "jenis kelamin", "agama", "alamat", "asal sekolah"," provinsi", "kokab", "kode pos", "email", "no hp", "no ktp"
                ,"nisn", "tempat lahir","tanggal lahir", "status sipil", "negara", "tahun lulus", "nama ayah",
                        "agama ayah", "Id pendidikan ayah","pendidikan ayah","nama ibu", "agama ibu",
                "Id pendidikan ibu","pendidikan ibu", "email ortu","nohp ortu"};

        Iterable<DetailPendaftar> dataPendaftar = detailPendaftarDao.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DetailPendaftar");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1 ;
        int baris = 1 ;

        for (DetailPendaftar p : dataPendaftar) {
            if (p.getNim() != null) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(baris++);
                row.createCell(1).setCellValue(p.getNim());
                row.createCell(2).setCellValue(p.getPendaftar().getNama());
                row.createCell(3).setCellValue(p.getPendaftar().getProgramStudi().getNama());
                row.createCell(4).setCellValue(p.getPendaftar().getProgramStudi().getKodeSimak());
                row.createCell(5).setCellValue(p.getJenisKelamin());
                row.createCell(6).setCellValue(p.getAgamaAyah());
                row.createCell(7).setCellValue(p.getAlamatRumah());
                row.createCell(8).setCellValue(p.getAsalSekolah());
                row.createCell(9).setCellValue(p.getPendaftar().getKabupatenKota().getProvinsi().getId());
                row.createCell(10).setCellValue(p.getPendaftar().getKabupatenKota().getId());
                row.createCell(11).setCellValue(p.getKodePos());
                row.createCell(12).setCellValue(p.getEmail());
                row.createCell(13).setCellValue(p.getNoHp());
                row.createCell(14).setCellValue(p.getNoKtp());
                row.createCell(15).setCellValue(p.getNisn());
                row.createCell(16).setCellValue(p.getTempatLahir());
                row.createCell(17).setCellValue(String.valueOf(p.getTanggalLahir()));
                row.createCell(18).setCellValue(p.getStatusSipil());
                row.createCell(19).setCellValue(p.getPendaftar().getNegara());
                row.createCell(20).setCellValue(p.getTahunLulusSekolah());
                row.createCell(21).setCellValue(p.getNamaAyah());
                row.createCell(22).setCellValue(p.getAgamaAyah());
                row.createCell(23).setCellValue(p.getPendidikanAyah().getId());
                row.createCell(24).setCellValue(p.getPendidikanAyah().getNama());
                row.createCell(25).setCellValue(p.getNamaIbu());
                row.createCell(26).setCellValue(p.getAgamaIbu());
                row.createCell(27).setCellValue(p.getPendidikanIbu().getId());
                row.createCell(28).setCellValue(p.getPendidikanIbu().getNama());
                row.createCell(29).setCellValue(p.getEmailOrangtua());
                row.createCell(30).setCellValue(p.getNohpOrangtua());
            }
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=Detail_Pendaftar.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

    }

    @GetMapping("/humas/xlsx")
    public void rekapHumasXlsx(HttpServletResponse response) throws Exception {

        String[] columns = {"no","nim","nama","program studi", "jenis kelamin", "agama", "alamat", "asal sekolah"," provinsi", "kokab", "kode pos", "email", "no hp", "no ktp"
                ,"nisn", "tempat lahir","tanggal lahir", "status sipil", "negara", "tahun lulus", "nama ayah",
                "agama ayah","Id pendidikan ayah" ,"pendidikan ayah","nama ibu", "agama ibu",
                "Id pendidikan ibu","pendidikan ibu", "email ortu","nohp ortu"};

        Iterable<DetailPendaftar> dataPendaftar = detailPendaftarDao.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DetailPendaftar");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1 ;
        int baris = 1 ;

        for (DetailPendaftar p : dataPendaftar) {
            if (p.getNim() != null) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(baris++);
                row.createCell(1).setCellValue(p.getNim());
                row.createCell(2).setCellValue(p.getPendaftar().getNama());
                row.createCell(3).setCellValue(p.getPendaftar().getProgramStudi().getNama());
                row.createCell(4).setCellValue(p.getJenisKelamin());
                row.createCell(5).setCellValue(p.getAgamaAyah());
                row.createCell(6).setCellValue(p.getAlamatRumah());
                row.createCell(7).setCellValue(p.getAsalSekolah());
                row.createCell(8).setCellValue(p.getPendaftar().getKabupatenKota().getProvinsi().getNama());
                row.createCell(9).setCellValue(p.getPendaftar().getKabupatenKota().getNama());
                row.createCell(10).setCellValue(p.getKodePos());
                row.createCell(11).setCellValue(p.getEmail());
                row.createCell(12).setCellValue(p.getNoHp());
                row.createCell(13).setCellValue(p.getNoKtp());
                row.createCell(14).setCellValue(p.getNisn());
                row.createCell(15).setCellValue(p.getTempatLahir());
                row.createCell(16).setCellValue(String.valueOf(p.getTanggalLahir()));
                row.createCell(17).setCellValue(p.getStatusSipil());
                row.createCell(18).setCellValue(p.getPendaftar().getNegara());
                row.createCell(19).setCellValue(p.getTahunLulusSekolah());
                row.createCell(20).setCellValue(p.getNamaAyah());
                row.createCell(21).setCellValue(p.getAgamaAyah());
                row.createCell(22).setCellValue(p.getPendidikanAyah().getId());
                row.createCell(23).setCellValue(p.getPendidikanAyah().getNama());
                row.createCell(24).setCellValue(p.getNamaIbu());
                row.createCell(25).setCellValue(p.getAgamaIbu());
                row.createCell(26).setCellValue(p.getPendidikanIbu().getId());
                row.createCell(27).setCellValue(p.getPendidikanIbu().getNama());
                row.createCell(28).setCellValue(p.getEmailOrangtua());
                row.createCell(29).setCellValue(p.getNohpOrangtua());
            }
        }

        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=Detail_Pendaftar.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();

    }


}
