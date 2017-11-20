alter table nilai_biaya
  drop column nilai;
alter table nilai_biaya
  add column nilai numeric(19,2) NOT NULL DEFAULT 0;

alter table periode
  drop column tanggal_mulai;
alter table periode
  add column tanggal_mulai DATE;

alter table periode
  drop column tanggal_selesai;
alter table periode
  add column tanggal_selesai DATE;

update periode set tanggal_mulai = '2017-10-01';
update periode set tanggal_selesai = '2017-10-01';

alter table periode
  alter column tanggal_mulai set not null;
alter table periode
  alter column tanggal_selesai set not null;

alter table grade
    drop column nilai_minimum;

alter table grade
    add column nilai_minimum NUMERIC(5,2) not NULL default 0;