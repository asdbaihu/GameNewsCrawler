package tv.duojiao.service.rec;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpageDAO;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.model.rec.RecommendEnity;
import tv.duojiao.utils.RestUtil;

import java.util.ArrayList;
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
        List<String> gameList = getGameList(uid);
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
        if (recSize < 10) {
            return commonWebpageDAO.searchByGame("", games, "", size, page);
        } else {
            return webpagePair.getLeft();
        }
    }

    /**
     * 获取用户关注的游戏列表
     *
     * @param uid 用户id
     * @return
     */
    public List<String> getGameList(int uid) {
        RestTemplate restTemplate = RestUtil.getRestTemplate();
        List<String> gameList = new ArrayList<>();
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(
                new RestUtil().DUOJIAO_HOST + "/api.php?mod=Game&act=getMyFollow" + "&uid=" + uid
                , JSONObject.class);
        JSONArray data = jsonObject.getBody().getJSONArray("data");
        data.forEach(o -> {
            JSONObject temp = JSONObject.parseObject(o.toString());
            gameList.add(temp.getString("name"));
        });
        LOG.info("{}.GameList: {}", uid, gameList.toString());
        return gameList;
    }
}
