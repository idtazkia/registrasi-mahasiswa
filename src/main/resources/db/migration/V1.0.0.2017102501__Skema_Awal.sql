CREATE TABLE sekolah (
  id                VARCHAR (36),
  nama              VARCHAR(255) NOT NULL,
  alamat            VARCHAR(255) NOT NULL,
  kontak            VARCHAR(255),
  nspn              VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE registrasi_awal (
  id                      VARCHAR (36),
  nama                    VARCHAR (255) NOT NULL,
  no_hp                   VARCHAR (13)  NOT NULL,
  email                   VARCHAR (255) NOT NULL,
  negara                  VARCHAR (255) NOT NULL,
  kota_asal_sekolah       VARCHAR (255) NOT NULL,
  nama_asal_sekolah       VARCHAR (255) NOT NULL,
  pemberi_rekomendasi     VARCHAR (255) NOT NULL,
  nama_perekomendasi      VARCHAR (255),
  program_studi_pilihan   VARCHAR (255) NOT NULL,
  konsentrasi             VARCHAR (255),
  PRIMARY KEY (id)

);

CREATE TABLE provinsi(
  id  VARCHAR (36),
  nama VARCHAR (255),
  PRIMARY KEY (id)
);

CREATE TABLE kabupaten_kota(
  id  VARCHAR (36),
  nama VARCHAR (255),
  id_provinsi VARCHAR (36),
  PRIMARY KEY (id),
  FOREIGN KEY (id_provinsi) REFERENCES provinsi (id)
);


-- Table Baru
CREATE TABLE program_studi(
  id VARCHAR (36),
  nama VARCHAR (255),
  PRIMARY KEY (id)
);

CREATE TABLE periode(
  id VARCHAR (36),
  nama VARCHAR (255),
  tanggal_mulai VARCHAR (255),
  tanggal_selesai VARCHAR (255),
  PRIMARY KEY (id)
);

CREATE TABLE grade(
  id VARCHAR (36),
  nama VARCHAR (255),
  nilai_minimum VARCHAR (255),
  PRIMARY KEY (id)
);

CREATE TABLE jenis_biaya(
  id VARCHAR (36),
  nama VARCHAR (255),
  PRIMARY KEY (id)
);

CREATE TABLE nilai_biaya(
  id VARCHAR (36),
  id_jenisbiaya VARCHAR (255),
  id_grade VARCHAR (255),
  id_periode VARCHAR (255),
  id_programstudi VARCHAR (255),
  nilai VARCHAR (255),
  PRIMARY KEY (id)
);
-- zzzzzzzz


CREATE TABLE registrasi_akhir (
  id                    VARCHAR (36),
  nama                  VARCHAR (255) NOT NULL,
  ttl                   VARCHAR (255) NOT NULL,
  jenis_kelamin         VARCHAR (13) NOT NULL,
  golongan_darah        VARCHAR (36) NOT NULL,
  no_ktp                VARCHAR (36) NOT NULL,
  alamat_rumah          VARCHAR (255) NOT NULL,
  provinsi              VARCHAR (255) NOT NULL,
  kokab                 VARCHAR (255) NOT NULL,
  kode_pos              VARCHAR (255) NOT NULL,
  no_hp                 VARCHAR (36) NOT NULL,
  email                 VARCHAR (36) NOT NULL,
  asal_sekolah          VARCHAR (255) NOT NULL,
  jurusan_sekolah       VARCHAR (36) NOT NULL,
  nisn                  VARCHAR (255) ,
  tahun_lulus_sekolah   VARCHAR (255) NOT NULL,
  pekerjaan_pribadi     VARCHAR (255) ,
  penghasilan_pribadi   VARCHAR (255) ,
  status_sipil          VARCHAR (255) NOT NULL,
  nama_ayah             VARCHAR (255) NOT NULL,
  agama_ayah            VARCHAR (255) NOT NULL,
  pendidikan_ayah       VARCHAR (255) NOT NULL,
  pekerjaan_ayah        VARCHAR (255) NOT NULL,
  nama_ibu              VARCHAR (255) NOT NULL,
  agama_ibu             VARCHAR (255) NOT NULL,
  pendidikan_ibu        VARCHAR (255) NOT NULL,
  pekerjaan_ibu         VARCHAR (255) NOT NULL,
  alamat_orangtua       VARCHAR (255) NOT NULL,
  kokab_orangtua        VARCHAR (255) NOT NULL,
  nohp_orangtua         VARCHAR (36) NOT NULL,
  email_orangtua        VARCHAR (255),
  penghasilan_orangtua  VARCHAR (255) NOT NULL,
  jumlah_tanggungan     VARCHAR (36) NOT NULL,
  rencana_biaya         VARCHAR (36) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (no_ktp)

);