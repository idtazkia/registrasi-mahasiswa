package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Data
public class TagihanAgen {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_agen")
    private Agen agen;

    @NotNull @NotEmpty
    private String nomorTagihan;

    @NotNull @Column(columnDefinition = "DATE")
    private LocalDate tanggalTagihan;

    @NotNull @NotEmpty
    private String keterangan;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_jenisbiaya")
    private JenisBiaya jenisBiaya;

    @NotNull @Min(0)
    private BigDecimal nilai;

    @NotNull
    private Boolean lunas = Boolean.FALSE;
}
