package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.RunningNumber;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.LockModeType;

public interface RunningNumberDao extends PagingAndSortingRepository<RunningNumber, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public RunningNumber findByNama(String nama);
}
