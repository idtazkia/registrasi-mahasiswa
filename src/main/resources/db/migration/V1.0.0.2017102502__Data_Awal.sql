-- INSERT INTO sekolah VALUES
--         ('1','SC','5','50103364','SMA Gilimandala','Jln. Kutilang','kab.Padang','0251754335'
--         );

INSERT INTO registrasi_awal VALUES
        ('001','Raden Abraham','081584321805','radenabraham@gmail.com','Indonesia','Kota Bandung',
         'SMA Islam Terpadu','Staff','Arif Ginanjar','Ekonomi Syariah','Ekonomi Pembangunan'
        );

INSERT INTO registrasi_akhir VALUES
        ('001','Raden Abraham','Bandung,16-08-1999','Laki-laki','O','114665622246','Jln. Cicalincing 01/04',
         'Jawa Barat','Kota Bandung','15200','081584321805','radenabraham@gmail.com','Kota Bandung',
         'SMA Islam Terpadu','IPA','12455474218','2017','-','-','WNI','Ibnu Nurahman',
         'Islam','SMA/SMK','Wirausaha','Aeni Sumantri','Islam','SMA/SMK','IRT','Jln. Cicalincing 01/04','Kota Bandung',
         '08787554222','-','2.000.000','5'
        );


INSERT INTO program_studi VALUES ('001','Islamic Economic');
INSERT INTO program_studi VALUES ('002', 'Islamic Accounting');
INSERT INTO program_studi VALUES ('003', 'Bisnis Manajemen' );
INSERT INTO program_studi VALUES ('004', 'Muamalah' );
INSERT INTO program_studi VALUES ('005', 'FTUI' );


INSERT INTO periode VALUES ('001','Batch 1','1 November 2017','30 April 2018');
INSERT INTO periode VALUES ('002','Batch 2','1 Mei 2018','30 juli 2018');
INSERT INTO periode VALUES ('003','Batch 3','1 Agustus 2018','30 Oktober 2018');


INSERT INTO grade VALUES ('001','Grade A','80');
INSERT INTO grade VALUES ('002','Grade B','60');

INSERT INTO jenis_biaya VALUES ('001','Pendaftaran');
INSERT INTO jenis_biaya VALUES ('002','Daftar Ulang');
INSERT INTO jenis_biaya VALUES ('003','Asrama');
INSERT INTO jenis_biaya VALUES ('004','Uang Pangkal');
INSERT INTO jenis_biaya VALUES ('005','UKT 2 Tahun');
INSERT INTO jenis_biaya VALUES ('006','Biaya Penelitian');

INSERT INTO nilai_biaya VALUES ('001','Daftar Ulang','Grade A','1','Islamic Economic','5.600.000');
INSERT INTO nilai_biaya VALUES ('002','Daftar Ulang','Grade B','1','Islamic Economic','5.600.000');
INSERT INTO nilai_biaya VALUES ('003','Daftar Ulang','Grade A','2','Islamic Economic','11.00.000');
INSERT INTO nilai_biaya VALUES ('004','Daftar Ulang','Grade B','2','Islamic Economic','11.00.000');