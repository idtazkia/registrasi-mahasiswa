-- Role Agen
INSERT INTO s_permission (id, permission_value, permission_label) VALUES
  ('editagen', 'EDIT_AGEN', 'Edit Agen'),
  ('viewagen', 'VIEW_AGEN', 'View Agen');

INSERT INTO s_role (id, description, name) VALUES
  ('agen','AGEN','Agen');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
  ('agen', 'viewagen'),
  ('agen', 'editagen');

-- ////

INSERT INTO jenis_biaya VALUES ('010','Agen Pendaftar');

--Tbl Agen

CREATE TABLE agen (
  id       VARCHAR (36),
  nama_cabang     VARCHAR(255) NOT NULL,
  alamat_cabang   VARCHAR(255) NOT NULL,
  no_hp     VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  penanggung_jawab    VARCHAR(255) NOT NULL,
  kode    VARCHAR(255) NOT NULL,
  status   BOOLEAN      NOT NULL,
  id_user VARCHAR (36) NOT NULL,
  PRIMARY KEY (id),
  foreign key (id_user) references s_user (id)
);
-- ///

-- Tbl Pendaftar_Agen
CREATE TABLE pendaftar_agen (
  id       VARCHAR (36),
  id_agen VARCHAR (36) NOT NULL,
  id_pendaftar VARCHAR (36) NOT NULL,
  tanggal TIMESTAMP NOT NULL,
  status_tagihan VARCHAR (36)  NOT NULL,
  PRIMARY KEY (id),
  foreign key (id_pendaftar) references pendaftar (id),
  foreign key (id_agen) references agen (id)
);
-- ///

-- Tagihan Agen

CREATE TABLE tagihan_agen (
  id       VARCHAR (36),
  id_agen VARCHAR (36) NOT NULL,
  id_jenisbiaya VARCHAR (36) NOT NULL,
  nomor_tagihan VARCHAR (255) NOT NULL,
  tanggal_tagihan DATE NOT NULL,
  keterangan VARCHAR (255) NOT NULL,
  nilai NUMERIC (19,2) NOT NULL,
  lunas BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (id),
  foreign key (id_agen) references agen (id),
  foreign key (id_jenisbiaya) references jenis_biaya (id)
);

--////

-- Pembayaran Agen

create table pembayaran_agen (
  id VARCHAR (36),
  id_tagihan VARCHAR(36) NOT NULL,
  id_bank VARCHAR (36) NOT NULL,
  waktu_pembayaran TIMESTAMP NOT NULL ,
  cara_pembayaran VARCHAR (255) NOT NULL ,
  bukti_pembayaran VARCHAR (255) NOT NULL ,
  nilai NUMERIC(19,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_tagihan) REFERENCES tagihan_agen(id),
  FOREIGN KEY (id_bank) REFERENCES bank(id)
);

-- ///