package id.ac.tazkia.registration.registrasimahasiswa.dto;

import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBerkas;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class BerkasDto {


    @NotNull
    private String pendaftar;

    @Enumerated(EnumType.STRING)
    private JenisBerkas jenisBerkas1;

    @NotNull
    private String fileBerkas1;

    @Enumerated(EnumType.STRING)
    private JenisBerkas jenisBerkas2;

    @NotNull
    private String fileBerkas2;

    @Enumerated(EnumType.STRING)
    private JenisBerkas jenisBerkas3;

    @NotNull
    private String fileBerkas3;

}
