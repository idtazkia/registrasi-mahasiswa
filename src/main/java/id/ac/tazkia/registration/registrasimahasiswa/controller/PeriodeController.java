package id.ac.tazkia.registration.registrasimahasiswa.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PeriodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndViewDefiningException;

@Controller
public class PeriodeController {
    @Autowired
    private PeriodeDao pd;

    @RequestMapping("/periode/list")
    public void daftarPeriode(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarPeriode", pd.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarPeriode", pd.findAll(page));
        }
    }
}
