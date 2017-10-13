package tv.duojiao.service.rec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.core.AbstractService;
import tv.duojiao.dao.rec.PortraitMapper;
import tv.duojiao.model.rec.Portrait;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Service
@Transactional
public class PortraitServiceImpl extends AbstractService<Portrait> implements PortraitService {
    Logger LOG = LogManager.getLogger(PortraitServiceImpl.class);
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
    public void updateByKeyword(int userId, String keyword, double score) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", userId);
        map.put("keyword", keyword);
        map.put("score", score);
        if (selectByKeyword(keyword).size() >= 1) {
            portraitMapper.updateByKeyword(map);
        } else {
            LOG.error("{}: 此关键词未录入", keyword);
            Portrait portrait = new Portrait(0, userId, 1, keyword, 0.00001, new Date());
            portraitMapper.insertAutoKey(portrait);
        }
    }

    @Override
    public Date selectByUpdateDate(int userId, String keyword) {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("keyword", keyword);
        return portraitMapper.selectByUpdateDate(data);
    }
}
