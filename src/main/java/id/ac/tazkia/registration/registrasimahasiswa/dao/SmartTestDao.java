package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.SmartTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SmartTestDao extends PagingAndSortingRepository<SmartTest, String> {
    Page<SmartTest> findByNamaContainingOrSekolahContainingIgnoreCaseOrderByNama(String nama, String sekolah, Pageable page);
}
