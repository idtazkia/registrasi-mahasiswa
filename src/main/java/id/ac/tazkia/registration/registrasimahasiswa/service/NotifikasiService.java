package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DataNotifikasiRegistrasi;
import id.ac.tazkia.registration.registrasimahasiswa.dto.NotifikasiRegistrasi;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
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

    @Value("${notifikasi.url}") private String urlNotifikasi;
    @Value("${notifikasi.username}") private String usernameNotifikasi;
    @Value("${notifikasi.password}") private String passwordNotifikasi;
    @Value("${kafka.topic.notifikasi}") private String registrasiTopic;
    @Value("${notifikasi.registrasi.konfigurasi}") private String konfigurasiNotifikasiRegistrasi;

    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private ObjectMapper objectMapper;


    @Async
    public void kirimNotifikasiRegistrasi(Pendaftar p){
        NotifikasiRegistrasi notif = NotifikasiRegistrasi.builder()
                .konfigurasi(konfigurasiNotifikasiRegistrasi)
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
                        .nomorKontak2("089696792628")
                        .namaKontak3("Panitia Penerimaan Mahasiswa Baru")
                        .nomorKontak3("humas@tazkia.ac.id")
                        .build())
                .build();

        try {
            kafkaTemplate.send(registrasiTopic, objectMapper.writeValueAsString(notif));
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }
}
