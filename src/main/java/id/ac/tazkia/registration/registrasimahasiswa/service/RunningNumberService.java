package id.ac.tazkia.registration.registrasimahasiswa.service;

import id.ac.tazkia.registration.registrasimahasiswa.dao.RunningNumberDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.RunningNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RunningNumberService {

    @Autowired
    private RunningNumberDao dao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RunningNumber generate(String nama){
        RunningNumber rn = dao.findByNama(nama);
        if(rn == null){
            rn = new RunningNumber();
            rn.setNama(nama);
            rn.setNomerTerakhir(0L);
        }
        logger.info("Angka lama : {}", rn.getNomerTerakhir());
        rn.setNomerTerakhir(rn.getNomerTerakhir() + 1);
        dao.save(rn);
        logger.info("Angka baru : {}",rn.getNomerTerakhir());

        return rn;
    }
}