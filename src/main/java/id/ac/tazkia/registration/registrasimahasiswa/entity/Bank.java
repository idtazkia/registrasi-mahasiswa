package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity @Data
public class Bank {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotEmpty @NotNull
    private String kodeBank;

    @NotEmpty @NotNull
    private String namaBank;

    @NotEmpty @NotNull
    private String nomorRekening;

    @NotEmpty @NotNull
    private String namaRekening;
}
