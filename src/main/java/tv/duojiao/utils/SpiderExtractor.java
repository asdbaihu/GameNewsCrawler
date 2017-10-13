package tv.duojiao.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderExtractor {

    /**
     * 去除字符串中的html标签（主要用于字符串NLP预处理）
     *
     * @param inputString 输入字符串
     * @return 净化后的纯文本字符串
     */
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

    public static List<String> getImageList(String str) {
        Pattern p_image;
        Matcher m_image;
        List<String> imageList = new ArrayList<>();
        String regEx_img = "(http|https)://(\\w+\\.)+(\\w+)[\\w/.\\-]*(jpg|jpeg|gif|png)"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(str);
        int count = 0;
        while (m_image.find() && count < 9) {
            imageList.add(m_image.group());
            count++;
        }
        return imageList;
    }

    /**
     * 根据字符串及内容获取时间（若字符串中不包含时间，则采用当前时间），时间格式采用hashmap
     *
     * @param publishTime      含有时间内容的字符串
     * @param simpleDateFormat 时间格式化器（已弃用）
     * @return 采集到的时间或系统时间
     */
    public static Date getDateBySystem(String publishTime, SimpleDateFormat simpleDateFormat) {
        //如果采集到的时间为空
        if (publishTime == null) {
            return Calendar.getInstance().getTime();
        } else {
            publishTime = publishTime.trim().replaceAll(" +", " ");
        }
        Map<String, String> formatePattern = new HashMap<String, String>() {
            {
                put("yyyy年MM月dd日 HH:mm:ss", "\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}:\\d{1,2}");
                put("yyyy年MM月dd日 HH:mm", "\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}");
                put("yyyy年MM月dd日", "\\d{4}年\\d{1,2}月\\d{1,2}日");
                put("yyyy/MM/dd HH:mm:ss", "\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
                put("yyyy/MM/dd HH:mm", "\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}");
                put("yyyy/MM/dd", "\\d{4}/\\d{1,2}/\\d{1,2}");
                put("yyyy-MM-dd HH:mm:ss", "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
                put("yyyy-MM-dd", "\\d{4}-\\d{1,2}-\\d{1,2}");
                put("yy-MM-dd", "\\d{2}-\\d{1,2}-\\d{1,2}");
                put("yy-MM-dd HH:mm", "\\d{2}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}");
                put("yy-MM-dd HH:mm:ss", "\\d{2}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
                put("MM-dd", "\\d{1,2}-\\d{1,2}");
                put("MM-dd HH:mm", "\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}");
                put("MM-dd HH:mm:ss", "\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
            }
        };

        Date publishDate;
        Date currentDate = Calendar.getInstance().getTime();
        Matcher matcher;

        for (Map.Entry<String, String> entry : formatePattern.entrySet()) {
            matcher = Pattern.compile(entry.getValue()).matcher(publishTime);
            if (matcher.find()) {
                publishTime = matcher.group(0);
                simpleDateFormat = new SimpleDateFormat(entry.getKey());
                try {
                    publishDate = simpleDateFormat.parse(publishTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    publishDate = Calendar.getInstance().getTime();
                }

                Calendar calendar = Calendar.getInstance();
                //如果时间没有包含年份,则默认使用当前年
                if (!StringUtils.containsAny(simpleDateFormat.toPattern(), "yyyy", "yy")) {
                    calendar.setTime(publishDate);
                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
                    publishDate = calendar.getTime();
                } else if (!StringUtils.containsAny(simpleDateFormat.toPattern(), "MM", "M")) {
                    calendar.setTime(publishDate);
                    calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
                    publishDate = calendar.getTime();
                } else if (!StringUtils.containsAny(simpleDateFormat.toPattern(), "HH")) {
                    calendar.setTime(publishDate);
                    calendar.set(Calendar.HOUR, Calendar.getInstance().get(Calendar.HOUR));
                    calendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
                    calendar.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));
                    calendar.set(Calendar.AM_PM, Calendar.getInstance().get(Calendar.AM_PM));
                    publishDate = calendar.getTime();
                }
                //如果识别出的时间在未来，则返回当前时间
                return publishDate.before(currentDate) ? publishDate : currentDate;
            }
        }

        return currentDate;     //无法从指定格式获取时间，直接返回当前时间
    }

    public static Date getLatestDate(int rate) {
        String json = "";
        try {
            Resource resource = new ClassPathResource("dynamicValue.json");
            json = FileUtils.readFileToString(Paths.get(resource.getURI()).toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Calendar current = Calendar.getInstance();
        current.setTime(new Date());
        current.add(Calendar.DATE, -rate * jsonObject.get("maxInvalidDayOfNews").getAsInt());
        return current.getTime();
    }

    public static Date getFrontDate(Date date, String unit, int number) {
        Calendar current = Calendar.getInstance();
        current.setTime((date == null) ? new Date() : date);
        int unitToConvert = Calendar.DATE;
        if ("DAY".equals(unit)) {
            unitToConvert = Calendar.DATE;
        } else if ("MINUTE".equals(unit)) {
            unitToConvert = Calendar.MINUTE;
        } else if ("SECOND".equals(unit)) {
            unitToConvert = Calendar.SECOND;
        }
        current.add(unitToConvert, -number);
        return current.getTime();
    }
}
