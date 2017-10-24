package tv.duojiao.service.quartz.subservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpageDAO;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.model.rec.Feature.Feature;
import tv.duojiao.model.rec.Feature.NameEnity;
import tv.duojiao.service.rec.PortraitService;
import tv.duojiao.utils.spider.PageExtractor;
import tv.duojiao.utils.RestUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/25
 */
@Component
public class AccessUserLogService {
    private static final Logger LOG = LogManager.getLogger(AccessUserLogService.class);
    @Autowired
    public CommonWebpageDAO commonWebpageDAO;
    @Resource
    private PortraitService portraitService;
    @Autowired
    public RestUtil restUtil;
    @Value("${feature.folder.address}")
    private String featureAddress;
    @Value("${duojiao.domain.name}")
    private String duojiaoDomain;

    private static int count = 0;

    public Feature getFeature(String gameName) {
        org.springframework.core.io.Resource resource = new ClassPathResource(featureAddress + gameName + ".json");
        String str = null;
        try {
            str = FileUtils.readFileToString(Paths.get(resource.getURI()).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Set<String> coreKeywords = new HashSet<>(), otherKeywords = new HashSet<>();
        LOG.info("当前webpage细节：{}", webpage.toString());
        list.forEach(s -> {
            feature.getData().forEach(subFeature -> {
                for (NameEnity nameEnity : subFeature.getContent()) {
                    if (StringUtils.contains(nameEnity.alias, s) || StringUtils.contains(nameEnity.name, s)) {
                        coreKeywords.add(nameEnity.name);
                    } else {
                        otherKeywords.add(s);
                    }
                }
            });
        });
        LOG.warn("核心关键词为：{}，非核心关键词为：{}", coreKeywords.toString(), otherKeywords.toString());
        switch (behavior) {
            case "click": {
                coreKeywords.forEach(keyword -> {
                    LOG.warn("({})--- 点击 + 核心关键词", keyword);
                    portraitService.updateByKeyword(userId, keyword, 0.00005);
                });
                otherKeywords.forEach(keyword -> {
                    LOG.warn("({})--- 点击 + 其他关键词", keyword);
                    portraitService.updateByKeyword(userId, keyword, 0.00001);
                });
                break;
            }
            case "close": {
                coreKeywords.forEach(keyword -> {
                    if (portraitService.selectByUpdateDate(userId, keyword) != null) {
                        if (portraitService.selectByUpdateDate(userId, keyword).after(PageExtractor.getFrontDate(time, "MINUTE", 5))) {
                            portraitService.updateByKeyword(userId, keyword, 0.00003);
                            LOG.warn("({})--- 关闭 + 核心关键词 + 时间限制内", keyword);
                        }
                    }
                });
                otherKeywords.forEach(keyword -> {
                    if (portraitService.selectByUpdateDate(userId, keyword) != null) {
                        if (portraitService.selectByUpdateDate(userId, keyword).after(PageExtractor.getFrontDate(time, "MINUTE", 5))) {
                            portraitService.updateByKeyword(userId, keyword, 0.000007);
                            LOG.warn("({})--- 关闭 + 其他关键词 + 时间限制内", keyword);
                        }
                    }
                });
                break;
            }
            default: {
                LOG.warn("此资讯包含无法解析的动作{},对应的资源ID:{},时间:{},用户ID:{}", behavior, resourceId, time, userId);
            }
        }
        return true;
    }

    public String getUrl(int a) {
        return Hashing.md5().hashString(duojiaoDomain + "/p/" + a, Charset.forName("utf-8")).toString();
    }

    public JSONArray testLog() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = Calendar.getInstance().getTime();
        String data = "[{\n" +
                "      \"behavior\": \"" + (count % 2 == 0 ? "click" : "close") + "\",\n" +
                "      \"ResourceId\": \"" + getUrl(count) + "\",\n" +
                "      \"time\": \"" + simpleDateFormat.format(currentDate) + "\",\n" +
                "      \"userID\": \"1\",\n" +
                "    },\n" +
                "      {\"behavior\": \"" + (count % 3 == 0 ? "click" : "close") + "\",\n" +
                "      \"ResourceId\": \"" + getUrl(count++) + "\",\n" +
                "      \"time\": \"" + simpleDateFormat.format(currentDate) + "\",\n" +
                "      \"userID\": \"2\",\n" +
                "    }]";

        JSONArray jsonArray = JSONObject.parseObject(data, JSONArray.class);
        return jsonArray;
    }

    public boolean updateLog() {
        String url = restUtil.DUOJIAO_HOST + "/api.php?mod=Recommend&act=getUserActivity";
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
//        JSONArray jsonArray = responseEntity.getBody().getJSONArray("data");
        JSONArray jsonArray = testLog();
        LOG.warn("当前日志数量为：{}", jsonArray.size());
        if (jsonArray.size() == 0) {
            return false;
        }
        String behavior, resourceId;
        Date time;
        int userId;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            behavior = jsonObject.getString("behavior");
            resourceId = jsonObject.getString("ResourceId");
            time = jsonObject.getDate("time");
            userId = jsonObject.getInteger("userID");
            LOG.info(behavior + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) + " " + resourceId + " " + userId);
            //更新用户画像
            process(behavior, resourceId, time, userId);
        }
        return true;
    }

    public RestUtil getRestUtil() {
        return restUtil;
    }

    public void setRestUtil(RestUtil restUtil) {
        this.restUtil = restUtil;
    }
}
