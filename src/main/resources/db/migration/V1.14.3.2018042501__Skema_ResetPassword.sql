CREATE TABLE reset (
  id VARCHAR(36) NOT NULL ,
  id_user VARCHAR(36) NOT NULL ,
  code VARCHAR(36) NOT NULL ,
  expired DATE NOT NULL ,
  PRIMARY KEY (id),
  FOREIGN KEY (id_user) REFERENCES s_user(id)
);