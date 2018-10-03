ALTER TABLE detail_pendaftar RENAME COLUMN ttl TO tempat_lahir;
ALTER TABLE detail_pendaftar ADD COLUMN tanggal_lahir date;


ALTER TABLE pendaftar ADD COLUMN agama VARCHAR (35);

ALTER TABLE hasil_test ADD COLUMN keterangan VARCHAR (225);
