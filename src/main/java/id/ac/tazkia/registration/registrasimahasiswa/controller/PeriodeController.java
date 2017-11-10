package id.ac.tazkia.registration.registrasimahasiswa.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PeriodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndViewDefiningException;

@Controller
public class PeriodeController {
    @Autowired
    private PeriodeDao pd;

    @RequestMapping("/periode/list")
    public void daftarPeriode(Model m){
        m.addAttribute("daftarPeriode", pd.findAll());
    }
}
