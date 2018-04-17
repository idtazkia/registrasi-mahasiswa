package id.ac.tazkia.registration.registrasimahasiswa.dto;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TagihanAgenDto {

    private Agen agen;

    private String nomorTagihan;

    private LocalDate tanggalTagihan;

    private String keterangan;

    private List<BigDecimal> nilai = new ArrayList<>();

    private Boolean lunas = Boolean.FALSE;

    private List<LocalDate> tanggal = new ArrayList<>();
}
