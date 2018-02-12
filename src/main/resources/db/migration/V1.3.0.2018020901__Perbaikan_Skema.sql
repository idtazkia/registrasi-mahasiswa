alter table hasil_test
    add column jenis_test VARCHAR(366) not NULL default 0;
alter table hasil_test
    add column tanggal_test DATE;

update hasil_test set tanggal_test = '2018-01-01';

alter table hasil_test
  alter COLUMN tanggal_test set NOT NULL;

ALTER TABLE pendaftar ALTER COLUMN no_hp DROP NOT NULL;
ALTER TABLE pendaftar ALTER COLUMN email DROP NOT NULL;
ALTER TABLE pendaftar ALTER COLUMN negara DROP NOT NULL;
ALTER TABLE pendaftar ALTER COLUMN pemberi_rekomendasi DROP NOT NULL;
ALTER TABLE pendaftar ALTER COLUMN id_user DROP NOT NULL;
ALTER TABLE pendaftar ALTER COLUMN id_program_studi DROP NOT NULL;

ALTER TABLE pendaftar ALTER COLUMN no_hp TYPE VARCHAR(255);
