package id.ac.tazkia.registration.registrasimahasiswa.dto;

import id.ac.tazkia.registration.registrasimahasiswa.entity.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
public class RegistrasiDetail {
    private String nama;
    private String asalSekolah;
    private String kabupatenKota;
    private String pendaftar;
    private String email;
    private String tempatLahir;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    private String golonganDarah;
    private String noKtp;
    private String alamatRumah;
    private String provinsi;
    private String kokab;
    private String kodePos;
    private String noHp;
    private String jurusanSekolah;
    private String nisn;
    private String tahunLulusSekolah;
    private String pekerjaanPribadi;
    private String penghasilanPribadi;
    private String statusSipil;
    private String namaAyah;
    private String agamaAyah;
    private Pendidikan pendidikanAyah;
    private Pekerjaan pekerjaanAyah;
    private String namaIbu;
    private String agamaIbu;
    private Pendidikan pendidikanIbu;
    private Pekerjaan pekerjaanIbu;
    private String alamatOrangtua;
    private String kokabOrangtua;
    private String nohpOrangtua;
    private String emailOrangtua;
    private Penghasilan penghasilanOrangtua;
    private String jumlahTanggungan;
    private String rencanaBiaya;
    private JenisTest jenisTest;
    private String nim;
    private DetailPendaftar detailPendaftar;
    private ProgramStudi programStudi;
    private String nomorRegistrasi;
    private String negara;
    private String namaAsalSekolah;
    private ProgramStudi prodiLama;
    private ProgramStudi status;


}
