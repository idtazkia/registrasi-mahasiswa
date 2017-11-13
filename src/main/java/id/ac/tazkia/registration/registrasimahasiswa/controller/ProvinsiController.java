package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.ProvinsiDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProvinsiController {
    @Autowired
    private ProvinsiDao provinsiDao;

    @GetMapping("/api/provinsi")
    public List<Provinsi> findByName(@RequestParam String nama){
        return provinsiDao.findByNamaContainingIgnoreCaseOrderByNama(nama);
    }
}




