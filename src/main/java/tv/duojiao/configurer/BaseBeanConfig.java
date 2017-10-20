package tv.duojiao.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.dao.CommonWebpageDAO;
import tv.duojiao.dao.CommonWebpagePipeline;
import tv.duojiao.dao.ESClient;
import tv.duojiao.dao.SpiderInfoDAO;
import tv.duojiao.gather.async.AsyncGather;
import tv.duojiao.gather.async.TaskManager;
import tv.duojiao.gather.commons.CasperjsDownloader;
import tv.duojiao.gather.commons.CommonSpider;
import tv.duojiao.gather.commons.ContentLengthLimitHttpClientDownloader;
import tv.duojiao.service.quartz.CornService;
import tv.duojiao.service.quartz.subservice.AccessDuoJiaoService;
import tv.duojiao.service.quartz.subservice.AccessUserLogService;
import tv.duojiao.service.quartz.subservice.PublishService;
import tv.duojiao.service.quartz.subservice.TestQuartzService;
import tv.duojiao.utils.spider.HANLPExtractor;
import tv.duojiao.utils.RestUtil;
import tv.duojiao.utils.spider.StaticValue;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.BindException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/23
 */
@Configuration
public class BaseBeanConfig {
    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Map<String, Object> map = new HashMap<>();
        map.put("testQuartzService", getTestQuartzService());
        map.put("cornService", getCornService());
        map.put("accessDuoJiaoService", getAccessDuoJiaoService());
        map.put("accessUserLogService", getAccessUserLogService());
        map.put("publishService", getPublishService());

        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        schedulerFactoryBean.setSchedulerContextAsMap(map);
        return schedulerFactoryBean;
    }

    @Bean
    public CornService getCornService() {
        CornService cornService = new CornService();
        cornService.setAccessDuoJiaoService(getAccessDuoJiaoService());
        cornService.setPublishService(getPublishService());
        return cornService;
    }

    @Bean
    public AccessDuoJiaoService getAccessDuoJiaoService() {
        AccessDuoJiaoService accessDuoJiaoService = new AccessDuoJiaoService();
        accessDuoJiaoService.setKeywordsExtractor(getHANlpExtractor());
        accessDuoJiaoService.setNamedEntitiesExtractor(getHANlpExtractor());
        accessDuoJiaoService.setSummaryExtractor(getHANlpExtractor());
        accessDuoJiaoService.setRestUtil(getRestUtil());
        return accessDuoJiaoService;
    }

    @Bean
    public AccessUserLogService getAccessUserLogService() {
        AccessUserLogService accessUserLogService = new AccessUserLogService();
        accessUserLogService.setRestUtil(getRestUtil());
        return accessUserLogService;
    }

    @Bean
    public HANLPExtractor getHANlpExtractor() {
        return new HANLPExtractor();
    }

    @Bean
    public TestQuartzService getTestQuartzService() {
        TestQuartzService testQuartzService = new TestQuartzService();
        return testQuartzService;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate(getSimpleClientHttpRequestFactory());
        return template;
    }

    @Bean
    public RestUtil getRestUtil() {
        RestUtil restUtil = new RestUtil();
        return restUtil;
    }

    @Bean
    public SimpleClientHttpRequestFactory getSimpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory schf = new SimpleClientHttpRequestFactory();
        schf.setConnectTimeout(10000);
        schf.setReadTimeout(10000);
        return schf;
    }

    @Bean
    public AsyncGather geAsyncGather() {
        return new AsyncGather();
    }

    @Bean
    public StaticValue getStaticValue() {
        return new StaticValue();
    }

    @Bean
    public TaskManager getTaskManager() {
        return new TaskManager();
    }

    @Bean
    public SpiderInfoDAO getSpiderInfoDAO() {
        return new SpiderInfoDAO();
    }

    @Bean
    public ContentLengthLimitHttpClientDownloader getContentLengthLimitHttpClientDownloader() {
        return new ContentLengthLimitHttpClientDownloader();
    }

    @Bean
    public CasperjsDownloader getCasperjsDownloader() {
        return new CasperjsDownloader();
    }

    @Bean
    public PublishService getPublishService() {
        PublishService publishService = new PublishService();
        return publishService;
    }

    @Bean
    public AccessDuoJiaoService accessDuoJiaoService() {
        AccessDuoJiaoService accessDuoJiaoService = new AccessDuoJiaoService();
        accessDuoJiaoService.setKeywordsExtractor(new HANLPExtractor());
        accessDuoJiaoService.setSummaryExtractor(new HANLPExtractor());
        accessDuoJiaoService.setNamedEntitiesExtractor(new HANLPExtractor());
        return accessDuoJiaoService;
    }

    @Bean
    public ESClient getEsClient() {
        return new ESClient().setStaticValue(getStaticValue());
    }

    @Bean
    public CommonWebpageDAO getCommonWebpageDAO() {
        CommonWebpageDAO commonWebpageDAO = new CommonWebpageDAO(getEsClient());
        return commonWebpageDAO;
    }

    @Bean
    public CommonWebpagePipeline getCommonWebpagePipeline() {
        return new CommonWebpagePipeline(getEsClient());
    }

    public List<Pipeline> getPipelineList() {
        List<Pipeline> pipelineList = new ArrayList<Pipeline>();
//        pipelineList.add(new CommonWebpageRedisPipeline(getStaticValue()));   // Redis输出
        pipelineList.add(getCommonWebpagePipeline());                         // ES输出
//        pipelineList.add(new JsonFilePipeline());                             // Json 输出
        return pipelineList;
    }

    @Bean
    public CommonSpider getCommonSpider() throws BindException, InterruptedException {
        CommonSpider commonSpider = new CommonSpider(getTaskManager(), getStaticValue());
        commonSpider.setCommonWebpageDAO(getCommonWebpageDAO());
        commonSpider.setSpiderInfoDAO(getSpiderInfoDAO());
        commonSpider.setCommonWebpagePipeline(getCommonWebpagePipeline());
        commonSpider.setContentLengthLimitHttpClientDownloader(getContentLengthLimitHttpClientDownloader());
        commonSpider.setKeywordsExtractor(new HANLPExtractor());
        commonSpider.setSummaryExtractor(new HANLPExtractor());
        commonSpider.setNamedEntitiesExtractor(new HANLPExtractor());
        commonSpider.setCasperjsDownloader(getCasperjsDownloader());
        commonSpider.setPipelineList(getPipelineList());
        return commonSpider;
    }

}
