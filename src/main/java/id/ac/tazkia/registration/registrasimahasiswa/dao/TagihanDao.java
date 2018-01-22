package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TagihanDao extends PagingAndSortingRepository<Tagihan, String> {
    Long countByPendaftar(Pendaftar pendaftar);

    List<Tagihan> findByPendaftarOrderByTanggalTagihan(Pendaftar one);

    List<Tagihan> findByPendaftarOrderByTanggalTagihan(Pendaftar pendaftar, Pageable page);

    Page<Tagihan> findById(String tagihan, Pageable page);

    Tagihan findByPendaftar(Pendaftar p);

    Tagihan findByNomorTagihan(String nomor);
}
