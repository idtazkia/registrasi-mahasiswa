package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.KabupatenKotaDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.ProvinsiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}