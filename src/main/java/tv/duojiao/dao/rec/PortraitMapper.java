package tv.duojiao.dao.rec;

import org.springframework.stereotype.Component;
import tv.duojiao.model.rec.Portrait;
import tv.duojiao.core.Mapper;

import java.util.Date;
import java.util.List;

@Component
public interface PortraitMapper extends Mapper<Portrait> {
    List<String> selectAllKeywords(int uid);

    List<String> selectCoreKeywords(int uid);

    List<Portrait> selectByKeyword(String keyword);
    
    void updateByKeyword(int userId, String keyword, double number);

    Date selectByUpdateDate(int userId, String keyword);
}