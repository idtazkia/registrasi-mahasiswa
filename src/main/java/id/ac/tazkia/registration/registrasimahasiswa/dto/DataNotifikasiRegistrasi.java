package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DataNotifikasiRegistrasi {
    private String nomor;
    private String nama;
    private String biaya;
    private String rekening;
    private String keterangan;
    private String namaKontak1;
    private String namaKontak2;
    private String namaKontak3;
    private String nomorKontak1;
    private String nomorKontak2;
    private String nomorKontak3;
}
