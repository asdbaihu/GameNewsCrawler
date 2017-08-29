package spider;

import com.gs.spider.utils.SpiderExtractor;
import com.overzealous.remark.Remark;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonSpider {
    public static void main(String[] args) {
        System.out.println(SpiderExtractor.getDateBySystem("2017-05-23", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
    }
}