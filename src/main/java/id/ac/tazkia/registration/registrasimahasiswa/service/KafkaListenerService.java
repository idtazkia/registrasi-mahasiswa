package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import id.ac.tazkia.registration.registrasimahasiswa.dao.BankDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PembayaranDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturResponse;
import id.ac.tazkia.registration.registrasimahasiswa.dto.PembayaranTagihan;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanResponse;
import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service @Transactional
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);
    private static final DateTimeFormatter FORMATTER_ISO_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired private ObjectMapper objectMapper;
    @Autowired private TagihanService tagihanService;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private TagihanDao tagihanDao;
    @Autowired private PembayaranDao pembayaranDao;

    @KafkaListener(topics = "${kafka.topic.debitur.response}", group = "${spring.kafka.consumer.group-id}")
    public void handleDebiturResponse(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            DebiturResponse response = objectMapper.readValue(message, DebiturResponse.class);
            if (!response.getSukses()) {
                LOGGER.warn("Create debitur gagal : {}", response.getData());
                return;
            }
            Pendaftar p = pendaftarDao.findByNomorRegistrasi(response.getNomorDebitur());
            if (p == null) {
                LOGGER.warn("Pendaftar dengan nomor registrasi {} tidak ditemukan", response.getNomorDebitur());
                return;
            }
            tagihanService.createTagihanRegistrasi(p);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.response}", group = "${spring.kafka.consumer.group-id}")
    public void handleTagihanResponse(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            TagihanResponse response = objectMapper.readValue(message, TagihanResponse.class);
            if (!response.getSukses()) {
                LOGGER.warn("Create tagihan gagal : {}", response.getDebitur());
                return;
            }

            LOGGER.debug("Create tagihan untuk pendaftar {} sukses dengan nomor {}",
                    response.getDebitur(), response.getNomorTagihan());

            insertTagihanRegistrasi(response);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    @KafkaListener(topics = "${kafka.topic.tagihan.payment}", group = "${spring.kafka.consumer.group-id}")
    public void handlePayment(String message) {
        try {
            LOGGER.debug("Terima message : {}", message);
            PembayaranTagihan pt = objectMapper.readValue(message, PembayaranTagihan.class);

            Tagihan tagihan = tagihanDao.findByNomorTagihan(pt.getNomorTagihan());
            if (tagihan == null) {
                LOGGER.debug("Tagihan dengan nomor {} tidak ditemukan", pt.getNomorTagihan());
                return;
            }

            tagihan.setLunas(true);

            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setTagihan(tagihan);
            pembayaran.setNilai(pt.getNilaiPembayaran());
            pembayaran.setWaktuPembayaran(LocalDateTime.parse(pt.getWaktuPembayaran(), FORMATTER_ISO_DATE_TIME));

            Bank bank = new Bank();
            bank.setId(pt.getBank());
            pembayaran.setBank(bank);
            pembayaran.setBuktiPembayaran(pt.getReferensiPembayaran());

            tagihanDao.save(tagihan);
            pembayaranDao.save(pembayaran);

            LOGGER.debug("Pembayaran untuk tagihan {} berhasil disimpan", pt.getNomorTagihan());

        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }


    private void insertTagihanRegistrasi(TagihanResponse tagihanResponse) {
        Pendaftar pendaftar = pendaftarDao.findByNomorRegistrasi(tagihanResponse.getDebitur());
        if (pendaftar == null) {
            LOGGER.warn("Pendaftar dengan nomor registrasi {} tidak ditemukan", tagihanResponse.getDebitur());
        }

        Tagihan tagihan = new Tagihan();
        tagihan.setPendaftar(pendaftar);
        tagihan.setNomorTagihan(tagihanResponse.getNomorTagihan());
        tagihan.setLunas(false);
        tagihan.setTanggalTagihan(tagihanResponse.getTanggalTagihan().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tagihan.setNilai(tagihanResponse.getNilaiTagihan());
        tagihan.setKeterangan(tagihanResponse.getKeterangan());

        JenisBiaya pendaftaran = new JenisBiaya();
        pendaftaran.setId(AppConstants.JENIS_BIAYA_PENDAFTARAN);
        tagihan.setJenisBiaya(pendaftaran);

        tagihanDao.save(tagihan);
    }

}
