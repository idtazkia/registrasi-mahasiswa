package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pekerjaan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PekerjaanDao extends PagingAndSortingRepository<Pekerjaan, String> {

}
