package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GradeDao extends PagingAndSortingRepository<Grade, String > {
    Page<Grade> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);

}
