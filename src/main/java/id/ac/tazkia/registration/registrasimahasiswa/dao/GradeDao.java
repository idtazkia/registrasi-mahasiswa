package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Grade;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GradeDao extends PagingAndSortingRepository<Grade,String  > {
}
