package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.HasilTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HasilTestDao extends PagingAndSortingRepository<HasilTest, String > {
    HasilTest findByPendaftar(Pendaftar p);
}
