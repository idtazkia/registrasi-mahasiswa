package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.BerkasDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.BerkasDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UploadBerkasController {
    private static final Logger logger = LoggerFactory.getLogger(UploadBerkasController.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    protected PendaftarDao pendaftarDao;
    @Value("${upload.folder}")
    private String uploadFolder;
    @Autowired
    private BerkasDao berkasDao;
    @Autowired
    private PembayaranDao pembayaranDao;

    @GetMapping("/registrasi/berkas/list")
    public void ListUploadBerkas(ModelMap model, Authentication currentUser, Pageable page){
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

        model.addAttribute("daftarBerkas",berkasDao.findByPendaftarOrderByJenisBerkas(p, page));

        Pembayaran pembayaran = pembayaranDao.findByTagihanPendaftarIdAndTagihanJenisBiayaId(p.getId(), "002");
        model.addAttribute("pembayaran",pembayaran );
    }

    @GetMapping("/registrasi/berkas/form")
    public void berkasForm(Model model, Authentication currentUser){

        BerkasDto berkas = new BerkasDto();
        model.addAttribute("berkas", berkas);

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
        model.addAttribute("pendaftar", p);


    }

    @PostMapping("/registrasi/berkas/form")
    public String  prosesUploadBerkas(@ModelAttribute @Valid BerkasDto berkasDto, BindingResult errors,
                                 MultipartFile fileBerkas1,MultipartFile fileBerkas2,MultipartFile fileBerkas3
            ,Model model,JenisBerkas jenisBerkas, Authentication currentUser) throws Exception{
        logger.debug("Authentication class : {}",currentUser.getClass().getName());

        if(currentUser == null){
            logger.warn("Current user is null");
        }

        String username = ((UserDetails)currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        logger.debug("User ID : {}", u.getId());
        if(u == null){
            logger.warn("Username {} not found in database ", username);
        }

        Pendaftar pendaftar = pendaftarDao.findByUser(u);
        logger.debug("Nama Pendaftar : "+pendaftar.getNama());
        if(pendaftar == null){
            logger.warn("Pendaftar not found for username {} ", username);
        }
        model.addAttribute("pendaftar", pendaftar);

        if (errors.hasErrors()) {
            logger.debug("Error upload bukti pembayaran : {}", errors.toString());
        }

// persiapan lokasi upload


        String idPeserta = pendaftar.getId();
        String lokasiUpload = uploadFolder + File.separator + idPeserta;
        logger.debug("Lokasi upload : {}", lokasiUpload);
        new File(lokasiUpload).mkdirs();

// Proses upload berkas.
        simpanBerkas(fileBerkas1, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas1());
        simpanBerkas(fileBerkas2, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas2());
        simpanBerkas(fileBerkas3, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas3());


        return "redirect:/registrasi/berkas/list";
    }

// Fungsi Upload Berkas
    private void simpanBerkas(MultipartFile berkasFile, Pendaftar pendaftar, BerkasDto berkasDto, String lokasiUpload, JenisBerkas jenisBerkas){
        try {
            if (berkasFile == null || berkasFile.isEmpty()) {
                logger.info("File berkas kosong, tidak diproses");
                return;
            }

            Berkas berkas = new Berkas();
            berkas.setPendaftar(pendaftar);
            berkas.setJenisBerkas(berkasDto.getJenisBerkas1());

            String namaFile = berkasFile.getName();
            String jenisFile = berkasFile.getContentType();
            String namaAsli = berkasFile.getOriginalFilename();
            Long ukuran = berkasFile.getSize();

            logger.debug("Nama File : {}", namaFile);
            logger.debug("Jenis File : {}", jenisFile);
            logger.debug("Nama Asli File : {}", namaAsli);
            logger.debug("Ukuran File : {}", ukuran);

            //memisahkan extensi
            String extension = "";

            int i = namaAsli.lastIndexOf('.');
            int p = Math.max(namaAsli.lastIndexOf('/'), namaAsli.lastIndexOf('\\'));

            if (i > p) {
                extension = namaAsli.substring(i + 1);
            }

            String idFile = UUID.randomUUID().toString();
            berkas.setFileBerkas(idFile + "." + extension);
            File tujuan = new File(lokasiUpload + File.separator + berkas.getFileBerkas());
            berkasFile.transferTo(tujuan);
            logger.debug("File sudah dicopy ke : {}", tujuan.getAbsolutePath());
            berkas.setJenisBerkas(jenisBerkas);
            berkasDao.save(berkas);
        } catch (Exception er) {
            logger.error(er.getMessage(), er);
        }

    }

    @GetMapping("/berkas/{berkas}/berkas/")
    public ResponseEntity<byte[]> tampilkanBerkas(@PathVariable Berkas berkas) throws Exception {
        String lokasiFile = uploadFolder + File.separator + berkas.getPendaftar().getId()
                + File.separator + berkas.getFileBerkas();
        logger.debug("Lokasi file bukti : {}", lokasiFile);

        try {
            HttpHeaders headers = new HttpHeaders();
            if (berkas.getFileBerkas().toLowerCase().endsWith("jpeg") || berkas.getFileBerkas().toLowerCase().endsWith("jpg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (berkas.getFileBerkas().toLowerCase().endsWith("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            }else if (berkas.getFileBerkas().toLowerCase().endsWith("pdf")) {
                headers.setContentType(MediaType.APPLICATION_PDF);
            }else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            byte[] data = Files.readAllBytes(Paths.get(lokasiFile));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (Exception err) {
            logger.warn(err.getMessage(), err);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/registrasi/berkas/hapus")
    public  String hapus(@RequestParam("id") Berkas id, Authentication currentUser) throws Exception {

        logger.debug("Authentication class : {}", currentUser.getClass().getName());

        if (currentUser == null) {
            logger.warn("Current user is null");
        }

        String username = ((UserDetails) currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        logger.debug("User ID : {}", u.getId());
        logger.debug("ROLE ID : {}", u.getRole().getId());
        if (u == null) {
            logger.warn("Username {} not found in database ", username);
        }

        berkasDao.delete(id);

        if (u.getRole().getId().equals("pendaftar")) {
            return "redirect:/registrasi/berkas/list";
        } else {
            return "redirect:/registrasi/berkas/view?pendaftar="+ id.getPendaftar().getId();
        }
    }

//Form Akademik
    @GetMapping("/registrasi/berkas/formAkademik")
    public void berkasFormAkademik(@RequestParam Pendaftar pendaftar, Model model){
        BerkasDto berkas = new BerkasDto();
        model.addAttribute("berkas", berkas);
        model.addAttribute("pendaftar", pendaftar);
    }

    @PostMapping("/registrasi/berkas/formAkademik")
    public String  prosesUploadBerkasAkademik(@ModelAttribute @Valid BerkasDto berkasDto, BindingResult errors,
                                      MultipartFile fileBerkas1,MultipartFile fileBerkas2,MultipartFile fileBerkas3
            ,Pendaftar pendaftar) throws Exception{

// persiapan lokasi upload


        String idPeserta = pendaftar.getId();
        String lokasiUpload = uploadFolder + File.separator + idPeserta;
        logger.debug("Lokasi upload : {}", lokasiUpload);
        new File(lokasiUpload).mkdirs();

// Proses upload berkas.
        simpanBerkas(fileBerkas1, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas1());
        simpanBerkas(fileBerkas2, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas2());
        simpanBerkas(fileBerkas3, pendaftar, berkasDto, lokasiUpload, berkasDto.getJenisBerkas3());


        return "redirect:/registrasi/berkas/view?pendaftar="+ pendaftar.getId();
    }

//
//Update Berkas

    @RequestMapping(value = "/registrasi/berkas/update", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "berkas", required = false) String berkas,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("berkas", new Berkas());

        if (berkas != null && !berkas.isEmpty()){
            Berkas berkas1= berkasDao.findById(berkas).get();
            if (berkas1 != null){
                m.addAttribute("berkas", berkas1);
            }
        }
        return "registrasi/berkas/update";
    }

    @RequestMapping(value = "/registrasi/berkas/update", method = RequestMethod.POST)
    public String  prosesUpdateBerkas(@ModelAttribute @Valid Berkas berkas, BindingResult errors,
                                      MultipartFile fileBerkas,String pendaftar,Authentication currentUser) throws Exception {

        logger.debug("Authentication class : {}", currentUser.getClass().getName());

        if (currentUser == null) {
            logger.warn("Current user is null");
        }

        String username = ((UserDetails) currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        logger.debug("User ID : {}", u.getId());
        logger.debug("ROLE ID : {}", u.getRole().getId());
        if (u == null) {
            logger.warn("Username {} not found in database ", username);
        }

        String idPeserta = pendaftar;

        String namaFile = fileBerkas.getName();
        String jenisFile = fileBerkas.getContentType();
        String namaAsli = fileBerkas.getOriginalFilename();
        Long ukuran = fileBerkas.getSize();

        logger.debug("Nama File : {}", namaFile);
        logger.debug("Jenis File : {}", jenisFile);
        logger.debug("Nama Asli File : {}", namaAsli);
        logger.debug("Ukuran File : {}", ukuran);

//        memisahkan extensi
        String extension = "";

        int i = namaAsli.lastIndexOf('.');
        int p = Math.max(namaAsli.lastIndexOf('/'), namaAsli.lastIndexOf('\\'));

        if (i > p) {
            extension = namaAsli.substring(i + 1);
        }

        String idFile = UUID.randomUUID().toString();
        String lokasiUpload = uploadFolder + File.separator + idPeserta;
        logger.debug("Lokasi upload : {}", lokasiUpload);
        new File(lokasiUpload).mkdirs();
        File tujuan = new File(lokasiUpload + File.separator + idFile + "." + extension);
        berkas.setFileBerkas(idFile + "." + extension);
        fileBerkas.transferTo(tujuan);
        logger.debug("File sudah dicopy ke : {}", tujuan.getAbsolutePath());
        logger.debug("Id Pendaftar : {}" , idPeserta);
        berkasDao.save(berkas);

        if (u.getRole().getId().equals("pendaftar")) {
            return "redirect:/registrasi/berkas/list";
        } else {
            return "redirect:/registrasi/berkas/view?pendaftar="+ idPeserta;
        }
    }
//////

    @GetMapping("/registrasi/berkas/view")
    public ModelMap viewBerkas(@RequestParam Pendaftar pendaftar, Pageable page){
        ModelMap mm = new ModelMap();
        mm.addAttribute("daftarBerkas",berkasDao.findByPendaftarOrderByJenisBerkas(pendaftar, page));
        mm.addAttribute("pendaftar", pendaftar);
        return mm;   }
}
