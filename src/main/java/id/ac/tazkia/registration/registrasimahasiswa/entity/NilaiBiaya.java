package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Data
public class NilaiBiaya {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_jenisbiaya")
    private JenisBiaya jenisBiaya;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_grade")
    private Grade grade;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_periode")
    private Periode periode;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_programstudi")
    private ProgramStudi programStudi;

    @Column(nullable = false)
    private BigDecimal nilai;


    @Column(columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime tanggalEdit;

    @ManyToOne
    @JoinColumn(name = "user_edit ")
    private User userEdit;
}
