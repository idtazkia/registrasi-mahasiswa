package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Agen;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgenDao extends PagingAndSortingRepository<Agen, String> {
}

