package tv.duojiao.service.robot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpageDAO;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.model.rec.Feature.Feature;
import tv.duojiao.model.rec.Feature.NameEnity;
import tv.duojiao.service.rec.PortraitService;
import tv.duojiao.utils.JsonUtils;
import tv.duojiao.utils.RestUtil;
import tv.duojiao.utils.SpiderExtractor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static tv.duojiao.utils.RestUtil.*;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/25
 */
@Component
public class AccessLogService {
    private static final Logger LOG = LogManager.getLogger(AccessLogService.class);
    @Autowired
    public CommonWebpageDAO commonWebpageDAO;

    @Resource
    private PortraitService portraitService;

    @Autowired
    public RestUtil restUtil;

    @Value("${feature.folder.address}")
    private String featureAddress;

    public Feature getFeature(String gameName) {
        String str = JsonUtils.ReadFile("classpath:featureLibrary\\" + gameName + ".json");
        Feature feature = JSONObject.parseObject(str, Feature.class);
        return feature;
    }

    public boolean process(String behavior, String resourceId, Date time, int userId) {
        if (StringUtils.isAnyBlank(behavior, resourceId)) {
            return false;
        }
        Webpage webpage = commonWebpageDAO.getWebpageById(resourceId);
        List<String> list = webpage.getKeywords();
        Feature feature = getFeature(webpage.getStaticFields().get("GameCategory").toString());
        List<String> coreKeywords = new ArrayList<>(), otherKeywords = new ArrayList<>();
        list.forEach(s -> {
            feature.getData().forEach(subFeature -> {
                for (NameEnity nameEnity : subFeature.getContent()) {
                    if (StringUtils.containsAny(s, nameEnity.alias, nameEnity.name)) {
                        coreKeywords.add(s);
                    } else {
                        otherKeywords.add(s);
                    }
                }
            });
        });
        switch (behavior) {
            case "click": {
                coreKeywords.forEach(keyword -> {
                    portraitService.updateByKeyword(userId, keyword, 0.00005);
                });
                otherKeywords.forEach(keyword -> {
                    portraitService.updateByKeyword(userId, keyword, 0.00001);
                });
                break;
            }
            case "close": {
                coreKeywords.forEach(keyword -> {
                    if (portraitService.selectByUpdateDate(userId, keyword).after(SpiderExtractor.getFrontDate("MINUTE", 5)))
                        portraitService.updateByKeyword(userId, keyword, 0.00003);
                });
                otherKeywords.forEach(keyword -> {
                    if (portraitService.selectByUpdateDate(userId, keyword).after(SpiderExtractor.getFrontDate("MINUTE", 5)))
                        portraitService.updateByKeyword(userId, keyword, 0.000007);
                });
                break;
            }
            default: {
                LOG.warn("此资讯包含无法解析的动作{},资源ID:{},时间:{},用户ID:{}");
            }
        }
        return true;
    }


    public boolean updateLog() {
        String url = restUtil.DUOJIAO_HOST + "/api.php?mod=Recommend&act=getUserActivity";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONArray jsonArray = responseEntity.getBody().getJSONArray("data");
        String behavior, resourceId;
        Date time;
        int userId;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            behavior = jsonObject.getString("behavior");
            resourceId = jsonObject.getString("ResourceId");
            time = jsonObject.getDate("time");
            userId = jsonObject.getInteger("userID");
            process(behavior, resourceId, time, userId);
            LOG.info(behavior + " " + time + " " + resourceId + " " + userId);
        }
        return true;
    }
}
