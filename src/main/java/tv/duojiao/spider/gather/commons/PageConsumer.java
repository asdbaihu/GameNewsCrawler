package tv.duojiao.spider.gather.commons;

import tv.duojiao.spider.model.async.Task;
import tv.duojiao.spider.model.commons.SpiderInfo;
import us.codecraft.webmagic.Page;

/**
 * PageConsumer
 *
 * @author Gao Shen
 * @version 16/7/8
 */
@FunctionalInterface
public interface PageConsumer {
    void accept(Page page, SpiderInfo info, Task task);
}
