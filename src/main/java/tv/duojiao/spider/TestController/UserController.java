package tv.duojiao.spider.TestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/17
 */
@Controller
//类上使用@Api
@Api(value="用户controller",description="用户相关操作")
public class UserController {

    @RequestMapping(value="index",method= RequestMethod.POST)
    //方法上使用@ApiOperation
    @ApiOperation(value="首页",notes="跳转到首页")
    //参数使用@ApiParam
    public Object getIndex(@ApiParam(name="topic实体",value="json格式",required=true) @RequestBody Topic topic){
        //业务内容，被我删除了，请忽略，主要看上面的注解
        Object obj = new Object();
        return obj;
    }
}