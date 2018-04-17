package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgenDao extends PagingAndSortingRepository<Agen, String> {
    Page<Agen> findByNamaCabangContainingIgnoreCaseOrderByNamaCabang(String nama, Pageable page);
    Agen findByUser(User u);

    Agen findByKode(String kode);
}

