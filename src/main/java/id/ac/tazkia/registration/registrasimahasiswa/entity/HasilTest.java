package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity @Data
public class HasilTest{

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne @JoinColumn (name="id_pendaftar")
    private Pendaftar pendaftar;

    @NotNull
    private BigDecimal nilai;

    @ManyToOne @JoinColumn (name="id_grade")
    private Grade grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private JenisTest jenisTest;
    
}
