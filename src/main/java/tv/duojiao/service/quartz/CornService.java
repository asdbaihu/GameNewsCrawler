package tv.duojiao.service.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.duojiao.job.*;
import tv.duojiao.gather.async.quartz.QuartzManager;
import tv.duojiao.model.utils.ResultBundle;
import tv.duojiao.model.utils.ResultBundleBuilder;
import tv.duojiao.service.quartz.subservice.AccessDuoJiaoService;
import tv.duojiao.service.quartz.subservice.PublishService;

import java.util.HashMap;

/**
 * 机器人服务：自动发布游戏-攻略、山头-话题
 */
@Component
public class CornService {
    private final String QUARTZ_JOB_GROUP_NAME = "robot-job";
    private final String QUARTZ_TRIGGER_GROUP_NAME = "robot-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE = "-minutes";

    private Logger LOG = LogManager.getLogger(CornService.class);
    @Autowired
    private PublishService publishService;
    @Autowired
    private AccessDuoJiaoService accessDuoJiaoService;
    @Autowired
    private QuartzManager quartzManager;
    private ResultBundleBuilder bundleBuilder = new ResultBundleBuilder();

    @Autowired
    public CornService() {
    }

    /**
     * 开始获取多椒用户日志
     *
     * @param minutesInterval 时间/小时
     * @return 获取成功与否
     */
    public ResultBundle<Boolean> createAccessUserLog(int minutesInterval) {
        quartzManager.addJobForever("AccessUserLog", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(minutesInterval) + "-" + "AccessUserLog" + QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE, QUARTZ_TRIGGER_GROUP_NAME
                , AccessUserLogJob.class, new HashMap<>(), "minute", minutesInterval);
        return bundleBuilder.bundle("AccessUserLog", () -> true);
    }

    public ResultBundle<String> removeAccessUserLog() {
        quartzManager.removeJob(JobKey.jobKey("AccessUserLog", QUARTZ_JOB_GROUP_NAME));
        LOG.warn("【STOP】停止用户行为日志采集器");
        return bundleBuilder.bundle("AccessUserLog", () -> "OK");
    }

    /**
     * 开始获取多椒内容
     *
     * @param minutesInterval 时间/小时
     * @return 获取成功与否
     */
    public ResultBundle<Boolean> createAccessDuoJiao(int minutesInterval) {
        quartzManager.addJobForever("AccessDuoJiao", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(minutesInterval) + "-" + "AccessDuoJiao" + QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE, QUARTZ_TRIGGER_GROUP_NAME
                , AccessDuoJiaoJob.class, new HashMap<>(), "minute", minutesInterval);
        return bundleBuilder.bundle("AccessDuoJiao", () -> true);
    }

    public ResultBundle<String> removeAccessDuoJiao() {
        quartzManager.removeJob(JobKey.jobKey("AccessDuoJiao", QUARTZ_JOB_GROUP_NAME));
        LOG.warn("【STOP】多椒资讯采集器（自动获取用户自产攻略话题等内容）");
        return bundleBuilder.bundle("AccessDuoJiao", () -> "OK");
    }


    /**
     * 创建定时发布任务（资讯自动发布、布隆过滤器定时任务）
     *
     * @param interval 间隔时长
     * @return
     */
    public ResultBundle<Boolean> createPublishNews(int interval) {
        if ((quartzManager.findInfo(JobKey.jobKey("ResetFilter", QUARTZ_JOB_GROUP_NAME)) == null)) {
            quartzManager.addJob("ResetFilter", QUARTZ_JOB_GROUP_NAME,
                    String.valueOf(interval * 4) + "-" + "ResetFilter" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                    , ResetBloomJob.class, new HashMap<>(), interval);
        }
        quartzManager.addJobForever("PublishNews", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(interval) + "-" + "PublishNews" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                , PublishNewsJob.class, new HashMap<>(), "minute", interval);

        return bundleBuilder.bundle("AutoPublishNews", () -> true);
    }

    /**
     * 移除攻略自动发送的定时任务
     *
     * @return
     */
    public ResultBundle<String> removePublishNews() {
        quartzManager.removeJob(JobKey.jobKey("PublishNews", QUARTZ_JOB_GROUP_NAME));
        quartzManager.removeJob(JobKey.jobKey("ResetFilter", QUARTZ_JOB_GROUP_NAME));
        return bundleBuilder.bundle("StopPublishStrategyAndTopic", () -> "OK");
    }


    /**
     * 创建定时发布任务（攻略自动发布、布隆过滤器定时任务）
     *
     * @param hoursInterval
     * @return
     */
    public ResultBundle<Boolean> createPublishStrategyAndTopic(int hoursInterval) {
        if ((quartzManager.findInfo(JobKey.jobKey("ResetFilter", QUARTZ_JOB_GROUP_NAME)) == null)) {
            quartzManager.addJob("ResetFilter", QUARTZ_JOB_GROUP_NAME,
                    String.valueOf(hoursInterval * 40) + "-" + "ResetFilter" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                    , ResetBloomJob.class, new HashMap<>(), hoursInterval);
        }
        quartzManager.addJob("PublishStrategyAndTopic", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(hoursInterval) + "-" + "PublishStrategyAndTopic" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                , PublishStrategyAndTopicJob.class, new HashMap<>(), hoursInterval);

        return bundleBuilder.bundle("AutoPublishStrategyAndTopic", () -> true);
    }

    /**
     * 移除攻略自动发送的定时任务
     *
     * @return
     */
    public ResultBundle<String> removePublishStrategyAndTopic() {
        quartzManager.removeJob(JobKey.jobKey("PublishStrategyAndTopic", QUARTZ_JOB_GROUP_NAME));
        quartzManager.removeJob(JobKey.jobKey("ResetFilter", QUARTZ_JOB_GROUP_NAME));
        return bundleBuilder.bundle("StopPublishStrategyAndTopic", () -> "OK");
    }


    public ResultBundle<Boolean> resetBloomFilter() {
        return bundleBuilder.bundle("resetBloomFilter", () -> publishService.resetBloomFilter());
    }

    public String getQUARTZ_JOB_GROUP_NAME() {
        return QUARTZ_JOB_GROUP_NAME;
    }

    public String getQUARTZ_TRIGGER_GROUP_NAME() {
        return QUARTZ_TRIGGER_GROUP_NAME;
    }

    public String getQUARTZ_TRIGGER_NAME_SUFFIX() {
        return QUARTZ_TRIGGER_NAME_SUFFIX;
    }

    public String getQUARTZ_TRIGGER_NAME_SUFFIX_MINUTE() {
        return QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE;
    }

    public Logger getLOG() {
        return LOG;
    }

    public void setLOG(Logger LOG) {
        this.LOG = LOG;
    }

    public PublishService getPublishService() {
        return publishService;
    }

    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    public AccessDuoJiaoService getAccessDuoJiaoService() {
        return accessDuoJiaoService;
    }

    public void setAccessDuoJiaoService(AccessDuoJiaoService accessDuoJiaoService) {
        this.accessDuoJiaoService = accessDuoJiaoService;
    }

    public QuartzManager getQuartzManager() {
        return quartzManager;
    }

    public void setQuartzManager(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    public ResultBundleBuilder getBundleBuilder() {
        return bundleBuilder;
    }

    public void setBundleBuilder(ResultBundleBuilder bundleBuilder) {
        this.bundleBuilder = bundleBuilder;
    }
}
