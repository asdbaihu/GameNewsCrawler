package tv.duojiao.spider.service.robot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.spider.gather.async.AsyncGather;
import tv.duojiao.spider.gather.async.TaskManager;
import tv.duojiao.spider.model.robots.NewsEnity;
import tv.duojiao.spider.model.robots.ResultEnity;
import tv.duojiao.spider.model.robots.ResultList;
import tv.duojiao.spider.model.robots.WebpageEnity;
import tv.duojiao.spider.utils.BloomFilterUtil;
import tv.duojiao.spider.utils.RestUtil;
import tv.duojiao.spider.utils.StaticValue;

import java.net.BindException;
import java.util.*;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/11
 */
@Component
public class PublishService extends AsyncGather {
    private final static Logger LOG = LogManager.getLogger(PublishService.class);
    private BloomFilter bloomFilter;
    private final String HOST_NAME_TEST = "http://localhost:8080";
    private final String HOST_NAME_PRODUCT = "http://47.95.37.151:8080";
    @Autowired
    private RestTemplate restTemplate;
    private MultiValueMap<String, String> paramMap;
    private String oauth_token;
    private String oauth_token_secret;

    private String adminUserName = "多仔";
    private String adminPassword = "111111";
    private String apiVersion = "4.5.0";

    private final String DUOJIAO_HOST_TEST = "http://ts.huaray.com";
    private final String DUOJIAO_HOST_PRODUCT = "http://admin.duojiao.tv";

    private String HOST_NAME = HOST_NAME_TEST;
    private String DUOJIAO_HOST = DUOJIAO_HOST_TEST;

    public PublishService() {
    }

    @Autowired
    public PublishService(TaskManager taskManager) throws InterruptedException, BindException {
        this.taskManager = taskManager;
    }

    public static void main(String[] args) throws BindException, InterruptedException {
        PublishService sp = new PublishService(new TaskManager());
//        sp.getGameList().forEach((k, v) -> System.out.println(k + " " + v + "\t"));
        sp.publicshAll();
    }


    /**
     * 机器人功能服务
     */
    public boolean publicshAll() {
        if (isLogin() && getGameList().size() > 0) {
            getGameList().forEach((id, name) -> publishStrategy(id + "", name));
            if (getMountainList().size() > 0)
                getMountainList().forEach((id, name) -> publishTopic(id, getGameOfMountains(id).split(" ")));
            return true;
        }
        return false;
    }

    /**
     * 发布话题
     *
     * @param gameName 游戏名称（多个）
     * @return 发布成功与否
     */
    public boolean publishTopic(String mountainId, String... gameName) {
        ArrayList<ResultList> mixList = new ArrayList<>();
        bloomFilter = BloomFilterUtil.getInstance();
        for (String name : gameName) {
            getNews(name, "", "5").forEach(resultList -> mixList.add(resultList));
        }
        for (ResultList resultList : mixList) {
            if (bloomFilter.mightContain(resultList.id)) {
                continue;
            } else {
                ResultEnity result = getWebpageById(resultList.id);
                paramMap.add("type", "postmountain");
                paramMap.add("mountain_id", mountainId);
                paramMap.add("title", result.title);
                paramMap.add("content", result.content);
                paramMap.add("from", "0");
                paramMap.add("latitude", "28.213238");
                paramMap.add("longitude", "112.884766");
                paramMap.add("oauth_token", oauth_token);
                paramMap.add("oauth_token_secret", oauth_token_secret);

                Map res = RestUtil.postMessage(
                        DUOJIAO_HOST + "/api.php?mod=Weibo&act=post_weibo",
                        paramMap,
                        "status", "msg"
                );
                String status = res.get("status").toString();
//                String msg = res.get("msg").toString();
//                System.out.println(status + " " + msg);
//                System.out.println(oauth_token + "  -hhh-  " + oauth_token_secret);
                bloomFilter.put(resultList.id);
                if ("1".equals(status)) {
                    LOG.info("[话题发布成功]：--{}-- title:{}  id:{}", Arrays.toString(gameName), getWebpageById(resultList.id).title, resultList.id);
                    return true;
                } else {
                    LOG.error("[话题发布失败]： -- {}--- 理由可能是密码错误或者认证过期", Arrays.toString(gameName));
                    return false;
                }
            }
        }
        LOG.warn("发布【{}】话题失败，理由可能是没有更新的话题，或所有话题均已被使用", Arrays.toString(gameName));
        return false;
    }

    /**
     * 发布资讯
     *
     * @param gameId   游戏ID
     * @param gameName 游戏名称
     * @return 发布成功与否
     */
    public boolean publishStrategy(String gameId, String gameName) {
        ArrayList<ResultList> list = getNews(gameName, "攻略");
        bloomFilter = BloomFilterUtil.getInstance();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("", "");
        for (ResultList resultList : list) {
            if (bloomFilter.mightContain(resultList.id)) {
//                System.out.println("已存在：" + resultList.id);
                continue;
            } else {
                ResultEnity result = getWebpageById(resultList.id);
                paramMap.add("type", "poststrategy");
                paramMap.add("game_id", gameId);
                paramMap.add("title", result.title);
                paramMap.add("content", result.content);
                paramMap.add("from", "0");
                paramMap.add("latitude", "28.213238");
                paramMap.add("longitude", "112.884766");
                paramMap.add("oauth_token", oauth_token);
                paramMap.add("oauth_token_secret", oauth_token_secret);

                Map res = RestUtil.postMessage(
                        DUOJIAO_HOST + "/api.php?mod=Weibo&act=post_weibo",
                        paramMap,
                        "status", "msg"
                );
                String status = res.get("status").toString();
//                String msg = res.get("msg").toString();
//                System.out.println(status + " " + msg);
//                System.out.println(oauth_token + "  -hhh-  " + oauth_token_secret);
                bloomFilter.put(resultList.id);
                if ("1".equals(status)) {
                    LOG.info("[攻略发布成功]：--{}-- title:{}  id:{}", gameName, getWebpageById(resultList.id).title, resultList.id);
                    return true;
                } else {
                    LOG.error("发布【{}】攻略失败，理由可能是密码错误或者认证过期", gameName);
                    return false;
                }
            }
        }
        LOG.warn("发布【{}】攻略失败，理由可能是没有更新的攻略，或所有攻略均已被使用", gameName);
        return false;
    }

