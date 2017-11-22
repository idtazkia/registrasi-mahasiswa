delete from program_studi;
delete from jenis_biaya;
delete from grade;
delete from periode;
delete from nilai_biaya;

INSERT INTO program_studi (id, nama) VALUES ('001', 'Islamic Economic');
INSERT INTO program_studi (id, nama) VALUES ('002', 'Islamic Accounting');
INSERT INTO program_studi (id, nama) VALUES ('003', 'Bisnis Manajemen');
INSERT INTO program_studi (id, nama) VALUES ('004', 'Muamalah');
INSERT INTO program_studi (id, nama) VALUES ('005', 'FTUI');
INSERT INTO program_studi (id, nama) VALUES ('006', 'Islamic E. Teaching');


INSERT INTO jenis_biaya (id, nama) VALUES ('001', 'Pendaftaran');
INSERT INTO jenis_biaya (id, nama) VALUES ('002', 'Daftar Ulang');
INSERT INTO jenis_biaya (id, nama) VALUES ('003', 'Asrama');
INSERT INTO jenis_biaya (id, nama) VALUES ('004', 'Uang Pangkal');
INSERT INTO jenis_biaya (id, nama) VALUES ('005', 'Cicilan 1 UKT 2 Tahun');
INSERT INTO jenis_biaya (id, nama) VALUES ('007', 'Cicilan 3 UKT 2 Tahun');
INSERT INTO jenis_biaya (id, nama) VALUES ('006', 'Cicilan 2 UKT 2 Tahun');
INSERT INTO jenis_biaya (id, nama) VALUES ('008', 'Cicilan 4 UKT 2 Tahun');
INSERT INTO jenis_biaya (id, nama) VALUES ('009', 'Biaya Penelitian');

INSERT INTO grade (id, nama, nilai_minimum) VALUES ('001', 'Grade A', 70.00);
INSERT INTO grade (id, nama, nilai_minimum) VALUES ('002', 'Grade B', 30.00);

INSERT INTO periode (id, nama, tanggal_mulai, tanggal_selesai) VALUES ('001', 'Batch 1 2017-2018', '2017-11-01', '2018-04-30');
INSERT INTO periode (id, nama, tanggal_mulai, tanggal_selesai) VALUES ('002', 'Batch 2 2017-2018', '2018-05-01', '2018-07-30');
INSERT INTO periode (id, nama, tanggal_mulai, tanggal_selesai) VALUES ('003', 'Batch 3 2017-2018', '2018-08-01', '2018-10-31');

