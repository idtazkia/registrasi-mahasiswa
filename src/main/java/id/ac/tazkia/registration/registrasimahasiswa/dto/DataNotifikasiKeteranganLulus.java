package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataNotifikasiKeteranganLulus {
    private String id;
    private String nomor;
    private String nama;
    private String noHp;
    private String email;
    private String namaKontak1;
    private String namaKontak2;
    private String namaKontak3;
    private String nomorKontak1;
    private String nomorKontak2;
    private String nomorKontak3;
}
