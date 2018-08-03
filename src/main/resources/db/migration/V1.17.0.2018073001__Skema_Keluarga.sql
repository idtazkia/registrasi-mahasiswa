CREATE TABLE hub_keluarga (
  id VARCHAR(36) NOT NULL ,
  id_pendaftar VARCHAR(36) NOT NULL ,
  nim VARCHAR(36) NOT NULL ,
  nama VARCHAR(36) NOT NULL ,
  hubungan VARCHAR(36) NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (id_pendaftar) REFERENCES pendaftar(id)
);