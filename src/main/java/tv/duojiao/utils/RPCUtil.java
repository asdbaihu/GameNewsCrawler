package tv.duojiao.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/16
 */
@Component
public class RPCUtil {
    public static Logger LOG = LogManager.getLogger(RPCUtil.class);
    @Autowired
    private RestUtil restUtil;

    /**
     * 根据游戏名称获取游戏ID
     *
     * @param gname 游戏名称
     * @return 游戏ID
     */
    public int getGidByGname(String gname) {
        RestTemplate restTemplate = restUtil.getRestTemplate();
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(
                restUtil.DUOJIAO_HOST + "/api.php?mod=Game&act=getDetail" + "&gname=" + gname
                , JSONObject.class);
        JSONObject data = jsonObject.getBody().getJSONObject("data");
        return data.getInteger("id");
    }

    /**
     * 根据游戏ID获取游戏名称
     *
     * @param gid 游戏ID
     * @return 游戏名称
     */
    public String getGidByGname(int gid) {
        RestTemplate restTemplate = restUtil.getRestTemplate();
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(
                restUtil.DUOJIAO_HOST + "/api.php?mod=Game&act=getDetail" + "&gid=" + gid
                , JSONObject.class);
        JSONObject data = jsonObject.getBody().getJSONObject("data");
        return data.getString("name");
    }

    /**
     * 获取用户关注的游戏列表
     *
     * @param uid 用户id
     * @return
     */
    public List<String> getUserFollowGames(int uid) {
        RestTemplate restTemplate = restUtil.getRestTemplate();
        List<String> gameList = new ArrayList<>();
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(
                restUtil.DUOJIAO_HOST + "/api.php?mod=Game&act=getMyFollow" + "&uid=" + uid
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
