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

--Tbl Agen

CREATE TABLE agen (
  id       VARCHAR (36),
  nama_cabang     VARCHAR(255) NOT NULL,
  alamat_cabang   VARCHAR(255) NOT NULL,
  no_hp     VARCHAR(255) NOT NULL,
  email    VARCHAR(255) NOT NULL,
  penanggung_jawab    VARCHAR(255) NOT NULL,
  status   BOOLEAN      NOT NULL,
  id_user VARCHAR (36) NOT NULL,
  PRIMARY KEY (id),
  foreign key (id_user) references s_user (id)
);

-- ///
