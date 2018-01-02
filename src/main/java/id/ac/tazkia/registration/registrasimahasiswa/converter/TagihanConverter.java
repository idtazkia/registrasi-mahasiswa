package id.ac.tazkia.registration.registrasimahasiswa.converter;

import id.ac.tazkia.registration.registrasimahasiswa.dao.TagihanDao;
import id.ac.tazkia.registration.registrasimahasiswa.entity.Tagihan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TagihanConverter implements Converter<String, Tagihan> {

    @Autowired private TagihanDao tagihanDao;

    @Override
    public Tagihan convert(String idTagihan) {
        return tagihanDao.findOne(idTagihan);
    }
}
