package id.ac.tazkia.registration.registrasimahasiswa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TagihanController {

    @GetMapping("/biaya/tagihan/list")
    public void listTagihan(){}

    @GetMapping("/biaya/tagihan/form")
    public  void formTagihan(){}
}
