package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PembayaranDao extends PagingAndSortingRepository<Pembayaran, String>{
    List<Pembayaran> findByTagihan(Tagihan tagihan);
    List<Pembayaran> findByIdOrderByTagihan(String id,Pageable page);

//    List<Pembayaran> findByTagihanPendaftarNamaContainingIgnoreCaseOrderByTagihan(String pendaftar,Pageable page);

    @Query("select h from Pembayaran h where (lower(h.tagihan.pendaftar.nama) like :pendaftar  or lower(h.tagihan.jenisBiaya.nama) like:pendaftar) order by h.tagihan.pendaftar.nama")
    Page<Pembayaran> cariByPendaftar(@Param("pendaftar") String pendaftar, Pageable page);

    Iterable<Pembayaran> findByTagihanJenisBiayaAndWaktuPembayaranBetweenOrderByWaktuPembayaran(JenisBiaya jenis, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
