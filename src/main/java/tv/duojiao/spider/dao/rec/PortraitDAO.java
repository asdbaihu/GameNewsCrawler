package tv.duojiao.spider.dao.rec;

import org.apache.ibatis.annotations.Param;
import tv.duojiao.spider.model.rec.PortraitEnity;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/22
 */
public interface PortraitDAO {
    List<PortraitEnity> selectAll();

    PortraitEnity selectById(@Param("pid")int pid);

    List<PortraitEnity> selectByUid(@Param("pid")int pid);

    Boolean updatePortrait(@Param("pid")int pid);
}
