package spider;

import tv.duojiao.spider.utils.SpiderExtractor;

import java.text.SimpleDateFormat;

public class CommonSpider {
    public static void main(String[] args) {
        System.out.println(SpiderExtractor.getDateBySystem("2017-05-23", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
    }
}