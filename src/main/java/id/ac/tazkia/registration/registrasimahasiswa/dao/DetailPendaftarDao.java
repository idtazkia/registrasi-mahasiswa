package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.DetailPendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DetailPendaftarDao extends PagingAndSortingRepository<DetailPendaftar, String> {

    DetailPendaftar findByPendaftar(Pendaftar p);
}
