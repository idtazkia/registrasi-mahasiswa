package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.dao.PeriodeDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class PeriodeController {
    @Autowired
    private PeriodeDao pd;
//list
    @RequestMapping("/periode/list")
    public void daftarPeriode(@RequestParam(required = false)String nama, Model m, Pageable page){
        if(StringUtils.hasText(nama)) {
            m.addAttribute("nama", nama);
            m.addAttribute("daftarPeriode", pd.findByNamaContainingIgnoreCaseOrderByNama(nama, page));
        } else {
            m.addAttribute("daftarPeriode", pd.findAll(page));
        }
    }
/////
//Hapus Data
        @RequestMapping("/periode/hapus")
        public  String hapus(@RequestParam("id") Periode id ){
            pd.delete(id);
            return "redirect:list";
        }
//

//tampikan form
        @RequestMapping(value = "/periode/form", method = RequestMethod.GET)
        public String tampilkanForm(@RequestParam(value = "id", required = false) String id,
                                    Model m){
            //defaultnya, isi dengan object baru
            m.addAttribute("periode", new Periode());

            if (id != null && !id.isEmpty()){
                Periode p= pd.findById(id).get();
                if (p != null){
                    m.addAttribute("periode", p);
                }
            }
            return "periode/form";
        }
////
//simpan
        @RequestMapping(value = "/periode/form", method = RequestMethod.POST)
        public String prosesForm(@Valid Periode p, BindingResult errors){
            if(errors.hasErrors()){
                return "/periode/form";
            }
            pd.save(p);
            return "redirect:list";
        }
////




}
