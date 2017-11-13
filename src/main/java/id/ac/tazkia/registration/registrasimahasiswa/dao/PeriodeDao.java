package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PeriodeDao extends PagingAndSortingRepository<Periode, String> {
    Page<Periode> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);

}
