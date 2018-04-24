package id.ac.tazkia.registration.registrasimahasiswa.controller.agen;

import id.ac.tazkia.registration.registrasimahasiswa.controller.RegistrasiDetailController;
import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.AgenDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import id.ac.tazkia.registration.registrasimahasiswa.service.AgenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AgenController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrasiDetailController.class);

    @Autowired
    private AgenDao agenDao;
    @Autowired
    private AgenService agenService;
    @Autowired
    private UserDao userDao;

    @RequestMapping("/agen/list")
    public void daftarSekolah(@RequestParam(required = false) String nama, Model m,
                              @PageableDefault(size = 10, sort = "namaCabang", direction = Sort.Direction.ASC) Pageable page) {

        if (StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarAgen", agenDao.findByNamaCabangContainingIgnoreCaseOrderByNamaCabang(nama, page));
        } else {
            m.addAttribute("daftarAgen", agenDao.findAll(page));
        }

    }

    //tampikan form
    @RequestMapping(value = "/agen/form", method = RequestMethod.GET)
    public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                Model m) {
        //defaultnya, isi dengan object baru
        m.addAttribute("agen", new Agen());

        if (id != null && !id.isEmpty()) {
            Agen p = agenDao.findOne(id);
            if (p != null) {
                m.addAttribute("agen", p);
            }
        }
        return "agen/form";
    }
////

    @PostMapping("/agen/form")
    public String formAgen(@ModelAttribute @Valid AgenDto agenDto, BindingResult errors) {

        if (errors.hasErrors()) {
            return "agen/form";
        }
        agenService.prosesPendaftaranAgen(agenDto);

        return "redirect:/agen/list";
    }


    @PostMapping("/agen/nonaktif")
    public String processHapusForm(@RequestParam Agen agen) {
        if (agen == null) {
            logger.warn("Update Agen null");
            return "redirect:/agen/list";
        }

        Agen u = agenDao.findByUser(agen.getUser());
        u.getUser().setActive(false);
        agen.setStatus(false);
        agenDao.save(agen);
        return "redirect:/agen/list";
    }

    @PostMapping("/agen/aktif")
    public String processAktifForm(@RequestParam Agen agen) {
        if (agen == null) {
            logger.warn("Update Agen null");
            return "redirect:/agen/list";
        }

        Agen u = agenDao.findByUser(agen.getUser());
        u.getUser().setActive(true);
        agen.setStatus(true);
        agenDao.save(agen);
        return "redirect:/agen/list";
    }
}