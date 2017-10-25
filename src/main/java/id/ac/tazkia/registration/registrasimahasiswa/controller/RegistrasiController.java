package id.ac.tazkia.registration.registrasimahasiswa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;
import com.github.mkopylec.recaptcha.validation.ValidationResult;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrasiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiController.class);

    @Autowired
    private RecaptchaValidator recaptchaValidator;

    @Autowired private ObjectMapper objectMapper;

    @PostMapping("/registrasi")
    public String prosesRegistrasi(@ModelAttribute @Valid Registrasi reg, BindingResult errors, SessionStatus status, HttpServletRequest request) throws JsonProcessingException {
        ValidationResult hasil = recaptchaValidator.validate(request);
        if(!hasil.isSuccess()){
            LOGGER.warn("Captcha Validation Failed : {}", hasil.getErrorCodes());
            return "error";
        }

        LOGGER.debug("Data Registrasi : {}", objectMapper.writeValueAsString(reg));
        return "hasil";
    }
}
