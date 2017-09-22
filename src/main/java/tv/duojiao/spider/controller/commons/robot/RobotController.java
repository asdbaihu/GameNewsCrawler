package tv.duojiao.spider.controller.commons.robot;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.service.robot.RobotService;
import tv.duojiao.spider.utils.BloomFilterUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/16
 */
//@ApiIgnore
//@Controller
@RestController
@RequestMapping("/commons/robot")
public class RobotController {
    private Logger LOG = LogManager.getLogger(RobotController.class);
    @Autowired
    private RobotService robotService;

    /**
     * 定时获取多椒资讯（攻略及话题等）
     * @return
     */
    @RequestMapping(value = "accessDuoJiao", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> accessDuoJiao(){
        return robotService.accessDuoJiao();
    }
    /**
     * 自动发布游戏攻略及山头话题
     *
     * @param hour 定时器间隔时长
     * @return 发布成功与否
     */
    @RequestMapping(value = "publishAll", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> publishAllQuartz(@RequestParam(required = false, defaultValue = "1") int hour) {
        return robotService.createQuartzJob(hour);
    }

    @RequestMapping(value = "stopPublish", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopPublish() {
        return robotService.removeQuartzJob();
    }


    @RequestMapping(value = "resetFilter", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> resetFilter(){
        LOG.info("布隆过滤器准备重置，当前容器内元素总量为{}", BloomFilterUtil.getSize());
        return robotService.resetBloomFilter();
    }
}
