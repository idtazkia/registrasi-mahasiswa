package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class AgenDto {

    private  String id;
    @NotNull @NotEmpty
    private String namaCabang;
    @NotNull @NotEmpty
    private String penanggungJawab;
    @Email
    private String email;
    private String noHp;
    private String alamatCabang;
    private String status;
    private String username;
    private String password;


}
