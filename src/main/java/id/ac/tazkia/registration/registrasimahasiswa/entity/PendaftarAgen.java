package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity @Data
public class PendaftarAgen {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_agen")
    private Agen agen;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_pendaftar")
    private Pendaftar pendaftar;

    @NotNull @Column(columnDefinition = "DATE")
    private LocalDateTime tanggal = LocalDateTime.now();

    @NotNull @Enumerated(EnumType.STRING)
    private StatusTagihan statusTagihan = StatusTagihan.BELUM_DITAGIH;
}
