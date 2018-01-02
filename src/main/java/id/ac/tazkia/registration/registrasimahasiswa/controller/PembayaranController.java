package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.BankDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Bank;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @ModelAttribute("daftarBank")
    public Iterable<Bank> daftarBank(){
        return bankDao.findAll();
    }

    @RequestMapping(value = "/biaya/pembayaran/form", method = RequestMethod.GET)
    public void tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m){
        //defaultnya, isi dengan object baru
        m.addAttribute("bayar", new Tagihan());

        if (id != null && !id.isEmpty()){
            Tagihan p= tagihanDao.findOne(id);
            if (p != null){
                Pembayaran x = new Pembayaran();
                x.setNilai(p.getNilai());
                x.setTagihan(p);
                m.addAttribute("bayar", x);
            }

        }
        return "/biaya/pembayaran/form";
    }

    @RequestMapping(value = "/biaya/pembayaran/form", method = RequestMethod.POST)
    public String prosesForm(@ModelAttribute @Valid Pembayaran pembayaran, BindingResult errors,
                             MultipartFile buktiPembayaran) throws Exception{

        String idPeserta = pembayaran.getTagihan().getPendaftar().getId();

        String namaFile = buktiPembayaran.getName();
        String jenisFile = buktiPembayaran.getContentType();
        String namaAsli = buktiPembayaran.getOriginalFilename();
        Long ukuran = buktiPembayaran.getSize();

        LOGGER.debug("Nama File : {}", namaFile);
        LOGGER.debug("Jenis File : {}", jenisFile);
        LOGGER.debug("Nama Asli File : {}", namaAsli);
        LOGGER.debug("Ukuran File : {}", ukuran);

//        memisahkan extensi
        String extension = "";

        int i = namaAsli.lastIndexOf('.');
        int p = Math.max(namaAsli.lastIndexOf('/'), namaAsli.lastIndexOf('\\'));

        if (i > p) {
            extension = namaAsli.substring(i+1);
        }


        String idFile = UUID.randomUUID().toString();
        String lokasiUpload = uploadFolder+ File.separator + idPeserta;
        LOGGER.debug("Lokasi upload : {}",lokasiUpload);
        new File(lokasiUpload).mkdirs();
        File tujuan = new File(lokasiUpload + File.separator + idFile + "." + extension);
        pembayaran.setBuktiPembayaran(idFile+"."+extension);
        buktiPembayaran.transferTo(tujuan);
        LOGGER.debug("File sudah dicopy ke : {}",tujuan.getAbsolutePath());

        Tagihan tagihan = pembayaran.getTagihan();
        tagihan.setLunas(true);
        tagihanDao.save(tagihan);

        User user = pembayaran.getTagihan().getPendaftar().getUser();
        user.setActive(true);
        userDao.save(user);


        LOGGER.debug("Bank : {}",pembayaran.getBank());


        pembayaranDao.save(pembayaran);
        return "redirect:/registrasi/list";
    }

    @RequestMapping("/biaya/pembayaran/list")
    public void  list(@RequestParam(value = "id",required = false)String idTagihan, Model m, Pageable page){
        if(StringUtils.hasText(idTagihan)) {
            m.addAttribute("nama", idTagihan);
            Tagihan t = tagihanDao.findOne(idTagihan);
            m.addAttribute("pembayaran", pembayaranDao.findByTagihan(t));
        } else {
            m.addAttribute("pembayaran", pembayaranDao.findAll(page));
        }

    }


    @GetMapping("/pembayaran/{pembayaran}/bukti/")
    public ResponseEntity<byte[]> tampilkanBuktiPembayaran(@PathVariable Pembayaran pembayaran) throws Exception{
        String lokasiFile = uploadFolder + File.separator + pembayaran.getTagihan().getPendaftar().getId()
                + File.separator + pembayaran.getBuktiPembayaran();
        LOGGER.debug("Lokasi file bukti : {}",lokasiFile);

        try {
            HttpHeaders headers = new HttpHeaders();
            if(pembayaran.getBuktiPembayaran().toLowerCase().endsWith("jpeg") || pembayaran.getBuktiPembayaran().toLowerCase().endsWith("jpg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if(pembayaran.getBuktiPembayaran().toLowerCase().endsWith("png")){
                headers.setContentType(MediaType.IMAGE_PNG);
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            byte[] data = Files.readAllBytes(Paths.get(lokasiFile));
            return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        } catch (Exception err){
            LOGGER.warn(err.getMessage(), err);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}

