package id.ac.tazkia.registration.registrasimahasiswa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RekapTagihanAgen {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggal;
    private Long jumlah;
    public BigDecimal getNilai(){
        return new BigDecimal("200000.00").multiply(new BigDecimal(jumlah));
    }
}
