package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @Entity
public class Agen {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;


    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 150)
    private String namaCabang;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String noHp;

    @Column(nullable = false)
    private String alamatCabang;

    @Column(nullable = false)
    @NotNull
    @Size(min = 3, max = 150)
    private String penanggungJawab;

    private Boolean status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
