package spider;

import com.gs.spider.utils.SpiderExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonSpider {
    public static void main(String[] args) {
        System.out.println(SpiderExtractor.getDateBySystem("<h3><em>[<a href=\"http://moba.uuu9.com/forum.php\" target=\"_blank\">MOBA论坛</a>] [<a href=\"http://dota2.uuu9.com/201708/550512.shtml#newcomment2011\">已跟帖<span id=\"ccount\" style=\"color: red; padding: 0 3px;\"></span>条</a>]</em>2017-8-11 19:45:08&nbsp;作者:游久微视频 来源:本站原创</h3>" +
                "\n", null));
    }
}