    /**
     * 验证登录成功与否，并更新oauth_token
     *
     * @return 登录结果
     */
    public boolean isLogin() {
        paramMap = new LinkedMultiValueMap();
        paramMap.add("oauth_token", oauth_token);
        paramMap.add("oauth_token_secret", oauth_token_secret);

        String status = RestUtil.postMessage(
                DUOJIAO_HOST + "/api.php?mod=User&act=info",
                paramMap,
                "status"
        ).get("status").toString();
//        System.out.println("status为" + status);
        if (!"1".equals(status)
                || StringUtils.isAnyBlank(oauth_token, oauth_token_secret)) {
            System.out.println("认证失效");
            paramMap = new LinkedMultiValueMap<>();
            paramMap.add("format", "json");
            paramMap.add("login", adminUserName);
            paramMap.add("password", adminPassword);
            paramMap.add("api_version", apiVersion);
            Map<String, Object> result = RestUtil.postMessage(
                    DUOJIAO_HOST + "/api.php?mod=Oauth&act=authorize",
                    paramMap,
                    "status", "oauth_token", "oauth_token_secret"
            );
            String statusOA = result.get("status").toString();
            if (statusOA.equals("1")) {
                oauth_token = result.get("oauth_token").toString();
                oauth_token_secret = result.get("oauth_token_secret").toString();
                return true;
            } else {
                LOG.info("[登录失败]用户：{} +密码：{}\t，请检查网络连接或账号密码", adminUserName, adminPassword);
                return false;
            }
        }
        return true;
    }

    public Map<String, String> getMountainList() {
        Map<String, String> mountainList = new HashMap<>();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("api_version", apiVersion);
        paramMap.add("oauth_token", oauth_token);
        paramMap.add("oauth_token_secret", oauth_token_secret);
        String data;
        data = RestUtil.postMessage(
                DUOJIAO_HOST + "/api.php?mod=Mountain&act=get_mountains",
                paramMap,
                "data"
        ).get("data").toString();
        ArrayList<JSONObject> list = JSONObject.parseObject(data, ArrayList.class);
        list.forEach(jsonObject -> mountainList.put(jsonObject.getString("id"), jsonObject.getString("name")));
        return mountainList;
    }

    public Map<Integer, String> getGameList() {
        Map<Integer, String> gameList = new HashMap<Integer, String>();
        restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(
                DUOJIAO_HOST + "/api.php?mod=Game&act=getAll", JSONObject.class);
        JSONArray json = responseEntity.getBody().getJSONArray("data");
        for (Object object : json) {
            JSONObject jsonObject = (JSONObject) object;
            gameList.put(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("name"));
        }
        return gameList;
    }

    public String getGameOfMountains(String mountainId) {
        StringBuffer gameNameList = new StringBuffer();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("mid", mountainId);
        paramMap.add("api_version", apiVersion);
        paramMap.add("oauth_token", oauth_token);
        paramMap.add("oauth_token_secret", oauth_token_secret);
        String data = RestUtil.postMessage(
                DUOJIAO_HOST + "/api.php?mod=Mountain&act=get_mountain_games",
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
        String url = HOST_NAME + "/GameNews/commons/webpage/searchByGame?query=&gameName="
                + gameName + "&category=" + category + "&size=" + size;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject data = responseEntity.getBody();
        NewsEnity js = data.toJavaObject(NewsEnity.class);
        ArrayList<ResultList> resultList = js.resultList;
//        resultList.forEach(items -> System.out.println(items.id));
        restTemplate = null;
        return resultList;
    }

    /**
     * 获取资讯（默认20条）
     *
     * @param gameName 游戏名称
     * @param category 资讯分类
     * @return 资讯列表
     */
    public ArrayList<ResultList> getNews(String gameName, String category) {
        restTemplate = new RestTemplate();
        String url = HOST_NAME + "/GameNews/commons/webpage/searchByGame?query=&gameName="
                + gameName + "&category=" + category;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject data = responseEntity.getBody();
        NewsEnity js = data.toJavaObject(NewsEnity.class);
        ArrayList<ResultList> resultList = js.resultList;
//        resultList.forEach(items -> System.out.println(items.id));
        restTemplate = null;
        return resultList;
    }

    /**
     * 得到
     *
     * @param id
     * @return
     */
    public ResultEnity getWebpageById(String id) {
        restTemplate = new RestTemplate();
        String url = HOST_NAME + "/GameNews/commons/webpage/getWebpageById?id=" + id;
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONObject jsonObject = responseEntity.getBody();
//        System.out.println(jsonObject.toJSONString());
        restTemplate = null;
        return jsonObject.toJavaObject(WebpageEnity.class).getResult();
    }

}




