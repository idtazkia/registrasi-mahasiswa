package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.dto.DataNotifikasiRegistrasi;
import id.ac.tazkia.registration.registrasimahasiswa.dto.NotifikasiRegistrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Service
public class NotifikasiService {
    @Value("${notifikasi.url}") private String urlNotifikasi;
    @Value("${notifikasi.username}") private String usernameNotifikasi;
    @Value("${notifikasi.password}") private String passwordNotifikasi;

    private RestTemplate restTemplate;
    private RestTemplateBuilder builder;

    public NotifikasiService(RestTemplateBuilder builder){
        this.builder = builder;
    }

    @PostConstruct
    public void inisialisasiRestTemplate(){
        restTemplate = builder.basicAuthorization(usernameNotifikasi, passwordNotifikasi)
                .build();
    }

    @Async
    public void kirimNotifikasiRegistrasi(Pendaftar p){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
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
                        .rekening("BNI Syariah 1100319991 a.n Tazkia Cendekia")
                        .namaKontak1("Irma")
                        .nomorKontak1("08159551299")
                        .namaKontak2("Siti")
                        .nomorKontak2("085775470279")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        restTemplate.postForObject(urlNotifikasi, notif, Void.class);
    }
}
