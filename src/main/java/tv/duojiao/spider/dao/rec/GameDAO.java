package tv.duojiao.spider.dao.rec;

import org.apache.ibatis.annotations.Param;
import tv.duojiao.spider.model.rec.GameEnity;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/22
 */

public interface GameDAO {
    GameEnity selectById(@Param("gid")int gid);
    
    List<GameEnity> selectAll();
    
    int delectById(@Param("gid")int gid);
}
