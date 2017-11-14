package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Sekolah;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SekolahDao extends PagingAndSortingRepository<Sekolah, String> {
    Page<Sekolah> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);
}
