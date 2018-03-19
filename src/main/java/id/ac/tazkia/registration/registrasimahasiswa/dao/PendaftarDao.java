package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface PendaftarDao extends PagingAndSortingRepository<Pendaftar, String> {
    Page<Pendaftar> findByNomorRegistrasiContainingOrNamaContainingIgnoreCaseAndProgramStudiNotNullOrderByNomorRegistrasi(String nomor, String nama, Pageable page);
    Pendaftar findByUser(User u);
    Pendaftar findByNomorRegistrasi(String nomor);

    Page<Pendaftar> findByNomorRegistrasiContainingIgnoreCaseOrderByNomorRegistrasi(String nomorRegistrasi, Pageable page);

    Page<Pendaftar> findByIdContainingIgnoreCaseOrderById(String idPendaftar, Pageable page);

    List<Pendaftar> findByProgramStudiNotNull();

    Page<Pendaftar> findByProgramStudiNotNull(Pageable page);

}
