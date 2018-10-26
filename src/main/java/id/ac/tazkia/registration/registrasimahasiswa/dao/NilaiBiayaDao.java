package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NilaiBiayaDao extends PagingAndSortingRepository <NilaiBiaya, String> {
    Page<NilaiBiaya> findByJenisBiayaAndProgramStudi(JenisBiaya jd, ProgramStudi programStudi, Pageable page);

    Page<NilaiBiaya> findByJenisBiayaNamaContainingIgnoreCaseOrderByJenisBiayaNama(String nama, Pageable page);

    Page<NilaiBiaya> findByJenisBiayaAndProgramStudiAndGradeAndPeriode(JenisBiaya daftarUlang, ProgramStudi programStudi, Grade grade, List<Periode> daftarPeriode, Pageable page);

    Page<NilaiBiaya> findByProgramStudiAndJenisBiayaAndPeriode(ProgramStudi prodi, JenisBiaya jenisBiaya, Periode periode, Pageable page);

    Page<NilaiBiaya> findByJenisBiayaAndPeriode(JenisBiaya jenisBiaya, Periode periode, Pageable page);

    Page<NilaiBiaya> findByPeriodeAndProgramStudi(Periode periode, ProgramStudi prodi, Pageable page);

    Page<NilaiBiaya> findByProgramStudi(ProgramStudi prodi, Pageable page);

    Page<NilaiBiaya> findByPeriode(Periode periode, Pageable page);

    Page<NilaiBiaya> findByJenisBiaya(JenisBiaya jenisBiaya, Pageable page);

    List<NilaiBiaya> findByProgramStudiAndJenisBiayaAndPeriodeAndGrade(ProgramStudi programStudi, JenisBiaya jenisBiaya, Periode pr, Grade gr);
}
