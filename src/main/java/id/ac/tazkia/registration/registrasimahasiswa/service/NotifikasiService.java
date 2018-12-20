package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.AgenDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.*;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class NotifikasiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotifikasiService.class);

    @Value("${kafka.topic.notifikasi}") private String topicNotifikasi;
    @Value("${notifikasi.registrasi.konfigurasi.pendaftaran}") private String konfigurasiNotifikasiPendaftaran;
    @Value("${notifikasi.registrasi.konfigurasi.reset-password}") private String konfigurasiNotifikasiResetPassword;
    @Value("${notifikasi.registrasi.konfigurasi.kartu-ujian}") private String getKonfigurasiNotifikasiKartuUjian;
    @Value("${notifikasi.registrasi.konfigurasi.grade}") private String getKonfigurasiNotifikasiHasilTest;
    @Value("${notifikasi.registrasi.konfigurasi.keterangan-lulus}") private String getKonfigurasiNotifikasiKeteranganLulus;
    @Value("${notifikasi.registrasi.konfigurasi.kartu-jpa}") private String getKonfigurasiNotifikasiKartuJpa;
    @Value("${notifikasi.registrasi.konfigurasi.reset-password-agen}") private String getKonfigurasiNotifikasiResetPasswordAgen;
    @Value("${notifikasi.registrasi.konfigurasi.it-reset-password}") private String getKonfigurasiNotifikasiReset;


    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private AgenDao agenDao;


    @Async
    public void kirimNotifikasiRegistrasi(Pendaftar p){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(konfigurasiNotifikasiPendaftaran)
                .email(p.getEmail())
                .mobile(p.getNoHp())
                .data(DataNotifikasiRegistrasi.builder()
                        .nama(p.getNama())
                        .nomor(p.getNomorRegistrasi())
                        .noHp(p.getNoHp())
                        .email(p.getEmail())
                        .sekolah(p.getNamaAsalSekolah())
                        .biaya("300."+p.getNomorRegistrasi().substring(p.getNomorRegistrasi().length() - 3))
                        .keterangan("Biaya Registrasi")
                        .rekening("Bank Syariah Mandiri 7031904018 a.n STEI Tazkia")
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiResetPassword(Pendaftar p, String passwordBaru){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(konfigurasiNotifikasiResetPassword)
                .email(p.getEmail())
                .mobile(p.getNoHp())
                .data(DataNotifikasiResetPassword.builder()
                        .nama(p.getNama())
                        .nomor(p.getNomorRegistrasi())
                        .noHp(p.getNoHp())
                        .email(p.getEmail())
                        .sekolah(p.getNamaAsalSekolah())
                        .username(p.getNomorRegistrasi())
                        .password(passwordBaru)
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak1("08561360444")
                        .namaKontak1("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak1("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiKartuUjian(DetailPendaftar p){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(getKonfigurasiNotifikasiKartuUjian)
                .email(p.getEmail())
                .mobile(p.getNoHp())
                .data(DataNotifikasiKartuUjian.builder()
                        .id(p.getPendaftar().getId())
                        .nama(p.getPendaftar().getNama())
                        .nomor(p.getPendaftar().getNomorRegistrasi())
                        .noHp(p.getNoHp())
                        .email(p.getEmail())
                        .sekolah(p.getPendaftar().getNamaAsalSekolah())
                        .prodi(p.getPendaftar().getProgramStudi().getNama())
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiHasilTest(HasilTest h){
        Pendaftar p = h.getPendaftar();
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(getKonfigurasiNotifikasiHasilTest)
                .email(p.getEmail())
                .data(DataNotifikasiPengumuman.builder()
                        .nama(p.getNama())
                        .nomor(p.getNomorRegistrasi())
                        .email(p.getEmail())
                        .grade(h.getGrade().getNama())
                        .sekolah(p.getNamaAsalSekolah())
                        .prodi(p.getProgramStudi().getNama())
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiKeteranganLulus(DetailPendaftar dp){
        Pendaftar p = dp.getPendaftar();
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(getKonfigurasiNotifikasiKeteranganLulus)
                .email(p.getEmail())
                .data(DataNotifikasiKeteranganLulus.builder()
                        .id(p.getId())
                        .nama(p.getNama())
                        .nomor(p.getNomorRegistrasi())
                        .email(p.getEmail())
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiJpa(DetailPendaftar p){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(getKonfigurasiNotifikasiKartuJpa)
                .email(p.getEmail())
                .data(DataNotifikasiJpa.builder()
                        .nama(p.getPendaftar().getNama())
                        .nomorRegistrasi(p.getPendaftar().getNomorRegistrasi())
                        .email(p.getEmail())
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiResetPasswordAgen(Agen agen, String passwordBaru){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(getKonfigurasiNotifikasiResetPasswordAgen)
                .email(agen.getEmail())
                .mobile(agen.getNoHp())
                .data(DataNotifikasiResetPasswordAgen.builder()
                        .namaCabang(agen.getNamaCabang())
                        .nomor(agen.getKode())
                        .noHp(agen.getNoHp())
                        .email(agen.getEmail())
                        .penanggungJawab(agen.getPenanggungJawab())
                        .username(agen.getKode())
                        .password(passwordBaru)
                        .namaKontak3("Irma")
                        .nomorKontak3("08159551299")
                        .namaKontak2("Furqon")
                        .nomorKontak2("08561360444")
                        .namaKontak1("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak1("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void resetPassword(Reset p) {
        User user = p.getUser();
        Pendaftar pendaftar = pendaftarDao.findByUser(user);
            NotifikasiReset notif = NotifikasiReset.builder().build().builder().build().builder()
                    .konfigurasi(getKonfigurasiNotifikasiReset)
                    .email(pendaftar.getEmail())
                    .data(DataReset.builder()
                            .code(p.getCode())
                            .nama(p.getUser().getUsername())
                            .build())
                    .build();

            try {
                kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
            } catch (Exception err) {
                LOGGER.warn(err.getMessage(), err);
            }
        }

    @Async
    public void resetPasswordAgen(Reset p) {
        User user = p.getUser();
        Agen agen = agenDao.findByUser(user);
        NotifikasiReset notif = NotifikasiReset.builder().build().builder().build().builder()
                .konfigurasi(getKonfigurasiNotifikasiReset)
                .email(agen.getEmail())
                .data(DataReset.builder()
                        .code(p.getCode())
                        .nama(p.getUser().getUsername())
                        .build())
                .build();

        try {
            kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @Async
    public void kirimNotifikasiHasilTestS2(HasilTest h){
        String hasil = "Lulus";
            Pendaftar p = h.getPendaftar();
            NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                    .konfigurasi(getKonfigurasiNotifikasiHasilTest)
                    .email(p.getEmail())
                    .data(DataNotifikasiPengumuman.builder()
                            .nama(p.getNama())
                            .nomor(p.getNomorRegistrasi())
                            .email(p.getEmail())
                            .grade(hasil)
                            .sekolah(p.getNamaAsalSekolah())
                            .prodi(p.getProgramStudi().getNama())
                            .namaKontak1("Irma")
                            .nomorKontak1("08159551299")
                            .namaKontak2("Furqon")
                            .nomorKontak2("08561360444")
                            .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                            .nomorKontak3("humas@tazkia.ac.id")
                            .build())
                    .build();

            try {
                kafkaTemplate.send(topicNotifikasi, objectMapper.writeValueAsString(notif));
            } catch (Exception err) {
                LOGGER.warn(err.getMessage(), err);
            }

    }
}
