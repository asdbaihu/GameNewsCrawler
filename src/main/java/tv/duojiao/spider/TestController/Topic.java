package tv.duojiao.spider.TestController;

import io.swagger.annotations.ApiModel;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/17
 */
//一般添加个@ApiModel（）就可以，看情况使用里面的属性
@ApiModel(value="Topic", discriminator = "foo", subTypes = {Topic.class})
public class Topic{

}