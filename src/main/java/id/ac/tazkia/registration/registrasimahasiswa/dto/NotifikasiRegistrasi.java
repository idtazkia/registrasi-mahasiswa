package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class NotifikasiRegistrasi {
    private String email;
    private String mobile;
    private DataNotifikasiRegistrasi data;
}
