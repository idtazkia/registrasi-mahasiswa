-- Update Nama  Prodi
update program_studi set nama = 'Ekonomi Syariah (S1)' where id = '001';
update program_studi set nama = 'Akuntansi Syariah (S1)' where id = '002';
update program_studi set nama = 'Bisnis & Manajemen Syariah (S1)' where id = '003';
update program_studi set nama = 'Hukum Ekonomi Syariah (S1)' where id = '004';
update program_studi set nama = 'FTUI (S1)' where id = '005';
update program_studi set nama = 'Pendidikan Ekonomi Syariah (S1)' where id = '006';
update program_studi set nama = 'Bisnis & Manajemen Syariah (S2)' where id = '007';
update program_studi set nama = 'Manajemen Harta Syariah (S2)' where id = '008';
update program_studi set nama = 'Akuntansi Syariah (S2)' where id = '009';

-- Update Nilai Grade
update grade set nilai_minimum ='29.00' where id = '002';

-- Nama Bank
insert into bank VALUES ('bnisy001','009','BNI Syariah YTC','1100319991','Yayasan Tazkia Cendekia');
insert into bank VALUES ('bsm001','451','BSM STEI Tazkia','7031904018','STEI Tazkia');
insert into bank VALUES ('tunai','001','Keuangan STEI TAZKIA','tunai','Keuangan STEI TAZKIA');

-- Truncate nilai biaya

TRUNCATE table nilai_biaya;

-- islamic economic (EI)
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('001', '001', '001', '001', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('002', '002', '001', '001', '001', 11000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('003', '001', '001', '002', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('004', '002', '001', '002', '001', 11000000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('005', '001', '001', '003', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('006', '002', '001', '003', '001', 11000000.00);

--Grade B                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('007', '001', '002', '001', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('008', '002', '002', '001', '001', 12000000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('009', '001', '002', '002', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('010', '002', '002', '002', '001', 12000000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('011', '001', '002', '003', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('012', '002', '002', '003', '001', 12000000.00);

-- islamic accounting
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('013', '001', '001', '001', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('014', '002', '001', '001', '002', 12200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('015', '001', '001', '002', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('016', '002', '001', '002', '002', 12200000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('017', '001', '001', '003', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('018', '002', '001', '003', '002', 12200000.00);

--Grade B                                                                                                jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('019', '001', '002', '001', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('020', '002', '002', '001', '002', 13200000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('021', '001', '002', '002', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('022', '002', '002', '002', '002', 13200000.00);


INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('023', '001', '002', '003', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('024', '002', '002', '003', '002', 13200000.00);

-- Bisnis Manajemen
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('025', '001', '001', '001', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('026', '002', '001', '001', '003', 12200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('027', '001', '001', '002', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('028', '002', '001', '002', '003', 12200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('029', '001', '001', '003', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('030', '002', '001', '003', '003', 12200000.00);

--Grade B                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('031', '001', '002', '001', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('032', '002', '002', '001', '003', 13200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('033', '001', '002', '002', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('034', '002', '002', '002', '003', 13200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('035', '001', '002', '003', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('036', '002', '002', '003', '003', 13200000.00);


-- Muamalah / islamic law
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('037', '001', '001', '001', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('038', '002', '001', '001', '004', 10600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('039', '001', '001', '002', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('040', '002', '001', '002', '004', 10600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('041', '001', '001', '003', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('042', '002', '001', '003', '004', 10600000.00);

--Grade B                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('043', '001', '002', '001', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('044', '002', '002', '001', '004', 11600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('045', '001', '002', '002', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('046', '002', '002', '002', '004', 11600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('047', '001', '002', '003', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('048', '002', '002', '003', '004', 11600000.00);

-- FT UI
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('049', '001', '001', '001', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('050', '002', '001', '001', '005', 16000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('051', '001', '001', '002', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('052', '002', '001', '002', '005', 16000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('053', '001', '001', '003', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('054', '002', '001', '003', '005', 16000000.00);

--Grade B                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('055', '001', '002', '001', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('056', '002', '002', '001', '005', 16000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('057', '001', '002', '002', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('058', '002', '002', '002', '005', 16000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('059', '001', '002', '003', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('060', '002', '002', '003', '005', 16000000.00);


-- islamic E. Teaching
--Grade A                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('061', '001', '001', '001', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('062', '002', '001', '001', '006', 9800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('063', '001', '001', '002', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('064', '002', '001', '002', '006', 9800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('065', '001', '001', '003', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('066', '002', '001', '003', '006', 9800000.00);

--Grade B                                                                                                 jb     gr     pr     std
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('067', '001', '002', '001', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('068', '002', '002', '001', '006', 10800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('069', '001', '002', '002', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('070', '002', '002', '002', '006', 10800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('071', '001', '002', '003', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('072', '002', '002', '003', '006', 10800000.00);


-- Biaya Diskon Keluarga

-- Ekonomi Islam (grade A)

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('343', '011', '001', '001', '001', 5600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('344', '011', '001', '002', '001', 7800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('345', '011', '001', '003', '001', 8300000.00);

-- grade B

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('346', '011', '002', '001', '001', 5600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('347', '011', '002', '002', '001', 8300000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('348', '011', '002', '003', '001', 8800000.00);

-- Akuntansi Islam (grade A)

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('349', '011', '001', '001', '002', 6800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('350', '011', '001', '002', '002', 9000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('351', '011', '001', '003', '002', 9500000.00);

-- grade B

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('352', '011', '002', '001', '002', 6800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('353', '011', '002', '002', '002', 9500000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('354', '011', '002', '003', '002', 10000000.00);

-- Bisni Manajemen Islam (grade A)

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('355', '011', '001', '001', '003', 6800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('356', '011', '001', '002', '003', 9000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('357', '011', '001', '003', '003', 9500000.00);

-- grade B

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('358', '011', '002', '001', '003', 6800000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('359', '011', '002', '002', '003', 9500000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('360', '011', '002', '003', '003', 10000000.00);


-- Muamalah (grade A)

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('361', '011', '001', '001', '004', 5200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('362', '011', '001', '002', '004', 7400000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('363', '011', '001', '003', '004', 7900000.00);

-- grade B

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('364', '011', '002', '001', '004', 5200000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('365', '011', '002', '002', '004', 7900000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('366', '011', '002', '003', '004', 8400000.00);

--  Tadris PEI (grade A)

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('367', '011', '001', '001', '006', 4400000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('368', '011', '001', '002', '006', 6600000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('369', '011', '001', '003', '006', 7100000.00);

-- grade B

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('370', '011', '002', '001', '006', 4400000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai)
VALUES ('371', '011', '002', '002', '006', 7100000.00);
