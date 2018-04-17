package id.ac.tazkia.registration.registrasimahasiswa.controller.agen;

import id.ac.tazkia.registration.registrasimahasiswa.controller.RegistrasiDetailController;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanAgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagihanAgenController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired private TagihanAgenDao tagihanAgenDao;


    @GetMapping ("/agen/tagihan/list")
    public ModelMap listTagihanAgen(@RequestParam(value = "agen")Agen agen, Pageable page){

        return new ModelMap()
                .addAttribute("daftarTagihan", tagihanAgenDao.findByAgenOrderByTanggalTagihan(agen, page))
                .addAttribute("agen", agen);

    }



}

