package tv.duojiao.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.service.quartz.CornService;
import tv.duojiao.service.quartz.SubService.PublishService;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class ResetBloomJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(ResetBloomJob.class);
    @Autowired
    private PublishService publishService;

    public void setPublishService(PublishService publishService) {
        this.publishService = publishService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean result = publishService.resetBloomFilter();
        LOG.info("【{}】完成布隆定时器重置", result ? "成功" : "失败");
    }
}
