package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PendaftarDao extends PagingAndSortingRepository<Pendaftar, String> {
    Page<Pendaftar> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);

}
