package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity @Data @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProgramStudi {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String nama;

    @NotEmpty @NotNull
    private String kodeBiaya;

    @NotEmpty @NotNull
    private String kodeSimak;
}
