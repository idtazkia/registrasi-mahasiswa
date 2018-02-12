package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.KabupatenKotaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.ProvinsiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KabupatenKotaController {

    @Autowired
    private KabupatenKotaDao kabupatenKotaDao;
    @Autowired private ProvinsiDao provinsiDao;

    @GetMapping({"/api/provinsi/{provinsi}/kabupaten", "/api/{provinsi}/kota"})
    public List<KabupatenKota> findByProvinsiAndName(@PathVariable String provinsi, @RequestParam String nama){
        return kabupatenKotaDao.findByProvinsiAndNamaContainingIgnoreCaseOrderByNama(provinsiDao.findOne(provinsi), nama);
    }

    @GetMapping("/api/kokabawal")
    public List<KabupatenKota> findByName(@RequestParam String nama){
        return kabupatenKotaDao.findByNamaContainingIgnoreCaseOrderByNama(nama);
    }

//uploadSmartTest
    @GetMapping("/api/cariKokab")
    @ResponseBody
    public Page<KabupatenKota> findByKokab(@RequestParam(required = false) String nama, Pageable page){
        if(!StringUtils.hasText(nama)) {
            return kabupatenKotaDao.findAll(page);
        }
        return kabupatenKotaDao.findByNamaContainingIgnoreCaseOrderById(nama, page);

    }
}