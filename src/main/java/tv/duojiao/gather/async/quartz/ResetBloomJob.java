package tv.duojiao.gather.async.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.service.robot.RobotService;

import java.util.Calendar;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class ResetBloomJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(ResetBloomJob.class);
    private RobotService robotService;

    public ResetBloomJob() {
        robotService = new RobotService();
    }

    public ResetBloomJob setRobotService(RobotService robotService) {
        this.robotService = robotService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (robotService == null){
            robotService = new RobotService();
        }
        boolean result = robotService.resetBloomFilter().getResult();
        LOG.info("【{}】完成布隆定时器重置", result ? "成功" : "失败");
    }
}
