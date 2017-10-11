package tv.duojiao.service.rec;


import tv.duojiao.core.Service;
import tv.duojiao.model.rec.Game;
import tv.duojiao.model.rec.Portrait;

import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2017/09/22.
 */
public interface PortraitService extends Service<Portrait> {
    /**
     * 获取用户核心画像
     * @return  所有画像关键词
     */
    List<String> selectCoreKeywords(Integer uid);

    /**
     * 获取用户核心画像关键词
     * @return  5个画像关键词
     */
    List<String> selectAllKeywords(Integer uid);

    /**
     *  根据关键词获取用户画像信息
     *  @return 用户画像信息
     */
    List<Portrait> selectByKeyword(String keyword);

    /**
     * 根据关键词更新用户画像
     * @param userId    用户ID
     * @param keyword   关键词
     * @param number    关键词权重增加
     * @return
     */
    void updateByKeyword(int userId, String keyword, double number);

    /**
     * 根据日期搜索该词条是否更新
     * @param userId    用户ID
     * @param keyword   关键词词条
     * @return
     */
    Date selectByUpdateDate(int userId, String keyword);
}
