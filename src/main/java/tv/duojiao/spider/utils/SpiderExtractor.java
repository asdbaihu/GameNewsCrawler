package tv.duojiao.spider.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderExtractor {
    public static String convertHtml2Text(String inputString) {
        //若出现空字符或NULL则不进行解析
        if (StringUtils.isBlank(inputString)) {
            return "";
        }

        String htmlStr = StringUtils.replaceEach(inputString, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"},
                new String[]{"&", "\"", "<", ">"});
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        java.util.regex.Pattern p_html1;
        java.util.regex.Matcher m_html1;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script,
                    Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern
                    .compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern
                    .compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll("").trim(); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + inputString);
        }

        return textStr;// 返回文本字符串
    }

    public static Date getDateBySystem(String publishTime, SimpleDateFormat simpleDateFormat) {
        Pattern pattern1 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
        Pattern pattern2 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
        Date publishDate = null;

        //如果采集到的时间为空
        if (publishTime == null)
            return Calendar.getInstance().getTime();

        Matcher matcher1 = pattern1.matcher(publishTime);
        Matcher matcher2 = pattern2.matcher(publishTime);

        if (simpleDateFormat == null) {
            if (matcher1.find()) {
                publishTime = matcher1.group(0);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (matcher2.find()) {
                publishTime = matcher2.group(0);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                //如果没有查找到时间，直接采用当前时间进行返回
                System.out.println("没有查找到时间");
                publishDate = Calendar.getInstance().getTime();
                return publishDate;
            }
        }

        //如果时间没有包含年份,则默认使用当前年
        if (!simpleDateFormat.toPattern().contains("yyyy")) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(publishDate);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            publishDate = calendar.getTime();
        }


        //根据时间进行转换
        try {
            publishDate = simpleDateFormat.parse(publishTime);
        } catch (ParseException e) {
            publishDate = Calendar.getInstance().getTime();
        } catch (IllegalStateException e) {
            publishDate = Calendar.getInstance().getTime();
        } finally {
//            System.err.println("Get the finally pblishTime:" + publishDate);
        }
        return (publishDate);
    }

    public static Date getLatestDate() {
        String json = "";
        try {
            json = FileUtils.readFileToString(new File(StaticValue.class.getClassLoader()
                    .getResource("dynamicvalue.json").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        current.add(Calendar.DATE, -jsonObject.get("maxInvalidDayOfNews").getAsInt());
        return current.getTime();
    }
}
