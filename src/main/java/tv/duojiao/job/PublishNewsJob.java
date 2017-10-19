package tv.duojiao.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.service.quartz.SubService.PublishService;

import java.util.Calendar;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/19
 */
@DisallowConcurrentExecution
public class PublishNewsJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(PublishStrategyJob.class);
    @Autowired
    private PublishService publishService;

    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean result = publishService.publishStrategyAndTopic();
        LOG.info("【{}】完成定时攻略及话题发布，时间{}", result ? "成功" : "失败", Calendar.getInstance().getTime());
    }
}
