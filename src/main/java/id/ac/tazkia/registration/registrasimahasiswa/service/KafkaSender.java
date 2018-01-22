package id.ac.tazkia.registration.registrasimahasiswa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.registration.registrasimahasiswa.dto.DebiturRequest;
import id.ac.tazkia.registration.registrasimahasiswa.dto.TagihanRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class KafkaSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSender.class);

    @Value("${kafka.topic.debitur.request}") private String kafkaTopicDebiturRequest;
    @Value("${kafka.topic.tagihan.request}") private String kafkaTopicTagihanRequest;

    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private ObjectMapper objectMapper;


    public void requestCreateDebitur(DebiturRequest request) {
        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            LOGGER.debug("Create Debitur Request : {}", jsonRequest);
            kafkaTemplate.send(kafkaTopicDebiturRequest, jsonRequest);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }

    public void requestCreateTagihan(TagihanRequest request) {
        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            LOGGER.debug("Create Tagihan Request : {}", jsonRequest);
            kafkaTemplate.send(kafkaTopicTagihanRequest, jsonRequest);
        } catch (Exception err) {
            LOGGER.warn(err.getMessage(), err);
        }
    }
}
