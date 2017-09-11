package tv.duojiao.spider.service.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import tv.duojiao.spider.gather.async.quartz.QuartzManager;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.model.utils.ResultBundleBuilder;

/**
 * 机器人服务：自动发布游戏-攻略、山头-话题
 */
public class RobotService {
    private final String QUARTZ_JOB_GROUP_NAME = "robot-job";
    private final String QUARTZ_TRIGGER_GROUP_NAME = "robot-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";
    private Logger LOG = LogManager.getLogger(RobotService.class);

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    private ResultBundleBuilder bundleBuilder;

    public ResultBundle<String> createQuartzJob() {
        return bundleBuilder.bundle("", () -> "OK");
    }
}
