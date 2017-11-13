package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JenisBiayaDao extends PagingAndSortingRepository <JenisBiaya, String> {
    Page<JenisBiaya> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);

}
