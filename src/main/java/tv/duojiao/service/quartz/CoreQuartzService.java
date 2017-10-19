package tv.duojiao.service.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.duojiao.gather.async.quartz.QuartzManager;
import tv.duojiao.job.TestQuartzJob;
import tv.duojiao.model.utils.ResultBundleBuilder;
import tv.duojiao.service.quartz.subservice.TestQuartzService;

import java.util.HashMap;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/12
 */
@Component
public class CoreQuartzService {
    private final String QUARTZ_JOB_GROUP_NAME = "robot-job";
    private final String QUARTZ_TRIGGER_GROUP_NAME = "robot-trigger";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX = "-hours";
    private final String QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE = "-minutes";

    private Logger LOG = LogManager.getLogger(CornService.class);

    @Autowired
    private QuartzManager quartzManager;
    @Autowired
    public TestQuartzService testQuartzService;
    private ResultBundleBuilder bundleBuilder = new ResultBundleBuilder();

    public String testQuartz(int secondInterval) {
        LOG.info("---【开始】调用testQuartz定时任务---");
        quartzManager.addJobForever("AccessDuoJiao", QUARTZ_JOB_GROUP_NAME,
                String.valueOf(secondInterval) + "-" + "AccessDuoJiao" + QUARTZ_TRIGGER_NAME_SUFFIX_MINUTE, QUARTZ_TRIGGER_GROUP_NAME
                , TestQuartzJob.class, new HashMap<>(), "second", secondInterval);
        LOG.info("---【完成】调用testQuartz定时任务---");
        return testQuartzService.print();
    }
}
