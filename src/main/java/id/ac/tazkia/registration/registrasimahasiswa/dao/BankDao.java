package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Bank;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankDao extends PagingAndSortingRepository<Bank, String> {
}
