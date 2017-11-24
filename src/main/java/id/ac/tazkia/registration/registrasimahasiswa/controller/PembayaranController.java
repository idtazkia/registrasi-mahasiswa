package id.ac.tazkia.registration.registrasimahasiswa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PembayaranController {

    @GetMapping("/biaya/pembayaran/form")
    public void form(){}

    @GetMapping("/biaya/pembayaran/list")
    public void list(){}

}
