package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.User;
import id.ac.tazkia.registration.registrasimahasiswa.entity.UserPassword;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserPasswordDao extends PagingAndSortingRepository<UserPassword, String> {
    UserPassword findByUser(User u);
    Optional<UserPassword> findById(String id);

}
