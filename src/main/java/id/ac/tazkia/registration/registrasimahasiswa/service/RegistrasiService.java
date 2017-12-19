package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.RoleDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.UserPasswordDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.Registrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service @Transactional
public class RegistrasiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrasiService.class);

    @Autowired private RunningNumberService runningNumberService;
    @Autowired private NotifikasiService notifikasiService;
    @Autowired private TagihanService tagihanService;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private RoleDao roleDao;
    @Autowired private UserDao userDao;
    @Autowired private UserPasswordDao userPasswordDao;
    @Autowired private PasswordEncoder passwordEncoder;

    public Pendaftar prosesPendaftaran(Registrasi registrasi, ProgramStudi ps, KabupatenKota kk){
        Pendaftar p = new Pendaftar();
        p.setProgramStudi(ps);
        p.setKabupatenKota(kk);

        BeanUtils.copyProperties(registrasi, p);
        p.setNomorRegistrasi(generateNomorRegistrasi());

        createUser(p);
        pendaftarDao.save(p);
        tagihanService.createTagihanPendaftaran(p);
        notifikasiService.kirimNotifikasiRegistrasi(p);

        return p;
    }

    public String resetPassword(Pendaftar p){
        User u = p.getUser();
        UserPassword up = userPasswordDao.findByUser(u);

        LOGGER.debug("userName = {}", u.getUsername());


        String passwordBaru = RandomStringUtils.randomAlphabetic(6);
        up.setPassword(passwordEncoder.encode(passwordBaru));

        LOGGER.debug("passwordBaru = {}", passwordBaru);

        userPasswordDao.save(up);
        //notifikasiService.kirimNotifikasiResetPassword(p,passwordBaru);
        return passwordBaru;
    }

    private void createUser(Pendaftar p) {
        Role rolePendaftar = roleDao.findOne(AppConstants.ID_ROLE_PENDAFTAR);

        User user = new User();
        user.setUsername(p.getNomorRegistrasi());
        user.setActive(false);
        user.setRole(rolePendaftar);
        userDao.save(user);

        UserPassword up = new UserPassword();
        up.setUser(user);
        up.setPassword(passwordEncoder.encode(RandomStringUtils.randomAlphabetic(6)));
        userPasswordDao.save(up);

        p.setUser(user);
    }

    private String generateNomorRegistrasi(){
        String tanggalSekarang = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        RunningNumber terbaru = runningNumberService.generate(tanggalSekarang);

        LOGGER.debug("Tanggal Sekarang : {}", tanggalSekarang);
        LOGGER.debug("Nomer Terakhir : {}", terbaru.getNomerTerakhir());

        String nomorRegistrasi = tanggalSekarang + String.format("%04d", terbaru.getNomerTerakhir());
        LOGGER.debug("Nomor Registrasi : {}", nomorRegistrasi);

        return nomorRegistrasi;
    }

}
