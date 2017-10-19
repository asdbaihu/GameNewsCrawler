package tv.duojiao.service.quartz.SubService;

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
import tv.duojiao.model.robots.NewsEnity;
import tv.duojiao.model.robots.ResultEntity;
import tv.duojiao.model.robots.ResultList;
import tv.duojiao.model.robots.WebpageEnity;
import tv.duojiao.utils.BloomFilterUtil;
import tv.duojiao.utils.RPCUtil;
import tv.duojiao.utils.RestUtil;

import java.util.*;

import static tv.duojiao.utils.RestUtil.*;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/11
 */
@Component
public class PublishService {
    private final static Logger LOG = LogManager.getLogger(PublishService.class);
    private BloomFilter bloomFilter;
    private MultiValueMap<String, String> paramMap;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestUtil restUtil;

    @Autowired
    private RPCUtil rpcUtil;

    @Autowired
    public PublishService() {
    }

//    public static void main(String[] args) throws BindException, InterruptedException {
//        PublishService sp = new PublishService(new TaskManager());
////        sp.getGameList().forEach((k, v) -> System.out.println(k + " " + v + "\t"));
//        sp.publishStrategyAndTopic();
//    }

    public boolean publishNews(){

        return true;
    }


    /**
     * 发布游戏攻略及话题
     */
    public boolean publishStrategyAndTopic() {
        if (isLogin() && getGameList().size() > 0) {
            getGameList().forEach((id, name) -> publishStrategyAndTopic(id + "", name));
            if (getMountainList().size() > 0) {
                getMountainList().forEach((id, name) -> publishTopic(id, rpcUtil.getGameOfMountains(id).split(" ")));
            }
            return true;
        }
        return false;
    }

    /**
     * 重置布隆过滤器
     *
     * @return
     */
    public boolean resetBloomFilter() {
        return BloomFilterUtil.reset();
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
            rpcUtil.getNews(name, "", "5").forEach(resultList -> mixList.add(resultList));
        }
        for (ResultList resultList : mixList) {
            if (bloomFilter.mightContain(resultList.id)) {
                continue;
            } else {
                ResultEntity result = rpcUtil.getWebpageById(resultList.id);
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
                        restUtil.DUOJIAO_HOST + "/api.php?mod=Weibo&act=post_weibo",
                        paramMap,
                        "status", "msg"
                );
                String status = res.get("status").toString();
//                String msg = res.get("msg").toString();
//                System.out.println(status + " " + msg);
//                System.out.println(oauth_token + "  -hhh-  " + oauth_token_secret);
                bloomFilter.put(resultList.id);
                BloomFilterUtil.putSize(1);
                if ("1".equals(status)) {
                    LOG.info("[话题发布成功]：--{}-- title:{}  id:{}", Arrays.toString(gameName), rpcUtil.getWebpageById(resultList.id).title, resultList.id);
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
    public boolean publishStrategyAndTopic(String gameId, String gameName) {
        ArrayList<ResultList> list = rpcUtil.getNews(gameName, "攻略");
        bloomFilter = BloomFilterUtil.getInstance();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("", "");
        for (ResultList resultList : list) {
            if (bloomFilter.mightContain(resultList.id)) {
//                System.out.println("已存在：" + resultList.id);
                continue;
            } else {
                ResultEntity result = rpcUtil.getWebpageById(resultList.id);
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
                        restUtil.DUOJIAO_HOST + "/api.php?mod=Weibo&act=post_weibo",
                        paramMap,
                        "status", "msg"
                );
                String status = res.get("status").toString();
//                String msg = res.get("msg").toString();
//                System.out.println(status + " " + msg);
//                System.out.println(oauth_token + "  -hhh-  " + oauth_token_secret);
                bloomFilter.put(resultList.id);
                BloomFilterUtil.putSize(1);
                if ("1".equals(status)) {
                    LOG.info("[攻略发布成功]：--{}-- title:{}  id:{}", gameName, rpcUtil.getWebpageById(resultList.id).title, resultList.id);
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
                restUtil.DUOJIAO_HOST + "/api.php?mod=User&act=info",
                paramMap,
                "status"
        ).get("status").toString();
//        System.out.println("status为" + status);
        if (!"1".equals(status)
                || StringUtils.isAnyBlank(oauth_token, oauth_token_secret)) {
            System.out.println("认证失效");
            paramMap = new LinkedMultiValueMap<>();
            paramMap.add("format", "json");
            paramMap.add("login", restUtil.adminUserName);
            paramMap.add("password", restUtil.adminPassword);
            paramMap.add("api_version", restUtil.apiVersion);
            Map<String, Object> result = RestUtil.postMessage(
                    restUtil.DUOJIAO_HOST + "/api.php?mod=Oauth&act=authorize",
                    paramMap,
                    "status", "oauth_token", "oauth_token_secret"
            );
            String statusOA = result.get("status").toString();
            if (statusOA.equals("1")) {
                oauth_token = result.get("oauth_token").toString();
                oauth_token_secret = result.get("oauth_token_secret").toString();
                return true;
            } else {
                LOG.info("[登录失败]用户：{} +密码：{}\t，请检查网络连接或账号密码", restUtil.adminUserName, restUtil.adminPassword);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取山头列表
     *
     * @return Map
     */
    public Map<String, String> getMountainList() {
        Map<String, String> mountainList = new HashMap<>();
        paramMap = new LinkedMultiValueMap();
        paramMap.add("api_version", restUtil.apiVersion);
        paramMap.add("oauth_token", oauth_token);
        paramMap.add("oauth_token_secret", oauth_token_secret);
        String data = "";
        try {
            data = RestUtil.postMessage(
                    restUtil.DUOJIAO_HOST + "/api.php?mod=Mountain&act=get_mountains",
                    paramMap,
                    "data"
            ).get("data").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        ArrayList<JSONObject> list = JSONObject.parseObject(data, ArrayList.class);
        list.forEach(jsonObject -> mountainList.put(jsonObject.getString("id"), jsonObject.getString("name")));
        return mountainList;
    }

    /**
     * 获取游戏列表
     *
     * @return Map
     */
    public Map<Integer, String> getGameList() {
        Map<Integer, String> gameList = new HashMap<Integer, String>();
        restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(
                restUtil.DUOJIAO_HOST + "/api.php?mod=Game&act=getAll", JSONObject.class);
        JSONArray json = responseEntity.getBody().getJSONArray("data");
        for (Object object : json) {
            JSONObject jsonObject = (JSONObject) object;
            gameList.put(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("name"));
        }
        return gameList;
    }


}