-- islamic economic
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('001', '001', '001', '001', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('002', '002', '001', '001', '001', 5600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('003', '003', '001', '001', '001', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('004', '004', '001', '001', '001', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('006', '005', '001', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('007', '006', '001', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('008', '007', '001', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('005', '008', '001', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('009', '009', '001', '001', '001', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('010', '001', '001', '002', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('011', '002', '001', '002', '001', 10000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('012', '003', '001', '002', '001', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('013', '004', '001', '002', '001', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('014', '005', '001', '002', '001', 3750000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('015', '006', '001', '002', '001', 3750000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('016', '007', '001', '002', '001', 3750000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('017', '008', '001', '002', '001', 3750000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('018', '009', '001', '002', '001', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('019', '001', '001', '003', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('020', '002', '001', '003', '001', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('021', '003', '001', '003', '001', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('022', '004', '001', '003', '001', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('023', '005', '001', '003', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('024', '006', '001', '003', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('025', '007', '001', '003', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('026', '008', '001', '003', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('027', '009', '001', '003', '001', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('028', '001', '002', '001', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('029', '002', '002', '001', '001', 5600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('030', '003', '002', '001', '001', 150000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('031', '004', '002', '001', '001', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('032', '005', '002', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('033', '006', '002', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('034', '007', '002', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('035', '008', '002', '001', '001', 2100000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('036', '009', '002', '001', '001', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('037', '001', '002', '002', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('038', '002', '002', '002', '001', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('039', '003', '002', '002', '001', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('040', '004', '002', '002', '001', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('041', '005', '002', '002', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('042', '006', '002', '002', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('043', '007', '002', '002', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('044', '008', '002', '002', '001', 4125000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('045', '009', '002', '002', '001', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('046', '001', '002', '003', '001', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('047', '002', '002', '003', '001', 12000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('048', '003', '002', '003', '001', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('049', '004', '002', '003', '001', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('050', '005', '002', '003', '001', 4500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('051', '006', '002', '003', '001', 4500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('052', '007', '002', '003', '001', 4500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('053', '008', '002', '003', '001', 4500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('054', '009', '002', '003', '001', 2000000.00);

-- islamic accounting
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('055', '001', '001', '001', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('056', '002', '001', '001', '002', 6400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('057', '003', '001', '001', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('058', '004', '001', '001', '002', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('059', '005', '001', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('060', '006', '001', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('061', '007', '001', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('062', '008', '001', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('063', '009', '001', '001', '002', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('064', '001', '001', '002', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('065', '002', '001', '002', '002', 10800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('066', '003', '001', '002', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('067', '004', '001', '002', '002', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('068', '005', '001', '002', '002', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('069', '006', '001', '002', '002', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('070', '007', '001', '002', '002', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('071', '008', '001', '002', '002', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('072', '009', '001', '002', '002', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('073', '001', '001', '003', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('074', '002', '001', '003', '002', 11800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('075', '003', '001', '003', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('076', '004', '001', '003', '002', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('077', '005', '001', '003', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('078', '006', '001', '003', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('079', '007', '001', '003', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('080', '008', '001', '003', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('081', '009', '001', '003', '002', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('082', '001', '002', '001', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('083', '002', '002', '001', '002', 6400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('084', '003', '002', '001', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('085', '004', '002', '001', '002', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('086', '005', '002', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('087', '006', '002', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('088', '007', '002', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('089', '008', '002', '001', '002', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('090', '009', '002', '001', '002', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('091', '001', '002', '002', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('092', '002', '002', '002', '002', 11800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('093', '003', '002', '002', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('094', '004', '002', '002', '002', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('095', '005', '002', '002', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('096', '006', '002', '002', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('097', '007', '002', '002', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('098', '008', '002', '002', '002', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('099', '009', '002', '002', '002', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('100', '001', '002', '003', '002', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('101', '002', '002', '003', '002', 12800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('102', '003', '002', '003', '002', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('103', '004', '002', '003', '002', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('104', '005', '002', '003', '002', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('105', '006', '002', '003', '002', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('106', '007', '002', '003', '002', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('107', '008', '002', '003', '002', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('108', '009', '002', '003', '002', 2000000.00);

-- Bisnis Manajemen
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('109', '001', '001', '001', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('110', '002', '001', '001', '003', 6400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('111', '003', '001', '001', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('112', '004', '001', '001', '003', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('113', '005', '001', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('114', '006', '001', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('115', '007', '001', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('116', '008', '001', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('117', '009', '001', '001', '003', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('118', '001', '001', '002', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('119', '002', '001', '002', '003', 10800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('120', '003', '001', '002', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('121', '004', '001', '002', '003', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('122', '005', '001', '002', '003', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('123', '006', '001', '002', '003', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('124', '007', '001', '002', '003', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('125', '008', '001', '002', '003', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('126', '009', '001', '002', '003', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('127', '001', '001', '003', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('128', '002', '001', '003', '003', 11800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('129', '003', '001', '003', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('130', '004', '001', '003', '003', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('131', '005', '001', '003', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('132', '006', '001', '003', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('133', '007', '001', '003', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('134', '008', '001', '003', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('135', '009', '001', '003', '003', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('136', '001', '002', '001', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('137', '002', '002', '001', '003', 6400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('138', '003', '002', '001', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('139', '004', '002', '001', '003', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('140', '005', '002', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('141', '006', '002', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('142', '007', '002', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('143', '008', '002', '001', '003', 2400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('144', '009', '002', '001', '003', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('145', '001', '002', '002', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('146', '002', '002', '002', '003', 11800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('147', '003', '002', '002', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('148', '004', '002', '002', '003', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('149', '005', '002', '002', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('150', '006', '002', '002', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('151', '007', '002', '002', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('152', '008', '002', '002', '003', 4425000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('153', '009', '002', '002', '003', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('154', '001', '002', '003', '003', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('155', '002', '002', '003', '003', 12800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('156', '003', '002', '003', '003', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('157', '004', '002', '003', '003', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('158', '005', '002', '003', '003', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('159', '006', '002', '003', '003', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('160', '007', '002', '003', '003', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('161', '008', '002', '003', '003', 4800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('162', '009', '002', '003', '003', 2000000.00);

-- Muamalah / islamic law
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('163', '001', '001', '001', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('164', '002', '001', '001', '004', 5200000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('165', '003', '001', '001', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('166', '004', '001', '001', '004', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('167', '005', '001', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('168', '006', '001', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('169', '007', '001', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('170', '008', '001', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('171', '009', '001', '001', '004', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('172', '001', '001', '002', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('173', '002', '001', '002', '004', 9600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('174', '003', '001', '002', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('175', '004', '001', '002', '004', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('176', '005', '001', '002', '004', 3600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('177', '006', '001', '002', '004', 3600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('178', '007', '001', '002', '004', 3600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('179', '008', '001', '002', '004', 3600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('180', '009', '001', '002', '004', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('181', '001', '001', '003', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('182', '002', '001', '003', '004', 10600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('183', '003', '001', '003', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('184', '004', '001', '003', '004', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('185', '005', '001', '003', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('186', '006', '001', '003', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('187', '007', '001', '003', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('188', '008', '001', '003', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('189', '009', '001', '003', '004', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('190', '001', '002', '001', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('191', '002', '002', '001', '004', 5200000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('192', '003', '002', '001', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('193', '004', '002', '001', '004', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('194', '005', '002', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('195', '006', '002', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('196', '007', '002', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('197', '008', '002', '001', '004', 1950000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('198', '009', '002', '001', '004', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('199', '001', '002', '002', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('200', '002', '002', '002', '004', 10600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('201', '003', '002', '002', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('202', '004', '002', '002', '004', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('203', '005', '002', '002', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('204', '006', '002', '002', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('205', '007', '002', '002', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('206', '008', '002', '002', '004', 3975000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('207', '009', '002', '002', '004', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('208', '001', '002', '003', '004', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('209', '002', '002', '003', '004', 11600000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('210', '003', '002', '003', '004', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('211', '004', '002', '003', '004', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('212', '005', '002', '003', '004', 4350000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('213', '006', '002', '003', '004', 4350000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('214', '007', '002', '003', '004', 4350000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('215', '008', '002', '003', '004', 4350000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('216', '009', '002', '003', '004', 2000000.00);

-- FT UI
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('217', '001', '001', '001', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('218', '002', '001', '001', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('219', '003', '001', '001', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('220', '004', '001', '001', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('221', '005', '001', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('222', '006', '001', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('223', '007', '001', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('224', '008', '001', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('225', '009', '001', '001', '005', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('226', '001', '001', '002', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('227', '002', '001', '002', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('228', '003', '001', '002', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('229', '004', '001', '002', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('230', '005', '001', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('231', '006', '001', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('232', '007', '001', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('233', '008', '001', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('234', '009', '001', '002', '005', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('235', '001', '001', '003', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('236', '002', '001', '003', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('237', '003', '001', '003', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('238', '004', '001', '003', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('239', '005', '001', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('240', '006', '001', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('241', '007', '001', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('242', '008', '001', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('243', '009', '001', '003', '005', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('244', '001', '002', '001', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('245', '002', '002', '001', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('246', '003', '002', '001', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('247', '004', '002', '001', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('248', '005', '002', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('249', '006', '002', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('250', '007', '002', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('251', '008', '002', '001', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('252', '009', '002', '001', '005', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('253', '001', '002', '002', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('254', '002', '002', '002', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('255', '003', '002', '002', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('256', '004', '002', '002', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('257', '005', '002', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('258', '006', '002', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('259', '007', '002', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('260', '008', '002', '002', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('261', '009', '002', '002', '005', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('262', '001', '002', '003', '005', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('263', '002', '002', '003', '005', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('264', '003', '002', '003', '005', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('265', '004', '002', '003', '005', 20000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('266', '005', '002', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('267', '006', '002', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('268', '007', '002', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('269', '008', '002', '003', '005', 6000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('270', '009', '002', '003', '005', 2000000.00);

-- islamic E. Teaching
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('271', '001', '001', '001', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('272', '002', '001', '001', '006', 4400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('273', '003', '001', '001', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('274', '004', '001', '001', '006', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('275', '005', '001', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('276', '006', '001', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('277', '007', '001', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('278', '008', '001', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('279', '009', '001', '001', '006', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('280', '001', '001', '002', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('281', '002', '001', '002', '006', 8800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('282', '003', '001', '002', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('283', '004', '001', '002', '006', 11000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('284', '005', '001', '002', '006', 3300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('285', '006', '001', '002', '006', 3300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('286', '007', '001', '002', '006', 3300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('287', '008', '001', '002', '006', 3300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('288', '009', '001', '002', '006', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('289', '001', '001', '003', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('290', '002', '001', '003', '006', 9800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('291', '003', '001', '003', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('292', '004', '001', '003', '006', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('293', '005', '001', '003', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('294', '006', '001', '003', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('295', '007', '001', '003', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('296', '008', '001', '003', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('297', '009', '001', '003', '006', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('298', '001', '002', '001', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('299', '002', '002', '001', '006', 4400000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('300', '003', '002', '001', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('301', '004', '002', '001', '006', 0.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('302', '005', '002', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('303', '006', '002', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('304', '007', '002', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('305', '008', '002', '001', '006', 1650000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('306', '009', '002', '001', '006', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('307', '001', '002', '002', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('308', '002', '002', '002', '006', 9800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('309', '003', '002', '002', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('310', '004', '002', '002', '006', 13500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('311', '005', '002', '002', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('312', '006', '002', '002', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('313', '007', '002', '002', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('314', '008', '002', '002', '006', 3675000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('315', '009', '002', '002', '006', 2000000.00);

INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('316', '001', '002', '003', '006', 300000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('317', '002', '002', '003', '006', 10800000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('318', '003', '002', '003', '006', 1500000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('319', '004', '002', '003', '006', 16000000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('320', '005', '002', '003', '006', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('321', '006', '002', '003', '006', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('322', '007', '002', '003', '006', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('323', '008', '002', '003', '006', 4050000.00);
INSERT INTO nilai_biaya (id, id_jenisbiaya, id_grade, id_periode, id_programstudi, nilai) VALUES ('324', '009', '002', '003', '006', 2000000.00);
