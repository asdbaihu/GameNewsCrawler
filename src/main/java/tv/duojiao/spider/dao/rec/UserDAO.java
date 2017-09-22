package tv.duojiao.spider.dao.rec;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tv.duojiao.spider.model.rec.UserEnity;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/22
 */
@Component
public interface UserDAO {
    UserEnity selectById(@Param("uid")int uid);

    List<UserEnity> selectAll();
}
