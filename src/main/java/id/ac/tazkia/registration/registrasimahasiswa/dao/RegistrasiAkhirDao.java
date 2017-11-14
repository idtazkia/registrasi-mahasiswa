package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegistrasiAkhirDao extends PagingAndSortingRepository<DetailPendaftar, String> {

}
