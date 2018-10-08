package id.ac.tazkia.registration.registrasimahasiswa.controller;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.ResetDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserPasswordDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Reset;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import id.ac.tazkia.registration.registrasimahasiswa.entity.UserPassword;
import id.ac.tazkia.registration.registrasimahasiswa.service.NotifikasiService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
public class ResetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);


    @Autowired private UserDao userDao;
    @Autowired private UserPasswordDao userPasswordDao;
    @Autowired private ResetDao resetDao;
    @Autowired private NotifikasiService notifikasiService;
    @Autowired private PasswordEncoder passwordEncoder;



    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public void reset(){}

    @RequestMapping(value = "/reset-sukses", method = RequestMethod.GET)
    public void sukses(){}

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public void blank(){}


    @PostMapping(value = "/reset")
    public String reset(@Valid @RequestParam String user){
        User u = userDao.findByUsername(user);

        if(u == null){
            LOGGER.info("Reset password untuk username belum terdaftar : {}", user);
            return "redirect:reset-sukses";
        }


        Reset rp = resetDao.findByUser(u);

        if(rp == null){
            rp = new Reset();
            rp.setUser(u);
        }

        rp.setCode(RandomStringUtils.randomAlphabetic(6));
        rp.setExpired(LocalDate.now().plusDays(3));

        resetDao.save(rp);

        if (rp.getUser().getRole().getId().equals(AppConstants.ID_ROLE_PENDAFTAR)) {
            notifikasiService.resetPassword(rp);
        }

        if (rp.getUser().getRole().getId().equals( AppConstants.ID_ROLE_AGEN)){
            notifikasiService.resetPasswordAgen(rp);
        }
        return "redirect:reset-sukses";
    }

    @GetMapping(value = "/confirm")
    public String confirm(@RequestParam String code,Model m) {
        Reset resetPassword = resetDao.findByCode(code);

        if (resetPassword == null){
            return "redirect:/404";
        }

        if (code != null && !code.isEmpty()){
            Optional<UserPassword> userPassword= userPasswordDao.findById(resetPassword.getUser().getId());
            if (userPassword != null){
                m.addAttribute("confirm", userPassword);
                System.out.println("as" + userPassword);
            }

        }

        if(resetPassword.getExpired().isBefore(LocalDate.now())){
            return "redirect:404";
        }

        m.addAttribute("code", code);
        return "/confirm";
    }


    @PostMapping("/confirm")
    public String updatePassword(@RequestParam String code,
                                 @RequestParam String password){


        Reset resetPassword = resetDao.findByCode(code);


        UserPassword up = userPasswordDao.findByUser(resetPassword.getUser());
        if(up == null){
            LOGGER.info("User tidak ditemukan :" + up);
            up = new UserPassword();
            up.setUser(resetPassword.getUser());
        }
        up.setPassword(passwordEncoder.encode(password));


        userPasswordDao.save(up);
        return "redirect:login";


    }



    @GetMapping("/registrasi/reset/reset")
    public String ubahPassword(Model model, Authentication currentUser) {
        LOGGER.debug("Authentication class : {}", currentUser.getClass().getName());

        if (currentUser == null) {
            LOGGER.warn("Current user is null");
        }

        String username = ((UserDetails) currentUser.getPrincipal()).getUsername();
        User u = userDao.findByUsername(username);
        LOGGER.debug("User ID : {}", u.getId());
        if (u == null) {
            LOGGER.warn("Username {} not found in database ", username);
        }

        model.addAttribute("user", u);

        return "registrasi/reset/reset";
    }

    @PostMapping("/registrasi/reset/reset")
    public String ubahPassword(@RequestParam User id,
                                 @RequestParam String password,  RedirectAttributes redirectAttributes){

        UserPassword up = userPasswordDao.findByUser(id);
        if(up == null){
            LOGGER.info("User tidak ditemukan :" + up);
            up = new UserPassword();
            up.setUser(id);
        }
        up.setPassword(passwordEncoder.encode(password));

        redirectAttributes.addFlashAttribute("user", up);
        redirectAttributes.addFlashAttribute("password", password);

        System.out.println(password);
        userPasswordDao.save(up);
        return "redirect:/home";


    }
}
