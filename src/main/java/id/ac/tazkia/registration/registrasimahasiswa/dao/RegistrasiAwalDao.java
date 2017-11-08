package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.RegistrasiAwal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegistrasiAwalDao  extends PagingAndSortingRepository<RegistrasiAwal, String> {
}
