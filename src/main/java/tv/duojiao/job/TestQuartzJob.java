package tv.duojiao.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import tv.duojiao.service.quartz.subservice.TestQuartzService;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/12
 */
@Component
public class TestQuartzJob extends QuartzJobBean {
    Logger LOG = LogManager.getLogger(TestQuartzJob.class);

    @Autowired
    private TestQuartzService testQuartzService;

    public TestQuartzJob setTestQuartzService(TestQuartzService testQuartzService) {
        this.testQuartzService = testQuartzService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("+++++定时任务开始+++++");
        testQuartzService.print();
        LOG.info("+++++定时任务结束+++++");
    }
}
