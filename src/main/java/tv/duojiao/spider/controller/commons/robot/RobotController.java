package tv.duojiao.spider.controller.commons.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.spider.controller.AsyncGatherBaseController;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.service.AsyncGatherService;
import tv.duojiao.spider.service.commons.spider.CommonsSpiderService;
import tv.duojiao.spider.service.robot.RobotService;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/16
 */
@RestController
@RequestMapping("/commons/robot")
@Controller
public class RobotController {
    private Logger LOG = LogManager.getLogger(RobotController.class);
    @Autowired
    private RobotService robotService;
    /**
     * 自动发布游戏攻略及山头话题
     *
     * @param hour 定时器间隔时长
     * @return 发布成功与否
     */
    @RequestMapping(value = "publishAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> publishAllQuartz(@RequestParam(required = false, defaultValue = "1") int hour) {
        return robotService.createQuartzJob(hour);
    }

    @RequestMapping(value = "stopPublish", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<String> stopPublish() {
        return robotService.removeQuartzJob();
    }
}