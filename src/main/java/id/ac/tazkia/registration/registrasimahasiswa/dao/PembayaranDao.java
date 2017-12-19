package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.Pembayaran;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PembayaranDao extends PagingAndSortingRepository<Pembayaran, String>{
    List<Pembayaran> findByIdContainingIgnoreCaseOrderByTagihan(String tagihan);
}
