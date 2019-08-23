package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.dto.NimDto;
import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.StatusTagihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailPendaftarDao extends PagingAndSortingRepository<DetailPendaftar, String> {

    DetailPendaftar findByPendaftar(Pendaftar p);

    Page<DetailPendaftar> findByPendaftarNomorRegistrasiContainingOrNimContainingOrPendaftarNamaContainingIgnoreCaseAndNimNotNullOrderByPendaftarNomorRegistrasi(String nomor, String nim, String nama, Pageable page);


    Page<DetailPendaftar> findByNimNotNull(Pageable page);

    Long countDetailPendaftarByPendaftarNotNull();

    Iterable<DetailPendaftar> findByNimNotNullAndStatus(StatusTagihan status);
    List<DetailPendaftar> findByNimAndStatus(String nim, StatusTagihan status);
}
