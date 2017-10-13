package tv.duojiao.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.service.quartz.SubService.AccessUserLogService;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class AccessUserLogJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(AccessUserLogJob.class);
    @Autowired
    private AccessUserLogService accessUserLogService;

    public void setAccessUserLogService(AccessUserLogService accessUserLogService) {
        this.accessUserLogService = accessUserLogService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean result = accessUserLogService.updateLog();
        LOG.info("【{}】获取多椒日志", result ? "成功" : "失败");
    }
}
