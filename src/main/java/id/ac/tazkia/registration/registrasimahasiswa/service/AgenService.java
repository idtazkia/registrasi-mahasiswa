package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.*;
import id.ac.tazkia.registration.registrasimahasiswa.dto.AgenDto;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
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

import java.time.LocalDate;
import java.time.ZoneId;

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
    @Autowired
    private RegistrasiService registrasiService;
    @Autowired
    private PendaftarDao pendaftarDao;
    @Autowired
    private NotifikasiService notifikasiService;
    @Autowired
    private TagihanService tagihanService;
    @Autowired
    private PendaftarAgenDao pendaftarAgenDao;

//CreateAgen
    public Agen prosesPendaftaranAgen(AgenDto agenDto) {
        Agen p = new Agen();
        p.setStatus(true);
        BeanUtils.copyProperties(agenDto, p);

        p.setKode(9 +registrasiService.generateNomorRegistrasi());

        createUserAgen(p);
        agenDao.save(p);
        aktivasiUserAgen(p);

        return p;
    }

    private void createUserAgen(Agen agen) {
        Role roleAgen = roleDao.findOne(AppConstants.ID_ROLE_AGEN);

        User user = new User();
        user.setUsername(agen.getKode());
        user.setActive(true);
        user.setRole(roleAgen);
        userDao.save(user);

        UserPassword up = new UserPassword();
        up.setUser(user);
        up.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(6)));
        userPasswordDao.save(up);

        agen.setUser(user);
    }

    public String aktivasiUserAgen(Agen agen){
        User u = agen.getUser();
        UserPassword up = userPasswordDao.findByUser(u);

        LOGGER.debug("userName = {}", u.getUsername());

        String passwordBaru = RandomStringUtils.randomAlphabetic(6);
        up.setPassword(passwordEncoder.encode(passwordBaru));

        LOGGER.debug("passwordBaruAgen = {}", passwordBaru);

        u.setActive(true);
        userDao.save(u);
        userPasswordDao.save(up);
        notifikasiService.kirimNotifikasiResetPasswordAgen(agen,passwordBaru);
        return passwordBaru;
    }
//
////Create Pendaftar
    public Pendaftar prosesDaftar(Registrasi registrasi, ProgramStudi ps, KabupatenKota kk){
        Pendaftar p = new Pendaftar();
        p.setProgramStudi(ps);
        p.setKabupatenKota(kk);

        BeanUtils.copyProperties(registrasi, p);

        if (!org.springframework.util.StringUtils.hasText(registrasi.getId())) {
            p.setNomorRegistrasi(registrasiService.generateNomorRegistrasi());
        } else {
            p.setNomorRegistrasi(registrasi.getNomorRegistrasi());
        }
        registrasi.setNomorRegistrasi(p.getNomorRegistrasi());
        registrasi.setId(p.getId());


        createUserAktif(p);
        pendaftarDao.save(p);
        notifikasiService.kirimNotifikasiRegistrasi(p);
        tagihanService.prosesTagihanPendaftaran(p);

        return p;
    }

//createUsernamePassword
    private void createUserAktif(Pendaftar p) {
        Role rolePendaftar = roleDao.findOne(AppConstants.ID_ROLE_PENDAFTAR);

        User user = new User();
        user.setUsername(p.getNomorRegistrasi());
        user.setActive(true);
        user.setRole(rolePendaftar);
        userDao.save(user);

        UserPassword up = new UserPassword();
        up.setUser(user);
        String passwordBaru = RandomStringUtils.randomAlphabetic(6);
        up.setPassword(passwordEncoder.encode(passwordBaru));

        userPasswordDao.save(up);
        p.setUser(user);
        notifikasiService.kirimNotifikasiResetPassword(p,passwordBaru);

    }

}