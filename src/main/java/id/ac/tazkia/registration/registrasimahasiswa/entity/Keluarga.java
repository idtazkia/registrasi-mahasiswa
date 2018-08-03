package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "hub_keluarga")
public class Keluarga {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_pendaftar")
    private Pendaftar pendaftar;

    @NotNull
    private String nim;

    @NotNull
    private String nama;

    @NotNull
    private String hubungan;


}


