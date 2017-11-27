package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TagihanController {

    @Autowired private PendaftarDao pendaftarDao;

    @GetMapping("/api/pendaftar")
    @ResponseBody
    public Page<Pendaftar> findByName(@RequestParam(required = false) String nomorRegistrasi, Pageable page){
        if(!StringUtils.hasText(nomorRegistrasi)) {
            return pendaftarDao.findAll(page);
        }
        return pendaftarDao.findByNomorRegistrasiContainingIgnoreCaseOrderByNomorRegistrasi( nomorRegistrasi, page);
    }

    @GetMapping("/biaya/tagihan/list")
    public void listTagihan(){}

    @GetMapping("/biaya/tagihan/form")
    public  void formTagihan(){}
}
