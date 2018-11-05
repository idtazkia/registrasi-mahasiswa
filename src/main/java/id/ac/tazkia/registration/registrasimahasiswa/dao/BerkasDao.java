package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Berkas;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BerkasDao extends PagingAndSortingRepository<Berkas, String> {

    Page<Berkas> findByPendaftarOrderByJenisBerkas(Pendaftar p, Pageable page);
}
