package tv.duojiao.spider.service.robot;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import tv.duojiao.spider.model.commons.Webpage;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.model.utils.ResultBundleBuilder;
import tv.duojiao.spider.service.commons.webpage.CommonWebpageService;

import java.util.List;


/**
 * Description:
 * User: Yodes
 * Date: 2017/9/11
 */
public class StrategyPublishService {
    private final static Logger LOG = LogManager.getLogger(StrategyPublishService.class);
    @Autowired
    private ResultBundleBuilder bundleBuilder;
    @Autowired
    private CommonWebpageService commonWebpageService;

    public ResultBundle<String> publishStrategy() {
        ResultBundle<Pair<List<Webpage>, Long>> resultBundle = commonWebpageService
                .getWebPageByKeywordAndDomain("", "", "publishTime", "降序", 3, 1);
        return bundleBuilder.bundle("", () -> "OK");
    }
}
