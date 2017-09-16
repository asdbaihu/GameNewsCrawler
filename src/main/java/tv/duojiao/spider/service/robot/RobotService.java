package tv.duojiao.spider.service.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.duojiao.spider.gather.async.quartz.QuartzManager;
import tv.duojiao.spider.gather.async.quartz.RobotJob;
import tv.duojiao.spider.gather.async.quartz.WebpageSpiderJob;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.model.utils.ResultBundleBuilder;

import java.util.HashMap;

/**
 * 机器人服务：自动发布游戏-攻略、山头-话题
 */
@Component
public class RobotService {
    private final String QUARTZ_JOB_GROUP_NAME = "robot-job";
    private final String QUARTZ_TRIGGER_GROUP_NAME = "robot-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";

    private Logger LOG = LogManager.getLogger(RobotService.class);

    @Autowired
    private PublishService publishService = new PublishService();
    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private ResultBundleBuilder bundleBuilder = new ResultBundleBuilder();

    public ResultBundle<Boolean> start() {
        return bundleBuilder.bundle("AutoPublish", () -> publishService.publicshAll());
    }

    public ResultBundle<Boolean> createQuartzJob(int hoursInterval) {
        quartzManager.addJob("AutoPublish", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(hoursInterval) + "-" + "AutoPublish" + QUARTZ_TRIGGER_NAME_SUFFIX, QUARTZ_TRIGGER_GROUP_NAME
                , RobotJob.class, new HashMap<>(), hoursInterval);
        return bundleBuilder.bundle("AutoPublish", () -> publishService.publicshAll());
    }
}
