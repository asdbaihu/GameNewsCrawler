package tv.duojiao.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tv.duojiao.utils.RestUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/9
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${host.name.pro}")
    private String active;
    @Autowired
    private RestUtil restUtil;

    @GetMapping
    public String testEnv() {
        return active;
    }

    @GetMapping("/util")
    public String testUtil(){
        return restUtil.toString();
    }
}
