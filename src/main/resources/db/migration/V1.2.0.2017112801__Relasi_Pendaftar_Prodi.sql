alter table pendaftar
  add COLUMN id_program_studi VARCHAR(36);
alter table pendaftar
  add CONSTRAINT fk_prodi_pendaftar FOREIGN KEY (id_program_studi) REFERENCES program_studi (id);

update pendaftar set id_program_studi = '001' where program_studi_pilihan = 'ekonomi';
update pendaftar set id_program_studi = '002' where program_studi_pilihan = 'akuntansi';
update pendaftar set id_program_studi = '003' where program_studi_pilihan = 'managemen';
update pendaftar set id_program_studi = '004' where program_studi_pilihan = 'muamalah';
update pendaftar set id_program_studi = '005' where program_studi_pilihan = 'akuntansi' and konsentrasi = 'program_kerjasama';
update pendaftar set id_program_studi = '006' where program_studi_pilihan = 'tadris';

alter table pendaftar
  alter COLUMN id_program_studi set NOT NULL;

alter table pendaftar
  drop column program_studi_pilihan;