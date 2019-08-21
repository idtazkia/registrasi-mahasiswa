package id.ac.tazkia.registration.registrasimahasiswa.entity;

import id.ac.tazkia.registration.registrasimahasiswa.constants.AppConstants;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Entity @Data
public class DetailPendaftar {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @ManyToOne @JoinColumn(name = "id_pendaftar")
    private Pendaftar pendaftar;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String tempatLahir;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(columnDefinition = "DATE")
    private LocalDate tanggalLahir;

    @NotNull
    @NotEmpty
    private String jenisKelamin;

    @NotNull
    @NotEmpty
    @Size(max = 2)
    private String golonganDarah;

    @NotNull
    @NotEmpty
    private String noKtp;

    @NotNull
    @NotEmpty
    private String alamatRumah;

    @NotNull
    @NotEmpty
    private String provinsi;

    @NotNull
    @NotEmpty
    private String kokab;

    @NotNull
    @NotEmpty
    private String kodePos;

    @NotNull
    @NotEmpty
    private String noHp;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String asalSekolah;

    @NotNull
    @NotEmpty
    private String jurusanSekolah;

    private String nisn;

    @NotNull
    @NotEmpty
    private String tahunLulusSekolah;

    private String pekerjaanPribadi;

    private String penghasilanPribadi;

    @NotNull
    @NotEmpty
    private String statusSipil;

    @NotNull
    @NotEmpty
    private String namaAyah;

    @NotNull
    @NotEmpty
    private String agamaAyah;

    @NotNull
    @ManyToOne @JoinColumn(name = "pendidikan_ayah")
    private Pendidikan pendidikanAyah;

    @NotNull
    @ManyToOne @JoinColumn(name = "pekerjaan_ayah")
    private Pekerjaan pekerjaanAyah;

    @NotNull
    @NotEmpty
    private String namaIbu;

    @NotNull
    @NotEmpty
    private String agamaIbu;

    @NotNull
    @ManyToOne @JoinColumn(name = "pendidikan_ibu")
    private Pendidikan pendidikanIbu;

    @NotNull
    @ManyToOne @JoinColumn(name = "pekerjaan_ibu")
    private Pekerjaan pekerjaanIbu;

    @NotNull
    @NotEmpty
    private String alamatOrangtua;

    @NotNull
    @NotEmpty
    private String kokabOrangtua;

    @NotNull
    @NotEmpty
    private String nohpOrangtua;

    private String emailOrangtua;

    @NotNull
    @ManyToOne @JoinColumn(name = "penghasilan_orangtua")
    private Penghasilan penghasilanOrangtua;

    @NotNull
    @NotEmpty
    private String jumlahTanggungan;

    @NotNull
    @NotEmpty
    private String rencanaBiaya;

    @Enumerated(EnumType.STRING)
    private JenisTest jenisTest;

    private String nim;

    private String status;
}