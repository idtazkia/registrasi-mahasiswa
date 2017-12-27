package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PembayaranDao extends PagingAndSortingRepository<Pembayaran, String>{
    List<Pembayaran> findByTagihan(Tagihan tagihan);
    List<Pembayaran> findByIdOrderByTagihan(String id,Pageable page);
}
