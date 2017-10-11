package tv.duojiao.service.rec;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.core.AbstractService;
import tv.duojiao.dao.rec.PortraitMapper;
import tv.duojiao.model.rec.Portrait;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Service
@Transactional
public class PortraitServiceImpl extends AbstractService<Portrait> implements PortraitService {
    @Resource
    private PortraitMapper portraitMapper;

    @Override
    public List<String> selectAllKeywords(Integer uid) {
        return portraitMapper.selectAllKeywords(uid);
    }

    @Override
    public List<String> selectCoreKeywords(Integer uid) {
        return portraitMapper.selectCoreKeywords(uid);
    }

    @Override
    public List<Portrait> selectByKeyword(String keyword) {
        return portraitMapper.selectByKeyword(keyword);
    }

    @Override
    public void updateByKeyword(int userId, String keyword, double number) {
        portraitMapper.updateByKeyword(userId, keyword, number);
    }

    @Override
    public Date selectByUpdateDate(int userId, String keyword) {
        return portraitMapper.selectByUpdateDate(userId, keyword);
    }

}
