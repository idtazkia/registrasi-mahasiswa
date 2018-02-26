package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NilaiBiayaDao extends PagingAndSortingRepository <NilaiBiaya, String> {
    Page<NilaiBiaya> findByJenisBiayaAndProgramStudi(JenisBiaya jd, ProgramStudi programStudi, Pageable page);

    Page<JenisBiaya> findByJenisBiayaContainingIgnoreCaseOrderByJenisBiaya(String nama, Pageable page);

    Page<NilaiBiaya> findByJenisBiayaAndProgramStudiAndGradeAndPeriode(JenisBiaya daftarUlang, ProgramStudi programStudi, Grade grade, List<Periode> daftarPeriode, Pageable page);
}
