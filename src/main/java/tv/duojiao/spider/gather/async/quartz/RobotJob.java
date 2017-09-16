package tv.duojiao.spider.gather.async.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.spider.model.commons.SpiderInfo;
import tv.duojiao.spider.service.commons.spider.CommonsSpiderService;
import tv.duojiao.spider.service.robot.RobotService;

import java.util.Calendar;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class RobotJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(RobotJob.class);
    @Autowired
    private RobotService robotService;

    public RobotJob setRobotService(RobotService robotService) {
        this.robotService = robotService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("开始定时攻略及话题发布，时间{}", Calendar.getInstance().getTime());
        if(robotService == null){
            setRobotService(new RobotService());
        }
        boolean result = robotService.start().getResult();
        LOG.info("[{}]结束定时攻略及话题发布，时间{}", result ? "成功" : "失败", Calendar.getInstance().getTime());
    }
}
