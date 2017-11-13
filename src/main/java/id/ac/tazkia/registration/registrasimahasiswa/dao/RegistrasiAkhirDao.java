package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Detail_pendaftar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegistrasiAkhirDao extends PagingAndSortingRepository<Detail_pendaftar, String> {

}
