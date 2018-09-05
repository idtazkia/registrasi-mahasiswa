alter table program_studi
  add column kode_simak VARCHAR(36);

update program_studi set kode_simak = '03' where id = '001';
update program_studi set kode_simak = '02' where id = '002';
update program_studi set kode_simak = '01' where id = '003';
update program_studi set kode_simak = '04' where id = '004';
update program_studi set kode_simak = '02' where id = '005';
update program_studi set kode_simak = '08' where id = '006';
update program_studi set kode_simak = '06' where id = '007';
update program_studi set kode_simak = '06' where id = '008';
update program_studi set kode_simak = '06' where id = '009';

alter table program_studi
   ALTER COLUMN kode_simak
   SET NOT NULL;


alter table detail_pendaftar
  add column nim VARCHAR(36);


INSERT INTO s_permission (id, permission_value, permission_label) VALUES
  ('editakademik', 'EDIT_AKADEMIK', 'Edit Akademik'),
  ('viewakademik', 'VIEW_AKADEMIK', 'View Akademik');

INSERT INTO s_role (id, description, name) VALUES
  ('akademik','AKADEMIK','Akademik');

INSERT INTO s_role_permission (id_role, id_permission) VALUES
  ('akademik', 'viewakademik'),
  ('akademik', 'editakademik');