ALTER TABLE hasil_test ALTER COLUMN jenis_test TYPE VARCHAR(36);
ALTER TABLE hasil_test ALTER COLUMN jenis_test DROP NOT NULL;

update hasil_test set jenis_test = 'TPA';

alter table hasil_test
  alter COLUMN jenis_test set NOT NULL;

