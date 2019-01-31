package id.ac.tazkia.registration.registrasimahasiswa.controller;


import id.ac.tazkia.registration.registrasimahasiswa.dao.DetailPendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.HasilTestDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    PendaftarDao pendaftarDao;
    @Autowired
    DetailPendaftarDao detailPendaftarDao;
    @Autowired
    PembayaranDao pembayaranDao;
    @Autowired
    HasilTestDao hasilTestDao;

    @GetMapping("/home")
    public void home(Model model){
        model.addAttribute("cPendaftar", pendaftarDao.countPendaftarByProgramStudiNotNullAndStatusTrue());
        model.addAttribute("cDetail", detailPendaftarDao.countDetailPendaftarByPendaftarNotNull());
        model.addAttribute("cDaftar", pembayaranDao.countPembayaranByTagihanJenisBiayaId("002"));
        model.addAttribute("cHasil", hasilTestDao.countHasilTestByPendaftarNotNull());
    }
}
