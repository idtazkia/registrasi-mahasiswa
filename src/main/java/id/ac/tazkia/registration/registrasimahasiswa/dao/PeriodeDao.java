package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Periode;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PeriodeDao extends PagingAndSortingRepository<Periode, String> {
}
