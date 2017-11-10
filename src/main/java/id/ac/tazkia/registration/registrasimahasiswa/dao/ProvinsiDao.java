package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Provinsi;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProvinsiDao extends PagingAndSortingRepository<Provinsi, String> {
    List<Provinsi> findByNamaContainingIgnoreCaseOrderByNama(String nama);
}
