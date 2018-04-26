package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Reset;
import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResetDao extends PagingAndSortingRepository<Reset, String> {

    Reset findByUser(User u);

    Reset findByCode(String code);
}
