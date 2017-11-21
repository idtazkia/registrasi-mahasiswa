package id.ac.tazkia.registration.registrasimahasiswa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String defaultPage(){
        return "/index";
    }

    @GetMapping("/home")
    public void home(){}
}
