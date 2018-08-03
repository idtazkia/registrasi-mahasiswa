package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Keluarga;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KeluargaDao extends PagingAndSortingRepository<Keluarga, String> {

    Keluarga findByPendaftar(Pendaftar pendaftar);

}
