package tv.duojiao.gather.async.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.gather.async.AsyncGather;
import tv.duojiao.service.robot.RobotService;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class AccessDuoJiaoJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(AccessDuoJiaoJob.class);
    private RobotService robotService = new RobotService();

    public AccessDuoJiaoJob() {
        this.robotService = new RobotService();
    }

    public AccessDuoJiaoJob setRobotService(RobotService robotService) {
        this.robotService = robotService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (robotService == null) {
            LOG.error("roborService为空！");
            robotService = new RobotService();
        }
        boolean result = robotService.startAccessDuoJiao().getResult();
        LOG.info("【{}】获取多椒资讯", result ? "成功" : "失败");
    }
}
