package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendidikan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PendidikanDao extends PagingAndSortingRepository<Pendidikan, String> {

}
