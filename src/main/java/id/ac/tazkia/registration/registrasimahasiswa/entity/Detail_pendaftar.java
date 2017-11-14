package id.ac.tazkia.registration.registrasimahasiswa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity @Data
public class Detail_pendaftar {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String idPendaftar;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String ttl;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jenisKelamin;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(max = 2)
    private String golonganDarah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String noKtp;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String alamatRumah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String provinsi;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String kokab;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String kodePos;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String noHp;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String asalSekolah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jurusanSekolah;

    @Column(nullable = false)
    private String nisn;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String tahunLulusSekolah;

    @Column(nullable = false)
    private String pekerjaanPribadi;

    @Column(nullable = false)
    private String penghasilanPribadi;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String statusSipil;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String namaAyah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String agamaAyah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pendidikanAyah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pekerjaanAyah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String namaIbu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String agamaIbu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pendidikanIbu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pekerjaanIbu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String alamatOrangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String kokabOrangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String nohpOrangtua;

    @Column(nullable = false)
    private String emailOrangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String penghasilanOrangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jumlahTanggungan;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String rencanaBiaya;

}