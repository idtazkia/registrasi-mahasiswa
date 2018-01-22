package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Data;

@Data
public class DebiturResponse {
    private Boolean sukses;
    private Object data;
    private String nomorDebitur;
}
