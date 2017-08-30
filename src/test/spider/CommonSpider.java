package spider;

import org.apache.commons.lang3.StringUtils;
import tv.duojiao.spider.utils.SpiderExtractor;
import us.codecraft.webmagic.Spider;

import java.text.SimpleDateFormat;

public class CommonSpider {
    public static void main(String[] args) {
//        System.out.println(StringUtils.isNotBlank("   d "));
//        System.out.println(SpiderExtractor.convertHtml2Text("<h4>2017-8-27 9:24:41 &nbsp;&nbsp; 文章来源：伐木累 &nbsp;&nbsp; 作者：英雄联盟赛事</h4>"));
        System.out.println(SpiderExtractor.getDateBySystem("2016-07-06 作者:未知 来源:网络", null));
    }
}