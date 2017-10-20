package tv.duojiao.service.quartz.subservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpagePipeline;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.utils.PageExtractor;
import tv.duojiao.utils.spider.NLPExtractor;
import tv.duojiao.utils.RestUtil;
import us.codecraft.webmagic.utils.UrlUtils;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * Description:
 * User: Yodes
 * Date: 2017/9/21
 */
@Component
public class AccessDuoJiaoService {
    private final static Logger LOG = LogManager.getLogger(AccessDuoJiaoService.class);
    @Autowired
    CommonWebpagePipeline commonWebpagePipeline;

    @Qualifier("HANLPExtractor")
    @Autowired
    private NLPExtractor keywordsExtractor;
    @Qualifier("HANLPExtractor")
    @Autowired
    private NLPExtractor summaryExtractor;
    @Qualifier("HANLPExtractor")
    @Autowired
    private NLPExtractor namedEntitiesExtractor;
    @Autowired
    RestUtil restUtil;
    @Value("${duojiao.domain.name}")
    String duojiaoDomain;

    private static int count = 0;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean insert() {
//        System.out.println("restUtil为：" + restUtil);
        if (restUtil == null) {
            LOG.warn("restUtil is null!");
            restUtil = new RestUtil();
        }
        String url = restUtil.DUOJIAO_HOST + "/api.php?mod=Recommend&act=getDjContents";
//        url = "http://ts.huaray.com/api.php?mod=Recommend&act=getDjContents";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
//        JSONArray jsonArray = responseEntity.getBody().getJSONArray("data");
        JSONArray jsonArray = testAPI();
        if (jsonArray.isEmpty()) {
            System.out.println("返回数据为空");
            return false;
        }
        LOG.info("开始获取多椒数据，获取量为{}，获取数据为：{}", jsonArray.size(), jsonArray.toJSONString());
        JSONObject data;
        Webpage webpage;
        for (int i = 0; i < jsonArray.size(); i++) {
            data = jsonArray.getJSONObject(i);
            long start = System.currentTimeMillis();
            String tempUrl = duojiaoDomain + "/p/" + data.getString("id");
            String idFromUrl = Hashing.md5().hashString(tempUrl, Charset.forName("utf-8")).toString();
            String content = data.getString("content");
            if (StringUtils.isAnyBlank(content, tempUrl)) {
                continue;
            }
            String contentWithoutHtml = PageExtractor.convertHtml2Text(content);
            Date publishTime = null;
            try {
                publishTime = simpleDateFormat.parse(data.getString("publishTime"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Map<String, Object> dynamicFields = Maps.newHashMap(), staticFields = Maps.newHashMap();
            dynamicFields.put("TextDetail", "来自好玩有趣的多椒");
            staticFields.put("GameCategory", data.getString("gameName"));
            webpage = new Webpage();
            webpage.setContent(content);
            webpage.setTitle(data.getString("title"));
            webpage.setUrl(tempUrl);
            webpage.setId(data.getString("id"));
            webpage.setAssistField("{\"From\": 2,\"id\": \"" + idFromUrl + "\"}");
            webpage.setDomain(duojiaoDomain);
            webpage.setSpiderInfoId("abcdefghijklmn");
            webpage.setGathertime(Calendar.getInstance().getTime());
            webpage.setSpiderUUID("poiuytrewq");
            webpage.setKeywords(keywordsExtractor.extractKeywords(contentWithoutHtml));
            webpage.setSummary(summaryExtractor.extractSummary(contentWithoutHtml));
            webpage.setNamedEntity(namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml));
            webpage.setPublishTime(publishTime);
            webpage.setCategory(data.getString("category"));
            webpage.setRawHTML(UrlUtils.fixAllRelativeHrefs(content, tempUrl));
            webpage.setDynamicFields(dynamicFields);
            webpage.setStaticFields(staticFields);
            webpage.setAttachmentList(new ArrayList<>());
            webpage.setImageList(PageExtractor.getImageList(content));
            webpage.setProcessTime(System.currentTimeMillis() - start);
            LOG.info("得到的webpage为：{}", webpage);
            commonWebpagePipeline.insertData(webpage);
            LOG.info("----------------------------");
        }

        return true;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public CommonWebpagePipeline getCommonWebpagePipeline() {
        return commonWebpagePipeline;
    }

    public void setCommonWebpagePipeline(CommonWebpagePipeline commonWebpagePipeline) {
        this.commonWebpagePipeline = commonWebpagePipeline;
    }

    public NLPExtractor getKeywordsExtractor() {
        return keywordsExtractor;
    }

    public void setKeywordsExtractor(NLPExtractor keywordsExtractor) {
        this.keywordsExtractor = keywordsExtractor;
    }

    public NLPExtractor getSummaryExtractor() {
        return summaryExtractor;
    }

    public void setSummaryExtractor(NLPExtractor summaryExtractor) {
        this.summaryExtractor = summaryExtractor;
    }

    public NLPExtractor getNamedEntitiesExtractor() {
        return namedEntitiesExtractor;
    }

    public void setNamedEntitiesExtractor(NLPExtractor namedEntitiesExtractor) {
        this.namedEntitiesExtractor = namedEntitiesExtractor;
    }

    public RestUtil getRestUtil() {
        return restUtil;
    }

    public void setRestUtil(RestUtil restUtil) {
        this.restUtil = restUtil;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        AccessDuoJiaoService.count = count;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }

    public String getDuojiaoDomain() {
        return duojiaoDomain;
    }

    public void setDuojiaoDomain(String duojiaoDomain) {
        this.duojiaoDomain = duojiaoDomain;
    }

    public JSONArray testAPI() {
        String data = "[{\n" +
                "      \"id\": \"" + count + "\",\n" +
                "      \"title\": \"" + count + "\",\n" +
                "      \"content\": \"" + (count % 3 == 0 ? "王者荣耀守望先锋哈哈哈" : "英雄联盟绝地逃生") + "\",\n" +
                "      \"category\": \"" + (count % 3 == 0 ? "攻略" : "资讯") + "\",\n" +
                "      \"gameName\": \"" + (count % 3 == 0 ? "王者荣耀" : "英雄联盟") + "\",\n" +
                "      \"publishTime\": \"2017-10-" + (count++) % 30 + " 15:01:27\"\n" +
                "    },\n" +
                "      {\"id\": \"" + count + "\",\n" +
                "      \"title\": \"" + count + "\",\n" +
                "      \"content\": \"" + (count % 3 == 0 ? "王者荣耀守望先锋哈哈哈" : "英雄联盟绝地逃生") + "\",\n" +
                "      \"category\": \"" + (count % 5 == 0 ? "攻略" : "资讯") + "\",\n" +
                "      \"gameName\": \"" + (count % 3 == 0 ? "王者荣耀" : "英雄联盟") + "\",\n" +
                "      \"publishTime\": \"2017-10-" + (count++) % 30 + " 15:01:27\"\n" +
                "    }]";

        JSONArray jsonArray = JSONObject.parseObject(data, JSONArray.class);
        return jsonArray;
    }
}
