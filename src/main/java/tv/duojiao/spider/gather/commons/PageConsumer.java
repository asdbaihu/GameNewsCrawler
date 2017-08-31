package tv.duojiao.spider.gather.commons;

import tv.duojiao.spider.model.async.Task;
import tv.duojiao.spider.model.commons.SpiderInfo;
import us.codecraft.webmagic.Page;

/**
 * PageConsumer
 *
 * @author Yodes
 * @version
 */
@FunctionalInterface
public interface PageConsumer {
    void accept(Page page, SpiderInfo info, Task task);
}
