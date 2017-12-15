package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataNotifikasiResetPassword {
    private String nama;
    private String email;
    private String noHp;
    private String nomorRegistrasi;
    private String namaAsalSekolah;
    private String username;
    private String password;
    private String infoNama1;
    private String infoHandphone1;
    private String infoNama2;
    private String infoHandphone2;
    private String infoNama3;
    private String infoHandphone3;
}
