package id.ac.tazkia.registration.registrasimahasiswa.batch;

import id.ac.tazkia.registration.registrasimahasiswa.dao.PendaftarDao;
import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Pendaftar;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import id.ac.tazkia.registration.registrasimahasiswa.service.TagihanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GeneratorTagihanMigration implements CommandLineRunner{
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorTagihanMigration.class);

    @Autowired private TagihanService tagihanService;
    @Autowired private PendaftarDao pendaftarDao;
    @Autowired private TagihanDao tagihanDao;

    @Override
    public void run(String... args) throws Exception {
        Iterable<Pendaftar> semua = pendaftarDao.findAll();
        semua.forEach(pendaftar -> {
            LOGGER.info("Memproses tagihan pendaftaran {}",pendaftar.getNomorRegistrasi());
            if(tagihanDao.countByPendaftar(pendaftar) > 0){
                LOGGER.info("Pendaftar {} sudah memiliki tagihan pendaftaran", pendaftar.getNomorRegistrasi());
                return;
            }
            Tagihan t = tagihanService.createTagihanPendaftaran(pendaftar);
            LOGGER.info("Tagihan dibuat dengan nomer {} dan nilai {}", t.getNomorTagihan(), t.getNilai());
        });
    }
}
