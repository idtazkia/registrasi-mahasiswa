package id.ac.tazkia.registration.registrasimahasiswa.dto;

import id.ac.tazkia.registration.registrasimahasiswa.entity.StatusTagihan;
import lombok.Data;

@Data
public class NimDto {

    private String nama;
    private String nim;
    private StatusTagihan status;
}
