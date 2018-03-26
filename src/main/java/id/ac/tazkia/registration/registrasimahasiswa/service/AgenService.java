package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.RoleDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserPasswordDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.AgenDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.flywaydb.core.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service @Transactional
public class AgenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiService.class);

    @Autowired
    private AgenDao agenDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPasswordDao userPasswordDao;


    public Agen prosesPendaftaranAgen(AgenDto agenDto, @RequestParam String password,@RequestParam String username) {
        Agen p = new Agen();
        p.setStatus(true);
        BeanUtils.copyProperties(agenDto, p);

        createUserAgen(p,password,username);
        agenDao.save(p);

        return p;
    }

    private void createUserAgen(Agen p, @RequestParam String password,@RequestParam String username) {
        Role roleAgen = roleDao.findOne(AppConstants.ID_ROLE_AGEN);

        User user = new User();
        user.setUsername(username);
        user.setActive(true);
        user.setRole(roleAgen);
        userDao.save(user);

        UserPassword up = new UserPassword();
        up.setUser(user);
        up.setPassword(passwordEncoder.encode(password));
        userPasswordDao.save(up);

        p.setUser(user);
    }
}