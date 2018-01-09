INSERT INTO grade (id, nama, nilai_minimum) VALUES ('003', 'Grade C', 0);

CREATE TABLE hasil_test(
  id  VARCHAR (36),
  id_pendaftar VARCHAR (36) NOT NULL,
  nilai NUMERIC(5,2) not NULL default 0,
  id_grade VARCHAR (36) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_pendaftar) REFERENCES pendaftar (id),
  FOREIGN KEY (id_grade) REFERENCES grade (id)
);