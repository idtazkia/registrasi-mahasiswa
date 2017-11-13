package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProgramStudiDao extends PagingAndSortingRepository<ProgramStudi, String> {
    Page<ProgramStudi> findByNamaContainingIgnoreCaseOrderByNama(String nama, Pageable page);

}
