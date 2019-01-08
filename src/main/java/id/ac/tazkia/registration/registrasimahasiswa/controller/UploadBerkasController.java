package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.BerkasDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.BerkasDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Berkas;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBerkas;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public  String hapus(@RequestParam("id") Berkas id ){
        berkasDao.delete(id);
        return "redirect:/registrasi/berkas/list";
    }

}
