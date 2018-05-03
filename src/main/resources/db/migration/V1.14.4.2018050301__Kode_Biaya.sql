alter table program_studi
  add column kode_biaya VARCHAR(36);

update program_studi set kode_biaya = 'PSES-001' where id = '001';
update program_studi set kode_biaya = 'PSAS-001' where id = '002';
update program_studi set kode_biaya = 'PSBM-001' where id = '003';
update program_studi set kode_biaya = 'PSHES-001' where id = '004';
update program_studi set kode_biaya = 'PSES-001' where id = '005';
update program_studi set kode_biaya = 'PSTI-001' where id = '006';
update program_studi set kode_biaya = 'PSMES-001' where id = '007';
update program_studi set kode_biaya = 'PSMES-001' where id = '008';
update program_studi set kode_biaya = 'PSMES-001' where id = '009';

alter table program_studi
   ALTER COLUMN kode_biaya
   SET NOT NULL;