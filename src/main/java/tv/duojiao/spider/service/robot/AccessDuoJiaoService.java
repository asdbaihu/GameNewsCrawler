package tv.duojiao.spider.service.robot;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.spider.dao.CommonWebpagePipeline;
import tv.duojiao.spider.gather.commons.CommonSpider;
import tv.duojiao.spider.model.commons.Webpage;
import tv.duojiao.spider.utils.RestUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/21
 */
@Component
public class AccessDuoJiaoService {
    @Autowired
    CommonWebpagePipeline commonWebpagePipeline;
    @Autowired
    CommonSpider commonSpider;

    public boolean insert() {
        String url = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(url, JSONObject.class);
        responseEntity.getBody();


        //抽取关键词,10个词
//        page.putField("keywords", keywordsExtractor.extractKeywords(contentWithoutHtml));
//        //抽取摘要,5句话
//        page.putField("summary", summaryExtractor.extractSummary(contentWithoutHtml));
//        //抽取命名实体
//        page.putField("namedEntity", namedEntitiesExtractor.extractNamedEntity(contentWithoutHtml));
        Webpage webpage = new Webpage();
        commonWebpagePipeline.insertData(webpage);
        return true;
    }


}
