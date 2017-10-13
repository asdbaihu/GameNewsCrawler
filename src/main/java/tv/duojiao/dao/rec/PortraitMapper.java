package tv.duojiao.dao.rec;

import org.springframework.stereotype.Component;
import tv.duojiao.core.SubMapper.InsertPidMapper;
import tv.duojiao.model.rec.Portrait;
import tv.duojiao.core.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface PortraitMapper extends Mapper<Portrait> {
    List<String> selectAllKeywords(int uid);

    List<String> selectCoreKeywords(int uid);

    List<Portrait> selectByKeyword(String keyword);

    void updateByKeyword(Map data);

    Date selectByUpdateDate(Map data);

    Integer insertAutoKey(Portrait portrait);
}