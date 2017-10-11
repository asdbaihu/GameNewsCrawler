package tv.duojiao.controller.home;

import io.swagger.annotations.Api;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tv.duojiao.controller.BaseController;
import tv.duojiao.utils.AppInfo;

import javax.validation.Valid;

/**
 * Update by Yodes.
 */
@Api
@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
    @Value("${feature.folder.address}")
    String testStr;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("panel/welcome/welcome");
        modelAndView.addObject("appName", AppInfo.APP_NAME)
                .addObject("appVersion", AppInfo.APP_VERSION)
                .addObject("onlineDocumentation", AppInfo.ONLINE_DOCUMENTATION);
        return modelAndView;
    }

    @GetMapping(value = {"/hhh"})
    public String index() {
        return testStr + "success";
    }
}

