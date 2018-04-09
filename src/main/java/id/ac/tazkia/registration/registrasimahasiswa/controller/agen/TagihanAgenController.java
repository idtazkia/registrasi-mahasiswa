package id.ac.tazkia.registration.registrasimahasiswa.controller.agen;

import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarAgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.RekapTagihanAgen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.PendaftarAgen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TagihanAgenController {
    @Autowired private AgenDao agenDao;
    @Autowired private PendaftarAgenDao pendaftarAgenDao;

    @GetMapping ("/agen/tagihan/form")
    public void tagihanAgen(@RequestParam(value = "agen",required = false)String agen, Model m, Pageable page){

        if (agen != null && !agen.isEmpty()){
            Agen p= agenDao.findOne(agen);
            if (p != null){
//Tampil Nama Cabang
                m.addAttribute("view", p);
//
//rekap Pendaftar
                LocalDateTime mulai = LocalDateTime.now().minusMonths(1);
                LocalDateTime sampai = LocalDateTime.now();

                List<RekapTagihanAgen> hasil = pendaftarAgenDao.rekapTagihan(p, mulai, sampai);
                m.addAttribute("hasil", hasil);
///////
            }
        }
    }

}

