package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.TagihanAgen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface TagihanAgenDao extends PagingAndSortingRepository<TagihanAgen, String> {

    Page<TagihanAgen> findByAgenOrderByTanggalTagihan(Agen agen, Pageable page);

    TagihanAgen findByNomorTagihan(String nomorTagihan);
}
