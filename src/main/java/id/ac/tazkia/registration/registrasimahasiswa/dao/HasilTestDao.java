package id.ac.tazkia.registration.registrasimahasiswa.dao;

import id.ac.tazkia.registration.registrasimahasiswa.entity.HasilTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.JenisTest;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HasilTestDao extends PagingAndSortingRepository<HasilTest, String > {
    HasilTest findByPendaftar(Pendaftar p);

    Page<HasilTest> findByPendaftarNomorRegistrasiContainingOrPendaftarNamaContainingIgnoreCaseOrGradeNamaContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(String nomor, String nama, String grade, Pageable page);

    @Query("select h from HasilTest h where h.jenisTest in :jenis and (h.pendaftar.nomorRegistrasi like :pendaftar or lower(h.pendaftar.nama) like :pendaftar) order by h.pendaftar.nomorRegistrasi")
    Page<HasilTest> cariByJenisTestTpaJPaDanPendaftar(@Param("jenis") List<JenisTest> jenis, @Param("pendaftar") String pendaftar, Pageable page);

    @Query("select h from HasilTest h where h.jenisTest in :jenis and (lower(h.pendaftar.nama) like :pendaftar or lower (h.pendaftar.namaAsalSekolah) like :pendaftar) order by h.pendaftar.nomorRegistrasi")
    Page<HasilTest> cariByJenisTestSmartTestDanPendaftar(@Param("jenis") List<JenisTest> jenis, @Param("pendaftar") String pendaftar, Pageable page);


//    Page<HasilTest>  findByJenisTestInOrPendaftarNamaContainingOrPendaftarNamaAsalSekolahContainingIgnoreCaseOrderByPendaftarNomorRegistrasi(List<JenisTest>daftarJenisTest, String nama, String sekolah, Pageable smartPage);

    Page<HasilTest> findByJenisTestIn(Pageable tpaPage, JenisTest... jenisTests);

    Long  countHasilTestByPendaftarNotNull();

}

