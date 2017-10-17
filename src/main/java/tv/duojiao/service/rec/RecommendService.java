package tv.duojiao.service.rec;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.duojiao.dao.CommonWebpageDAO;
import tv.duojiao.model.rec.RecommendEnity;
import tv.duojiao.utils.RPCUtil;
import tv.duojiao.utils.RestUtil;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
@Component
public class RecommendService {
    private final static Logger LOG = LogManager.getLogger(RecommendService.class);
    @Autowired
    private PortraitService portraitService;
    @Autowired
    private CommonWebpageDAO commonWebpageDAO;
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private RPCUtil rpcUtil;

    /**
     * 获取推荐列表
     *
     * @param uid  用户id
     * @param size 页面大小
     * @param page 页码
     * @return
     */
    public List<RecommendEnity> getRecommendList(int uid, int size, int page) {
        List<String> keyList = portraitService.selectAllKeywords(uid);
        List<String> gameList = rpcUtil.getUserFollowGames(uid);
        String keys = "", games = "";
        for (String key : keyList) {
            keys += key + " ";
        }
        for (String game : gameList) {
            games += game + "";
        }
        LOG.info("User[{}].recommend.keys: {}", uid, keys);
        LOG.info("User[{}].recommend.games: {}", uid, games);
        Pair<List<RecommendEnity>, Long> webpagePair = commonWebpageDAO.getWebpageByKeywords(keys, games, size, page);
        Long recSize = webpagePair.getRight();
        LOG.info("User[{}].recommed.ListByKeyword.size: {}", uid, recSize);
        if (gameList.size() < 1 || keyList.size() < 5) {
            return commonWebpageDAO.searchByGame("", games, "", size, page);
        } else {
            return webpagePair.getLeft();
        }
    }
}
