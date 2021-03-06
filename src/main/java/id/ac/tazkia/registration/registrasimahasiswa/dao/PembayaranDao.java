package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import sun.security.util.Pem;

import java.time.LocalDateTime;
import java.util.List;

public interface PembayaranDao extends PagingAndSortingRepository<Pembayaran, String>{
    List<Pembayaran> findByTagihan(Tagihan tagihan);
    List<Pembayaran> findByIdOrderByTagihan(String id,Pageable page);

//    List<Pembayaran> findByTagihanPendaftarNamaContainingIgnoreCaseOrderByTagihan(String pendaftar,Pageable page);

    @Query("select h from Pembayaran h where (lower(h.tagihan.pendaftar.nama) like :pendaftar  or lower(h.tagihan.jenisBiaya.nama) like:pendaftar) order by h.tagihan.pendaftar.nama")
    Page<Pembayaran> cariByPendaftar(@Param("pendaftar") String pendaftar, Pageable page);

    Iterable<Pembayaran> findByTagihanJenisBiayaAndWaktuPembayaranBetweenOrderByWaktuPembayaran(JenisBiaya jenis, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Page<Pembayaran> findByTagihanJenisBiayaAndTagihanPendaftarNamaContainingIgnoreCase( JenisBiaya jenisBiaya,String pendaftar, Pageable page);

    Page<Pembayaran> findByTagihanPendaftarNamaContainingIgnoreCase(String pendaftar, Pageable page);

    Page<Pembayaran> findByTagihanJenisBiaya(JenisBiaya jenisBiaya, Pageable page);

    Long countPembayaranByTagihanJenisBiayaId(String jb);

    Pembayaran findByTagihanPendaftarIdAndTagihanJenisBiayaId(String id, String s);
}

