package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Data
public class Pembayaran {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tagihan")
    private Tagihan tagihan;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_bank")
    private Bank bank;

    @NotNull
    private LocalDateTime waktuPembayaran = LocalDateTime.now();

    private String buktiPembayaran;

    @NotNull @Enumerated(EnumType.STRING)
    private CaraPembayaran caraPembayaran;

    @NotNull @Min(0)
    private BigDecimal nilai;
}
