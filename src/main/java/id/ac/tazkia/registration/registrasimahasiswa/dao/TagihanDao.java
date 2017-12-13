package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagihanDao extends PagingAndSortingRepository<Tagihan, String> {
    Page<Tagihan> findById(String tagihan, Pageable page);
}
