create table pekerjaan (
  id VARCHAR (36),
  nama VARCHAR(36) NOT NULL,
  PRIMARY KEY (id)
);

insert into pekerjaan VALUES ('01', 'Tidak Bekerja');
insert into pekerjaan VALUES ('02', 'Pensiun/Almarhum');
insert into pekerjaan VALUES ('03', 'PNS');
insert into pekerjaan VALUES ('04', 'TNI/POLISI');
insert into pekerjaan VALUES ('05', 'Guru/Dosen');
insert into pekerjaan VALUES ('06', 'Pegawai Swasta');
insert into pekerjaan VALUES ('07', 'Pengusaha/Wiraswasta');
insert into pekerjaan VALUES ('08', 'Pengacara/Hakim/Jaksa/Notaris');
insert into pekerjaan VALUES ('09', 'Seniman/Pelukis/Artis/Sejenis');
insert into pekerjaan VALUES ('10', 'Dokter/Bidan/Perawat');
insert into pekerjaan VALUES ('11', 'Pilot/Pramugari');
insert into pekerjaan VALUES ('12', 'Pedagang');
insert into pekerjaan VALUES ('13', 'Petani/Peternak');
insert into pekerjaan VALUES ('14', 'Nelayan');
insert into pekerjaan VALUES ('15', 'Buruh (Tani/Pabrik/Bangunan)');
insert into pekerjaan VALUES ('16', 'Sopir/Masinis/Kondektur');
insert into pekerjaan VALUES ('17', 'Politikus');
insert into pekerjaan VALUES ('18', 'Lainnya');


create table penghasilan (
  id VARCHAR (36),
  nama VARCHAR(36) NOT NULL,
  PRIMARY KEY (id)
);

insert into penghasilan values ('1','Dibawah Rp.1.000.000');
insert into penghasilan values ('2','Rp.1.000.000 - Rp.2.000.000');
insert into penghasilan values ('3','Rp.2.000.001 - Rp.4.000.000');
insert into penghasilan values ('4','Rp.4.000.001 - Rp.6.000.000');
insert into penghasilan values ('5','Diatas Rp.6.000.000');


create table pendidikan (
  id VARCHAR (36),
  nama VARCHAR(36) NOT NULL,
  PRIMARY KEY (id)
);

insert into pendidikan values ('0','Tidak Berpendidikan Formal');
insert into pendidikan values ('1','<= SLTA');
insert into pendidikan values ('2','Diploma');
insert into pendidikan values ('3','S1');
insert into pendidikan values ('4','S2');
insert into pendidikan values ('5','S3');