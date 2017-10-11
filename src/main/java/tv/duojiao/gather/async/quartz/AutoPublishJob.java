package tv.duojiao.gather.async.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.gather.async.AsyncGather;
import tv.duojiao.service.robot.RobotService;

import java.util.Calendar;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class AutoPublishJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(AutoPublishJob.class);
    private RobotService robotService;

    public AutoPublishJob() {
        this.robotService = new RobotService();
    }

    public AutoPublishJob setRobotService(RobotService robotService) {
        this.robotService = robotService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (robotService == null){
            robotService = new RobotService();
        }
        boolean result = robotService.start().getResult();
        LOG.info("【{}】完成定时攻略及话题发布，时间{}", result ? "成功" : "失败", Calendar.getInstance().getTime());
    }
}
