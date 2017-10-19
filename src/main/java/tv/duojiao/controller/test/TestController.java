package tv.duojiao.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.configurer.ApiMapping;
import tv.duojiao.service.quartz.SubService.AccessDuoJiaoService;
import tv.duojiao.service.quartz.CoreQuartzService;
import tv.duojiao.utils.RPCUtil;
import tv.duojiao.utils.RestUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/9
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${host.name}")
    private String active;
    @Autowired
    private RestUtil restUtil;
    @Autowired
    private RPCUtil rpcUtil;

    @Autowired
    private AccessDuoJiaoService accessDuoJiaoService;

    @Autowired
    private CoreQuartzService coreQuartzService;


    @PostMapping("/testQuartz")
    public String testQuartz(@RequestParam(defaultValue = "10") int second) {
        return coreQuartzService.testQuartz(second);
    }

    @PostMapping("/testAccessDuoJiao")
    public String testAccessDuoJiao() {
        for (int i = 0; i <= 2; i++) {
            accessDuoJiaoService.insert();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "No";
            }
        }
        return "Yes";
    }

    @GetMapping
    public String testEnv() {
        return active;
    }

    @GetMapping("/util")
    public int testUtil(@RequestParam(defaultValue = "英雄联盟") String gname) {
        return rpcUtil.getGidByGname(gname);
    }
}
