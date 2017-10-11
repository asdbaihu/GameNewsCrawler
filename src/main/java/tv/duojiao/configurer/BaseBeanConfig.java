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
import tv.duojiao.service.robot.PublishService;
import tv.duojiao.service.robot.RobotService;
import tv.duojiao.utils.HANLPExtractor;
import tv.duojiao.utils.StaticValue;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/23
 */
@Configuration
public class BaseBeanConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate(getSimpleClientHttpRequestFactory());
        return template;
    }

    @Bean
    public SimpleClientHttpRequestFactory getSimpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory schf = new SimpleClientHttpRequestFactory();
        schf.setConnectTimeout(10000);
        schf.setReadTimeout(10000);
        return schf;
    }

    @Bean
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean sfb = new SchedulerFactoryBean();
        return sfb;
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
    public PublishService getPublishService() throws BindException, InterruptedException {
        PublishService publishService = new PublishService(getTaskManager());
        return publishService;
    }

    @Bean
    public RobotService getRobotService(){
        return new RobotService();
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
