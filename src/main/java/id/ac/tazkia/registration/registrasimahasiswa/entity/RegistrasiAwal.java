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
public class RegistrasiAwal {

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
    private String no_hp;

    @Column(nullable = false, unique = true)
    @Email
    @NotEmpty
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String negara;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String kota_asal_sekolah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String nama_asal_sekolah;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String pemberi_rekomendasi;

    @Column(nullable = false)
    private String nama_perekomendasi;

    @Column(nullable = false)
    private String program_studi_pilihan;

    @Column(nullable = false)
    private String konsentrasi;


//getter setter
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

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getKota_asal_sekolah() {
        return kota_asal_sekolah;
    }

    public void setKota_asal_sekolah(String kota_asal_sekolah) {
        this.kota_asal_sekolah = kota_asal_sekolah;
    }

    public String getNama_asal_sekolah() {
        return nama_asal_sekolah;
    }

    public void setNama_asal_sekolah(String nama_asal_sekolah) {
        this.nama_asal_sekolah = nama_asal_sekolah;
    }

    public String getPemberi_rekomendasi() {
        return pemberi_rekomendasi;
    }

    public void setPemberi_rekomendasi(String pemberi_rekomendasi) {
        this.pemberi_rekomendasi = pemberi_rekomendasi;
    }

    public String getNama_perekomendasi() {
        return nama_perekomendasi;
    }

    public void setNama_perekomendasi(String nama_perekomendasi) {
        this.nama_perekomendasi = nama_perekomendasi;
    }

    public String getProgram_studi_pilihan() {
        return program_studi_pilihan;
    }

    public void setProgram_studi_pilihan(String program_studi_pilihan) {
        this.program_studi_pilihan = program_studi_pilihan;
    }

    public String getKonsentrasi() {
        return konsentrasi;
    }

    public void setKonsentrasi(String konsentrasi) {
        this.konsentrasi = konsentrasi;
    }



}
