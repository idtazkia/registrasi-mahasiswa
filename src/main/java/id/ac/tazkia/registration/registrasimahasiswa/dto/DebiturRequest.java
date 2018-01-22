package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DebiturRequest {
    private String nomorDebitur;
    private String nama;
    private String email;
    private String noHp;
}
