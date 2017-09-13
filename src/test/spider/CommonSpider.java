package spider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.overzealous.remark.Remark;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tv.duojiao.spider.dao.CommonWebpageDAO;
import tv.duojiao.spider.utils.SpiderExtractor;
import tv.duojiao.spider.utils.StaticValue;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CommonSpider {
    @Autowired
    private static CommonWebpageDAO commonWebpageDAO;
    public static void main(String[] args) {

//        System.out.println(StringUtils.isNotBlank("   d "));
//        System.out.println(SpiderExtractor.convertHtml2Text("<h4>2017年8月27日 9:24:41 &nbsp;&nbsp; 文章来源：伐木累 &nbsp;&nbsp; 作者：英雄联盟赛事</h4>"));
        System.out.println(SpiderExtractor.getDateBySystem("发发表时间：2：2017-09-13 10:01 来源：网络 作者：网络 来源：网络 作者：网络", null));
//        System.out.println(new StaticValue().getMaxInvalidDayOfNews());
//        System.out.println(new Remark().convert(""));
//        System.out.println(SpiderExtractor.getLatestDate());
//        Map count = commonWebpageDAO.countDomainByGatherTime("lol.uuu9.com");
    }

    public static String getContext(String url){
        Page page = new Page();
        page.addTargetRequest(url);
        return page.getHtml().toString();
    }
}