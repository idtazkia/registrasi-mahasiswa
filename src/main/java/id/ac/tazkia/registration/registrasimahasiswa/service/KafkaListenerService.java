package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturResponse;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanResponse;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class KafkaListenerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListenerService.class);

    @Autowired private ObjectMapper objectMapper;
    @Autowired private TagihanService tagihanService;
    @Autowired private PendaftarDao pendaftarDao;

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
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

}
