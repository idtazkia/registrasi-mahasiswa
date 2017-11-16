package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleDao extends PagingAndSortingRepository<Role, String> {
}
