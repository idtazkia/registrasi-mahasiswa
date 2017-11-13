package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class Registrasi {
    @NotNull @NotEmpty
    private String nama;
    @Email
    private String email;
    @NotNull @NotEmpty
    private String noHp;
    private String pemberiRekomendasi;
    private String namaPerekomendasi;
    private String negara;
    private String idKabupatenKota;
    private String namaAsalSekolah;
    private String programStudiPilihan;
    private String konsentrasi;


}
