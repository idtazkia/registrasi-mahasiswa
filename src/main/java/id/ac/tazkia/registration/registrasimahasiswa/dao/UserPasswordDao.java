package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import id.ac.tazkia.registration.registrasimahasiswa.entity.UserPassword;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPasswordDao extends PagingAndSortingRepository<UserPassword, String> {
    UserPassword findByUser(User u);
}
