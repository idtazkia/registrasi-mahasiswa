package id.ac.tazkia.registration.registrasimahasiswa.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class RegistrasiAkhir {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 150)
    private String nama;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String ttl;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jenis_kelamin;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Size(max = 2)
    private String golongan_darah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String no_ktp;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String alamat_rumah;

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
    private String kode_pos;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String no_hp;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String asal_sekolah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jurusan_sekolah;

    @Column(nullable = false)
    private String nisn;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String tahun_lulus_sekolah;

    @Column(nullable = false)
    private String pekerjaan_pribadi;

    @Column(nullable = false)
    private String penghasilan_pribadi;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String status_sipil;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String nama_ayah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String agama_ayah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pendidikan_ayah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pekerjaan_ayah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String nama_ibu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String agama_ibu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pendidikan_ibu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pekerjaan_ibu;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String alamat_orangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String kokab_orangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String nohp_orangtua;

    @Column(nullable = false)
    private String email_orangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String penghasilan_orangtua;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String jumlah_tanggungan;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String rencana_biaya;

//getter and
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getGolongan_darah() {
        return golongan_darah;
    }

    public void setGolongan_darah(String golongan_darah) {
        this.golongan_darah = golongan_darah;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getAlamat_rumah() {
        return alamat_rumah;
    }

    public void setAlamat_rumah(String alamat_rumah) {
        this.alamat_rumah = alamat_rumah;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKokab() {
        return kokab;
    }

    public void setKokab(String kokab) {
        this.kokab = kokab;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAsal_sekolah() {
        return asal_sekolah;
    }

    public void setAsal_sekolah(String asal_sekolah) {
        this.asal_sekolah = asal_sekolah;
    }

    public String getJurusan_sekolah() {
        return jurusan_sekolah;
    }

    public void setJurusan_sekolah(String jurusan_sekolah) {
        this.jurusan_sekolah = jurusan_sekolah;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getTahun_lulus_sekolah() {
        return tahun_lulus_sekolah;
    }

    public void setTahun_lulus_sekolah(String tahun_lulus_sekolah) {
        this.tahun_lulus_sekolah = tahun_lulus_sekolah;
    }

    public String getPekerjaan_pribadi() {
        return pekerjaan_pribadi;
    }

    public void setPekerjaan_pribadi(String pekerjaan_pribadi) {
        this.pekerjaan_pribadi = pekerjaan_pribadi;
    }

    public String getPenghasilan_pribadi() {
        return penghasilan_pribadi;
    }

    public void setPenghasilan_pribadi(String penghasilan_pribadi) {
        this.penghasilan_pribadi = penghasilan_pribadi;
    }

    public String getStatus_sipil() {
        return status_sipil;
    }

    public void setStatus_sipil(String status_sipil) {
        this.status_sipil = status_sipil;
    }

    public String getNama_ayah() {
        return nama_ayah;
    }

    public void setNama_ayah(String nama_ayah) {
        this.nama_ayah = nama_ayah;
    }

    public String getAgama_ayah() {
        return agama_ayah;
    }

    public void setAgama_ayah(String agama_ayah) {
        this.agama_ayah = agama_ayah;
    }

    public String getPendidikan_ayah() {
        return pendidikan_ayah;
    }

    public void setPendidikan_ayah(String pendidikan_ayah) {
        this.pendidikan_ayah = pendidikan_ayah;
    }

    public String getPekerjaan_ayah() {
        return pekerjaan_ayah;
    }

    public void setPekerjaan_ayah(String pekerjaan_ayah) {
        this.pekerjaan_ayah = pekerjaan_ayah;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }

    public String getAgama_ibu() {
        return agama_ibu;
    }

    public void setAgama_ibu(String agama_ibu) {
        this.agama_ibu = agama_ibu;
    }

    public String getPendidikan_ibu() {
        return pendidikan_ibu;
    }

    public void setPendidikan_ibu(String pendidikan_ibu) {
        this.pendidikan_ibu = pendidikan_ibu;
    }

    public String getPekerjaan_ibu() {
        return pekerjaan_ibu;
    }

    public void setPekerjaan_ibu(String pekerjaan_ibu) {
        this.pekerjaan_ibu = pekerjaan_ibu;
    }

    public String getAlamat_orangtua() {
        return alamat_orangtua;
    }

    public void setAlamat_orangtua(String alamat_orangtua) {
        this.alamat_orangtua = alamat_orangtua;
    }

    public String getKokab_orangtua() {
        return kokab_orangtua;
    }

    public void setKokab_orangtua(String kokab_orangtua) {
        this.kokab_orangtua = kokab_orangtua;
    }

    public String getNohp_orangtua() {
        return nohp_orangtua;
    }

    public void setNohp_orangtua(String nohp_orangtua) {
        this.nohp_orangtua = nohp_orangtua;
    }

    public String getEmail_orangtua() {
        return email_orangtua;
    }

    public void setEmail_orangtua(String email_orangtua) {
        this.email_orangtua = email_orangtua;
    }

    public String getPenghasilan_orangtua() {
        return penghasilan_orangtua;
    }

    public void setPenghasilan_orangtua(String penghasilan_orangtua) {
        this.penghasilan_orangtua = penghasilan_orangtua;
    }

    public String getJumlah_tanggungan() {
        return jumlah_tanggungan;
    }

    public void setJumlah_tanggungan(String jumlah_tanggungan) {
        this.jumlah_tanggungan = jumlah_tanggungan;
    }

    public String getRencana_biaya() {
        return rencana_biaya;
    }

    public void setRencana_biaya(String rencana_biaya) {
        this.rencana_biaya = rencana_biaya;
    }
}