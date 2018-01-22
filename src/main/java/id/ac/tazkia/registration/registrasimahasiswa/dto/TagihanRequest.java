package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data @Builder
public class TagihanRequest {
    private String jenisTagihan;
    private String debitur;
    private BigDecimal nilaiTagihan;
    private Date tanggalJatuhTempo;
    private String keterangan;
}
