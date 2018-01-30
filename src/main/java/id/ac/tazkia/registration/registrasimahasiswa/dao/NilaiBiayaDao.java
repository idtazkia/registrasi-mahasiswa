package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.NilaiBiaya;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.ProgramStudi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NilaiBiayaDao extends PagingAndSortingRepository <NilaiBiaya, String> {
    Page<NilaiBiaya> findByJenisBiayaAndProgramStudi(JenisBiaya jd, ProgramStudi programStudi, Pageable page);

    Page<JenisBiaya> findByJenisBiayaContainingIgnoreCaseOrderByJenisBiaya(String nama, Pageable page);
}
