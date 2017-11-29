package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagihanDao extends PagingAndSortingRepository<Tagihan, String> {
    Long countByPendaftar(Pendaftar pendaftar);
}
