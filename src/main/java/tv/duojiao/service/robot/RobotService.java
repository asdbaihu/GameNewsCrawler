package tv.duojiao.service.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tv.duojiao.gather.async.AsyncGather;
import tv.duojiao.gather.async.quartz.AccessDuoJiaoJob;
import tv.duojiao.gather.async.quartz.QuartzManager;
import tv.duojiao.gather.async.quartz.AutoPublishJob;
import tv.duojiao.gather.async.quartz.ResetBloomJob;
import tv.duojiao.model.utils.ResultBundle;
import tv.duojiao.model.utils.ResultBundleBuilder;
import tv.duojiao.service.AsyncGatherService;

import java.util.HashMap;

/**
 * 机器人服务：自动发布游戏-攻略、山头-话题
 */
@Component
public class RobotService extends AsyncGatherService {
    private final String QUARTZ_JOB_GROUP_NAME = "robot-job";
    private final String QUARTZ_TRIGGER_GROUP_NAME = "robot-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE = "-minutes";

    private Logger LOG = LogManager.getLogger(RobotService.class);
    @Autowired
    private PublishService publishService = new PublishService();
    @Autowired
    private AccessDuoJiaoService accessDuoJiaoService = new AccessDuoJiaoService();
    @Autowired
    private QuartzManager quartzManager = new QuartzManager();
    @Autowired
    private ResultBundleBuilder bundleBuilder = new ResultBundleBuilder();

    @Autowired
    public RobotService() {
        publishService = new PublishService();
        accessDuoJiaoService = new AccessDuoJiaoService();
        quartzManager = new QuartzManager();
        bundleBuilder = new ResultBundleBuilder();
    }

    /**
     * 定时获取多椒内容
     *
     * @return 获取成功与否
     */
    public ResultBundle<Boolean> startAccessDuoJiao() {
        if (accessDuoJiaoService == null) {
            LOG.error("accessDuojiaoService为空！");
            accessDuoJiaoService = new AccessDuoJiaoService();
        }
        return bundleBuilder.bundle("AccessDuoJiao", () -> accessDuoJiaoService.insert());
    }

    /**
     * 开始获取多椒内容
     *
     * @param minutesInterval 时间/小时
     * @return 获取成功与否
     */
    public ResultBundle<Boolean> createAccessDuoJiao(int minutesInterval) {
        quartzManager.addJobByMinute("AccessDuoJiao", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(minutesInterval) + "-" + "AccessDuoJiao" + QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE, QUARTZ_TRIGGER_GROUP_NAME
                , AccessDuoJiaoJob.class, new HashMap<>(), minutesInterval);

        return bundleBuilder.bundle("AccessDuoJiao", () -> accessDuoJiaoService.insert());
    }

    public ResultBundle<String> removeAccessDuoJiao() {
        quartzManager.removeJob(JobKey.jobKey("AccessDuoJiao", QUARTZ_JOB_GROUP_NAME));
        return bundleBuilder.bundle("AccessDuoJiao", () -> "OK");
    }

    /**
     * 创建自动发布攻略话题的定时任务
     *
     * @return
     */
    public ResultBundle<Boolean> start() {
        if (publishService == null) {
            publishService = new PublishService();
        }
        return bundleBuilder.bundle("AutoPublish", () -> publishService.publicshAll());
    }

    /**
     * 创建定时发布任务（攻略自动发布、布隆过滤器定时任务）
     *
     * @param hoursInterval
     * @return
     */
    public ResultBundle<Boolean> createQuartzJob(int hoursInterval) {
        quartzManager.addJob("ResetFilter", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(hoursInterval * 40) + "-" + "ResetFilter" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                , ResetBloomJob.class, new HashMap<>(), hoursInterval);
        quartzManager.addJob("AutoPublish", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(hoursInterval) + "-" + "AutoPublish" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                , AutoPublishJob.class, new HashMap<>(), hoursInterval);

        return bundleBuilder.bundle("AutoPublish", () -> publishService.publicshAll());
    }

    /**
     * 移除攻略自动发送的定时任务
     *
     * @return
     */
    public ResultBundle<String> removeQuartzJob() {
        quartzManager.removeJob(JobKey.jobKey("AutoPublish", QUARTZ_JOB_GROUP_NAME));
        quartzManager.removeJob(JobKey.jobKey("ResetFilter", QUARTZ_JOB_GROUP_NAME));
        return bundleBuilder.bundle("StopPublish", () -> "OK");
    }

    /**
     * 重置布隆过滤器
     *
     * @return
     */
    public ResultBundle<Boolean> resetBloomFilter() {
        return bundleBuilder.bundle("ResetFilter", () -> publishService.resetBloomFilter());
    }
}
