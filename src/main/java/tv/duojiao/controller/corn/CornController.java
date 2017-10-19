package tv.duojiao.controller.corn;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.model.utils.ResultBundle;
import tv.duojiao.service.quartz.CornService;
import tv.duojiao.utils.BloomFilterUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/16
 */
//@ApiIgnore
//@Controller
@RestController
@RequestMapping("/commons/corn")
public class CornController {
    private Logger LOG = LogManager.getLogger(CornController.class);
    @Autowired
    private CornService cornService;

    /**
     * 定时获取多椒资讯
     * 默认10分钟扫描一次
     *
     * @return
     */
    @RequestMapping(value = "publishNews", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> publishNews(@RequestParam(name = "分钟", required = false, defaultValue = "10") int interVal) {
        return cornService.createPublishNews(interVal);
    }

    /**
     * 停止定时获取多椒资讯
     *
     * @return
     */
    @RequestMapping(value = "stopPublishNews", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopPublishNews() {
        return cornService.removePublishNews();
    }

    /**
     * 定时获取多椒资讯（攻略及话题等）
     * 默认10分钟扫描一次
     *
     * @return
     */
    @RequestMapping(value = "accessDuoJiao", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> accessDuoJiao(@RequestParam(required = false, defaultValue = "10") int minutes) {
        return cornService.createAccessDuoJiao(minutes);
    }

    /**
     * 停止定时获取多椒资讯（攻略及话题等）
     *
     * @return
     */
    @RequestMapping(value = "stopAccessDuoJiao", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopAccessDuoJiao() {
        return cornService.removeAccessDuoJiao();
    }

    /**
     * 定时获取多椒用户行为日志
     *
     * @param minutes
     * @return
     */
    @RequestMapping(value = "accessUserLog", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> accessUserLog(@RequestParam(required = false, defaultValue = "10") int minutes) {
        return cornService.createAccessUserLog(minutes);
    }

    @RequestMapping(value = "stopAccessUserLog", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopaccessUserLog() {
        return cornService.removeAccessUserLog();
    }

    /**
     * 自动发布游戏攻略及山头话题
     *
     * @param hour 定时器间隔时长
     * @return 发布成功与否
     */
    @RequestMapping(value = "publishStrategyAndTopic", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> publishStrategyAndTopic(@RequestParam(required = false, defaultValue = "1") int hour) {
        return cornService.createPublishStrategyAndTopic(hour);
    }

    @RequestMapping(value = "stopPublishStrategyAndTopic", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopPublishStrategyAndTopic() {
        return cornService.removePublishStrategyAndTopic();
    }


    @RequestMapping(value = "resetFilter", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> resetFilter() {
        LOG.info("布隆过滤器准备重置，当前容器内元素总量为{}", BloomFilterUtil.getSize());
        return cornService.resetBloomFilter();
    }
}
