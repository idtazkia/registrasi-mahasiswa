package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.HasilTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HasilTestDao extends PagingAndSortingRepository<HasilTest, String > {
    HasilTest findByPendaftar(Pendaftar p);

    Page<HasilTest> findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(String nomor, String nama, Pageable page);

    Page<HasilTest> findByPendaftarNomorRegistrasiContainingOrPendaftarNamaOrGradeNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(String nomor, String nama, String grade, Pageable page);
}
