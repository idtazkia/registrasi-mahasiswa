package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.KabupatenKota;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Provinsi;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface KabupatenKotaDao extends PagingAndSortingRepository<KabupatenKota, String> {
    List<KabupatenKota> findByProvinsiAndNamaContainingIgnoreCaseOrderByNama(Provinsi p, String nama);
}
