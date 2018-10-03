package id.ac.tazkia.registration.registrasimahasiswa.dto;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HasilTestDto {
    private  String id;

    private Pendaftar pendaftar;

    private BigDecimal nilai;

    private JenisTest jenisTest;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "DATE")
    private Date tanggalTest;

    private String nim;

    private String nama;

    private String keterangan;

}
