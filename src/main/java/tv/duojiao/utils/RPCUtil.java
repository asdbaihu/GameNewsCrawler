package tv.duojiao.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.model.corn.*;

import java.util.ArrayList;
import java.util.List;

import static tv.duojiao.utils.RestUtil.oauth_token;
import static tv.duojiao.utils.RestUtil.oauth_token_secret;

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
    private RestTemplate restTemplate;
    private MultiValueMap<String, String> paramMap;

    public List<UserEntity> getDJOffcialUsers() {
        restTemplate = new RestTemplate();
        List<UserEntity> userList = new ArrayList<>();
        ResponseEntity<JSONObject> jsonObject = restTemplate.getForEntity(
                restUtil.DUOJIAO_HOST + "/api.php?mod=User&act=get_dj_official_users"
                , JSONObject.class);
        JSONArray data = jsonObject.getBody().getJSONArray("data");
        data.forEach(o -> {
            UserEntity userEntity = JSONObject.parseObject(o.toString(), UserEntity.class);
            userList.add(userEntity);
        });
        return userList;
    }

    /**
     * 根据游戏名称获取游戏ID
     *
     * @param gname 游戏名称
     * @return 游戏ID
     */
    public int getGidByGname(String gname) {
        restTemplate = new RestTemplate();
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
        restTemplate = new RestTemplate();
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
        restTemplate = new RestTemplate();
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

    /**
     * 获取山头关联的游戏
     *
     * @param mountainId
     * @return "game1 game2 game3 ..."
     */
    public String getGameOfMountains(String mountainId) {
        restTemplate = new RestTemplate();
        StringBuffer gameNameList = new StringBuffer();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("mid", mountainId);
        paramMap.add("api_version", restUtil.apiVersion);
        paramMap.add("oauth_token", oauth_token);
        paramMap.add("oauth_token_secret", oauth_token_secret);
        String data = RestUtil.postMessage(
                restUtil.DUOJIAO_HOST + "/api.php?mod=Mountain&act=get_mountain_games",
                paramMap,
                "data"
        ).get("data").toString();
        ArrayList<JSONObject> list = JSONObject.parseObject(data, ArrayList.class);
        list.forEach(jsonObject -> gameNameList.append(jsonObject.getString("name") + " "));
        return gameNameList.toString().trim();
    }

    /**
     * 获取资讯
     *
     * @param gameName 游戏名称
     * @param category 资讯分类
     * @param size     资讯数量
     * @return 资讯列表
     */
    public ArrayList<ResultList> getNews(String gameName, String category, String size) {
        restTemplate = new RestTemplate();
        String url = restUtil.HOST_NAME + "/GameNews/commons/webpage/searchByGame?query=&gameName="
                + gameName + "&category=" + category + "&size=" + size;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject data = responseEntity.getBody();
        NewsEntity js = data.toJavaObject(NewsEntity.class);
        ArrayList<ResultList> resultList = js.resultList;
//        resultList.forEach(items -> System.out.println(items.id));
        return resultList;
    }

    /**
     * 获取资讯（默认20条）
     *
     * @param gameName 游戏名称
     * @param category 资讯分类
     * @return 资讯列表
     */
    public ArrayList<ResultList> getNews(String gameName, String category) throws NullPointerException {
        restTemplate = new RestTemplate();
        String url = restUtil.HOST_NAME + "/GameNews/commons/webpage/searchByGame?query=&gameName="
                + gameName + "&category=" + category;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject data = responseEntity.getBody();
        NewsEntity js = data.toJavaObject(NewsEntity.class);
        ArrayList<ResultList> resultList = js.resultList;
//        resultList.forEach(items -> System.out.println(items.id));
        return resultList;
    }

    /**
     * 根据ID得到资讯详情Entity
     *
     * @param id
     * @return ResultEntity
     */
    public ResultEntity getWebpageById(String id) {
        restTemplate = new RestTemplate();
        String url = restUtil.HOST_NAME + "/GameNews/commons/webpage/getWebpageById?id=" + id;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject jsonObject = responseEntity.getBody();
//        System.out.println(jsonObject.toJSONString());
        restTemplate = null;
        return jsonObject.toJavaObject(WebpageEntity.class).getResult();
    }


}
