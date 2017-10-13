package tv.duojiao.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tv.duojiao.service.quartz.CornService;
import tv.duojiao.service.quartz.SubService.AccessDuoJiaoService;

/**
 * Created by Yodes .
 */
@DisallowConcurrentExecution
public class AccessDuoJiaoJob extends QuartzJobBean {
    private Logger LOG = LogManager.getLogger(AccessDuoJiaoJob.class);
    @Autowired
    private AccessDuoJiaoService accessDuoJiaoService;

    public AccessDuoJiaoJob setAccessDuoJiaoService(AccessDuoJiaoService accessDuoJiaoService) {
        this.accessDuoJiaoService = accessDuoJiaoService;
        return this;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean result = accessDuoJiaoService.insert();
        LOG.info("【{}】获取多椒资讯", result ? "成功" : "失败");
    }
}
