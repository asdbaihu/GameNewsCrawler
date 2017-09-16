package tv.duojiao.spider.controller.commons.robot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tv.duojiao.spider.model.utils.ResultBundle;
import tv.duojiao.spider.service.robot.RobotService;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/16
 */
@RequestMapping("/commons/robot")
@Controller
public class RobotController {
    private Logger LOG = LogManager.getLogger(RobotService.class);
    @Autowired
    private RobotService robotService;

    /**
     * 自动发布游戏攻略及山头话题
     *
     * @param hoursInterval 定时器间隔时长
     * @return 发布成功与否
     */
    @RequestMapping(value = "publishAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResultBundle<Boolean> publishAllQuartz(@RequestParam(required = false, defaultValue = "1") int hoursInterval) {
        return robotService.createQuartzJob(hoursInterval);
    }
}
