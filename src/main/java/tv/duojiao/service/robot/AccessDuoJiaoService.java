package tv.duojiao.service.robot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpagePipeline;
import tv.duojiao.dao.ESClient;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.utils.HANLPExtractor;
import tv.duojiao.utils.RestUtil;
import tv.duojiao.utils.SpiderExtractor;
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
    @Autowired
    private HANLPExtractor nlpExtractor = new HANLPExtractor();
    @Autowired
    RestUtil restUtil;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String duojiaoDomain = "www.duojiao.tv";


    public AccessDuoJiaoService() {
        nlpExtractor = new HANLPExtractor();
    }


    public boolean insert() {
        String url = restUtil.DUOJIAO_HOST + "/api.php?mod=Recommend&act=getDjContents";
//        url = "http://ts.huaray.com/api.php?mod=Recommend&act=getDjContents";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        JSONArray jsonArray = responseEntity.getBody().getJSONArray("data");
        if (jsonArray.isEmpty()) {
            System.out.println("返回数据为空");
            return false;
        }
        LOG.info("开始获取多椒数据，获取量为{}，获取数据为：{}", jsonArray.size(), jsonArray.toJSONString());
        JSONObject data;
        Webpage webpage;
        for (int i = 0; i < jsonArray.size(); i++) {
            LOG.info("commonWebpagePipeline值为{}，nlpExtractor值为{}", commonWebpagePipeline, nlpExtractor);
            data = jsonArray.getJSONObject(i);
            long start = System.currentTimeMillis();
            String tempUrl = duojiaoDomain + "/p/" + data.getString("id");
            String idFromUrl = Hashing.md5().hashString(tempUrl, Charset.forName("utf-8")).toString();
            String content = data.getString("content");
            if (StringUtils.isAnyBlank(content, tempUrl)) {
                continue;
            }
            String contentWithoutHtml = SpiderExtractor.convertHtml2Text(content);
            Date publishTime = null;
            try {
                publishTime = simpleDateFormat.parse(data.getString("publishTime"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Map<String, Object> dynamicFields = Maps.newHashMap(), staticFields = Maps.newHashMap();
            dynamicFields.put("TextDetail", "来自好玩有趣的多椒");
            staticFields.put("GameCategory", data.getString("gameName"));
            LOG.info("Start Converter!");
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
            webpage.setKeywords(nlpExtractor.extractKeywords(contentWithoutHtml));
            webpage.setSummary(nlpExtractor.extractSummary(contentWithoutHtml));
            webpage.setNamedEntity(nlpExtractor.extractNamedEntity(contentWithoutHtml));
            webpage.setPublishTime(publishTime);
            webpage.setCategory(data.getString("category"));
            webpage.setRawHTML(UrlUtils.fixAllRelativeHrefs(content, tempUrl));
            webpage.setDynamicFields(dynamicFields);
            webpage.setStaticFields(staticFields);
            webpage.setAttachmentList(new ArrayList<>());
            webpage.setImageList(SpiderExtractor.getImageList(content));
            webpage.setProcessTime(System.currentTimeMillis() - start);
            if (commonWebpagePipeline == null) {
                System.err.println("CommonWebPagePipeline 为空");
                continue;
            }
            if (webpage != null)
                commonWebpagePipeline.insertData(webpage);
            else {
                System.err.println("Converter Failed!");
            }
            System.out.println("插入完成");
        }

        return true;
    }

}
