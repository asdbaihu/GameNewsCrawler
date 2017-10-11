package tv.duojiao.gather.commons;

import tv.duojiao.model.async.Task;
import tv.duojiao.model.commons.SpiderInfo;
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
