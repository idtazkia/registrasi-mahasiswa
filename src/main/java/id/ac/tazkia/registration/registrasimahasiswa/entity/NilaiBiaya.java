package id.ac.tazkia.registration.registrasimahasiswa.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class NilaiBiaya {

    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String id_jenisbiaya;

    @Column(nullable = false)
    private String id_grade;


    @Column(nullable = false)
    private String id_periode;

    @Column(nullable = false)
    private String id_programstudi;

    @Column(nullable = false)
    private String nilai;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_jenisbiaya() {
        return id_jenisbiaya;
    }

    public void setId_jenisbiaya(String id_jenisbiaya) {
        this.id_jenisbiaya = id_jenisbiaya;
    }

    public String getId_grade() {
        return id_grade;
    }

    public void setId_grade(String id_grade) {
        this.id_grade = id_grade;
    }

    public String getId_periode() {
        return id_periode;
    }

    public void setId_periode(String id_periode) {
        this.id_periode = id_periode;
    }

    public String getId_programstudi() {
        return id_programstudi;
    }

    public void setId_programstudi(String id_programstudi) {
        this.id_programstudi = id_programstudi;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
}
