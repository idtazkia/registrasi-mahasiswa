package id.ac.tazkia.registration.registrasimahasiswa.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Grade {
    @Id
    @GeneratedValue(generator = "uuid" )
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String nilai_minimum;

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

    public String getNilai_minimum() {
        return nilai_minimum;
    }

    public void setNilai_minimum(String nilai_minimum) {
        this.nilai_minimum = nilai_minimum;
    }
}
