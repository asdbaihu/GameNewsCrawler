package com.gs.spider.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderExtractor {

    public static Date getDateBySystem(String publishTime, SimpleDateFormat simpleDateFormat) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
        Date publishDate = null;

        //如果采集到的时间为空
        if(publishTime == null)
            return Calendar.getInstance().getTime();

        //如果格式化配置为空
        if(simpleDateFormat == null){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Matcher matcher = pattern.matcher(publishTime);

        if (matcher.find()) {
            publishTime = matcher.group(0);
            try {
                publishDate = simpleDateFormat.parse(publishTime);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                publishDate = Calendar.getInstance().getTime();
            }
        } else {
            //如果没有查找到时间，直接采用当前时间进行返回
            System.out.println("没有查找到时间");
            publishDate = Calendar.getInstance().getTime();
        }
        return (publishDate);
    }
}
