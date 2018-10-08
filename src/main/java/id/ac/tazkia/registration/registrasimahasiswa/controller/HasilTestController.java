package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.GradeDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.KeluargaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.HasilTestDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import id.ac.tazkia.registration.registrasimahasiswa.entity.HasilTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Keluarga;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.service.NotifikasiService;
import id.ac.tazkia.registration.registrasimahasiswa.service.RegistrasiService;
import id.ac.tazkia.registration.registrasimahasiswa.service.TagihanService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.ZoneId;

@Controller
public class HasilTestController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);


    @Autowired
    private PendaftarDao pendaftarDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private HasilTestDao hasilTestDao;
    @Autowired
    private RegistrasiService registrasiService;
    @Autowired
    private TagihanService tagihanService;
    @Autowired
    private NotifikasiService notifikasiService;
    @Autowired
    private KeluargaDao keluargaDao;

    @ModelAttribute("daftarGrade")
    public Iterable<Grade> daftarGrade(){return gradeDao.findAll(); }

    //tampikan form
    @RequestMapping(value = "/registrasi/hasil/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = true) String id,
                              @RequestParam(required = false) String error,
                              Model m){
        //defaultnya, isi dengan object baru
        HasilTestDto h = new HasilTestDto();
        m.addAttribute("hasil", h);
        m.addAttribute("error", error);

        Pendaftar p = pendaftarDao.findOne(id);
        m.addAttribute("pendaftar", p);
        if (p != null){
            h.setPendaftar(p);
        }

        HasilTest d = hasilTestDao.findByPendaftar(p);
        logger.debug("Nomor Registrasi :"+ p.getNomorRegistrasi());
        if (d != null){
            m.addAttribute("hasil", d);
        }
    }
////

    //simpan
    @RequestMapping(value = "registrasi/hasil/form", method = RequestMethod.POST)
    public String prosesForm(@Valid HasilTestDto hasilTestDto,  String nilai, BindingResult errors){
        if(errors.hasErrors()){
            return "/registrasi/hasil/form";
        }

        Grade hasil = registrasiService.hitungGrade(new BigDecimal(nilai));
        logger.debug("ID GRADE :"+ hasil.getId());

        HasilTest hasilTest = new HasilTest();
        Keluarga keluarga = new Keluarga();

        BeanUtils.copyProperties(hasilTestDto, hasilTest);
        hasilTest.setTanggalTest(hasilTestDto.getTanggalTest());
        hasilTest.setGrade(hasil);

        hasilTestDao.save(hasilTest);
        HasilTest h = hasilTestDao.findByPendaftar(hasilTest.getPendaftar());
        Pendaftar p = h.getPendaftar();

        notifikasiService.kirimNotifikasiHasilTest(h);

        logger.debug("NIM : "+ hasilTestDto.getNim());
//        if (!hasilTestDto.getNim().isEmpty() && hasilTestDto.getNama() != null) {
//            keluarga.setNim(hasilTestDto.getNim());
//            keluarga.setNama(hasilTestDto.getNama());
//            keluarga.setHubungan(AppConstants.HUBUNGAN_KEL);
//            keluarga.setPendaftar(h.getPendaftar());
//            keluargaDao.save(keluarga);
//            tagihanService.createTagihanDUdiskonUp(p, h, hasilTest.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//
//        }else{
//            tagihanService.createTagihanDaftarUlang(p, h, hasilTest.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        }

//        if(hasilTestDto.getNim().isEmpty() && hasilTestDto.getNim() == null && !StringUtils.hasText(hasilTestDto.getNim())) {
//            tagihanService.createTagihanDaftarUlang(p, h, hasilTest.getTanggalTest().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        }

        return "redirect:/registrasi/list";
    }

////

//List

    @GetMapping("registrasi/hasil/list")
    public String listHasilTest(@RequestParam(required = false)String search, Model m, Pageable page){
        if(StringUtils.hasText(search)) {
            m.addAttribute("search", search);
            m.addAttribute("daftarHasil", hasilTestDao.findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrGradeNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(search,search, search, page));
        } else {
            m.addAttribute("daftarHasil", hasilTestDao.findAll(page));
        }
        return "registrasi/hasil/list";
    }

    @GetMapping("/registrasi/hasil/csv")
    public void rekapPendaftarCsv(HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=Hasil Test.csv");
        response.setContentType("text/csv");
        response.getWriter().println("No,Nomor Registrasi,Nama,Kota/Kab Sekolah,No Hp,Email,Program Studi,Grade,Tanggal Test (Batch)");

        Iterable<HasilTest> dataPendaftar = hasilTestDao.findAll();

        Integer baris = 0;
        BigDecimal total = BigDecimal.ZERO;
        for (HasilTest hasilTest : dataPendaftar) {
            if (hasilTest.getPendaftar().getProgramStudi() != null) {
                baris++;
                response.getWriter().print(baris);
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getNomorRegistrasi());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getNama());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getKabupatenKota().getNama());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getNoHp());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getEmail());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getPendaftar().getProgramStudi().getNama());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getGrade().getNama());
                response.getWriter().print(",");
                response.getWriter().print(hasilTest.getTanggalTest());
                response.getWriter().println();
            }
        }

        response.getWriter().flush();
    }
}
