package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PendaftarDao extends PagingAndSortingRepository<Pendaftar, String> {
    Page<Pendaftar> findByNomorRegistrasiContainingOrNamaContainingIgnoreCaseOrderByNomorRegistrasi(String nomor, String nama, Pageable page);
    Pendaftar findByUser(User u);

    Page<Pendaftar> findByNomorRegistrasiContainingIgnoreCaseOrderByNomorRegistrasi(String nomorRegistrasi, Pageable page);

    Page<Pendaftar> findByIdContainingIgnoreCaseOrderById(String idPendaftar, Pageable page);

}
