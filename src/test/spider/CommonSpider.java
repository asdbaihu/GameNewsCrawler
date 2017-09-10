package spider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.overzealous.remark.Remark;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import tv.duojiao.spider.utils.SpiderExtractor;
import tv.duojiao.spider.utils.StaticValue;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonSpider {
    public static void main(String[] args) {

//        System.out.println(StringUtils.isNotBlank("   d "));
//        System.out.println(SpiderExtractor.convertHtml2Text("<h4>2017-8-27 9:24:41 &nbsp;&nbsp; 文章来源：伐木累 &nbsp;&nbsp; 作者：英雄联盟赛事</h4>"));
        System.out.println(SpiderExtractor.getDateBySystem("发布时间：2017-09-07   来源：原创    作者：燕云窜天猴    ", null));
//        System.out.println(new StaticValue().getMaxInvalidDayOfNews());
//        System.out.println(new Remark().convert(""));
//        System.out.println(SpiderExtractor.getLatestDate());
    }

    public static String getContext(String url){
        Page page = new Page();
        page.addTargetRequest(url);
        return page.getHtml().toString();
    }
}