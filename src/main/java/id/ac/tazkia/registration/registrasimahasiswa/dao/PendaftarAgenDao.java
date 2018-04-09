package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.dto.RekapTagihanAgen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.PendaftarAgen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PendaftarAgenDao extends PagingAndSortingRepository<PendaftarAgen, String> {


    @Query("select new id.ac.tazkia.registration.registrasimahasiswa.dto.RekapTagihanAgen(cast(pa.tanggal as date), count(pa)) from PendaftarAgen pa " +
            "where pa.agen = :agen and pa.tagihan = false "+
            "and pa.tanggal >= :mulai and pa.tanggal <= :sampai "+
            "group by cast(pa.tanggal as date)")
    List<RekapTagihanAgen> rekapTagihan(@Param ("agen") Agen agen ,
                                        @Param("mulai") LocalDateTime mulai,
                                        @Param("sampai") LocalDateTime sampai);
}

