package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataReset {
    private String code;
    private String email;
    private String nama;
}
