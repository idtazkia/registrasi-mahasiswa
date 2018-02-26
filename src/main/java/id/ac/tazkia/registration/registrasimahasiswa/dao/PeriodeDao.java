package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PeriodeDao extends PagingAndSortingRepository<Periode, String> {
    Page<Periode> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);
    @Query("select p from Periode p where :tanggal between p.tanggalMulai and p.tanggalSelesai")
    List<Periode> cariPeriodeUntukTanggal(@Param("tanggal") LocalDate tanggal);

}
