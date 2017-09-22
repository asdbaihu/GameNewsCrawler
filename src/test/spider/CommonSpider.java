package spider;

import com.google.common.hash.Hashing;
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
import java.nio.charset.Charset;
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
//        System.out.println(SpiderExtractor.getDateBySystem("发发表时间：2：2017年09月17日 11:22 来源：网络 作者：网络 来源：网络 作者：网络", null));
//        System.out.println(new StaticValue().getMaxInvalidDayOfNews());
//        System.out.println(new Remark().convert(""));
//        System.out.println(SpiderExtractor.getLatestDate());
//        Map count = commonWebpageDAO.countDomainByGatherTime("lol.uuu9.com");
//        System.out.println(("asda                   s&nbsp;         asdsa />  xianzai")
//                .replaceAll("( ){4,}","    "));
//                .replaceAll("(&nbsp;\\s*)+", " "));
//                .replaceAll("(<br>)+","<br>")
//                .replaceAll("(<br />)+","<br />"));

        System.out.println(test());

    }

    public static String getContext(String url){
        Page page = new Page();
        page.addTargetRequest(url);
        return page.getHtml().toString();
    }

    public static String test(){
        for(int i = 0; i <= 10; i++)
            System.out.println(Hashing.md5().hashString(i + "", Charset.forName("utf-8")).toString());
        return "";
    }
}