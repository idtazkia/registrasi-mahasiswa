create table berkas (
  id VARCHAR (36),
  id_pendaftar VARCHAR(36) NOT NULL,
  jenis_berkas VARCHAR (36) NOT NULL,
  file_berkas VARCHAR (255) NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (id_pendaftar) REFERENCES pendaftar(id)
);