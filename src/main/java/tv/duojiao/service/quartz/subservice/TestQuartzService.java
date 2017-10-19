package tv.duojiao.service.quartz.subservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/12
 */
@Component
public class TestQuartzService {
    private static int count = 0;
    private Logger LOG = LogManager.getLogger(TestQuartzService.class);

    public String print() {
        LOG.info("当前数据为：{}", count);
        return "true";
    }
}
