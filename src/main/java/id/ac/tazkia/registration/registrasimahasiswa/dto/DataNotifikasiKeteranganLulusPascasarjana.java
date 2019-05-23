package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataNotifikasiKeteranganLulusPascasarjana {
    private String id;
    private String nomor;
    private String nama;
    private String noHp;
    private String email;
    private String namaKontak2;
    private String namaKontak3;
    private String nomorKontak2;
    private String nomorKontak3;
}
