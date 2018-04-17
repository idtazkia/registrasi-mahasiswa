package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.PendaftarAgen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.StatusTagihan;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PendaftarAgenDao extends PagingAndSortingRepository<PendaftarAgen, String> {

    @Query("select count (pa) from PendaftarAgen pa " +
            "where pa.agen = :agen and pa.statusTagihan = :status "+
            "and pa.tanggal >= :mulai and pa.tanggal <= :sampai "+
            "group by cast(pa.agen.id as string)")
    Long jumlahTagihanAgen(@Param ("agen") Agen agen ,
                                @Param("mulai") LocalDateTime mulai,
                                @Param("sampai") LocalDateTime sampai,
                                @Param("status") StatusTagihan statusTagihan);

    @Modifying
    @Query("update PendaftarAgen pa set pa.statusTagihan = :status " +
            "where pa.agen = :agen " +
            "and pa.tanggal >= :mulai and pa.tanggal <= :sampai ")
    Integer updateStatusTagihan(@Param("agen") Agen agen ,
                             @Param("mulai") LocalDateTime mulai,
                             @Param("sampai") LocalDateTime sampai,
                             @Param("status") StatusTagihan statusTagihan);

}

