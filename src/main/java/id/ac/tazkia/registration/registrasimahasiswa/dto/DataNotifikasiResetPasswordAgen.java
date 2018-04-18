package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataNotifikasiResetPasswordAgen {
    private String namaCabang;
    private String email;
    private String noHp;
    private String nomor;
    private String penanggungJawab;
    private String username;
    private String password;
    private String namaKontak1;
    private String nomorKontak1;
    private String namaKontak2;
    private String nomorKontak2;
    private String namaKontak3;
    private String nomorKontak3;
}
