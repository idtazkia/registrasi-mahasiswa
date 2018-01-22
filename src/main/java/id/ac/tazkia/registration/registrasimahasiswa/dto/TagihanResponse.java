package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TagihanResponse {
    private Boolean sukses;
    private String debitur;
    private String jenisTagihan;
    private BigDecimal nilaiTagihan;
    private Date tanggalTagihan;
    private Date tanggalJatuhTempo;
    private String nomorTagihan;
    private String keterangan;
}
