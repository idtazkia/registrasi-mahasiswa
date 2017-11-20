package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
}
