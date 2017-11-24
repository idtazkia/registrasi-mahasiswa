create table tagihan (
  id VARCHAR (36),
  id_pendaftar VARCHAR (36) NOT NULL,
  id_jenisbiaya VARCHAR (36) NOT NULL,
  nomor_tagihan VARCHAR (255) NOT NULL,
  tanggal_tagihan DATE NOT NULL,
  keterangan VARCHAR (255) NOT NULL,
  nilai NUMERIC (19,2) NOT NULL,
  lunas BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  UNIQUE (nomor_tagihan),
  FOREIGN KEY (id_pendaftar) REFERENCES pendaftar(id),
  FOREIGN KEY (id_jenisbiaya) REFERENCES jenis_biaya(id)
);

CREATE TABLE bank (
  id        VARCHAR(36),
  kode_bank VARCHAR(100) NOT NULL,
  nama_bank VARCHAR(255) NOT NULL,
  nomor_rekening VARCHAR(255) NOT NULL,
  nama_rekening VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

create table pembayaran (
  id VARCHAR (36),
  id_tagihan VARCHAR(36) NOT NULL,
  id_bank VARCHAR (36) NOT NULL,
  waktu_pembayaran TIMESTAMP NOT NULL ,
  cara_pembayaran VARCHAR (255) NOT NULL ,
  bukti_pembayaran VARCHAR (255) NOT NULL ,
  nilai NUMERIC(19,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_tagihan) REFERENCES tagihan(id),
  FOREIGN KEY (id_bank) REFERENCES bank(id)
);