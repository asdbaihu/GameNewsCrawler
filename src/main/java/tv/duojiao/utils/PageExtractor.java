package tv.duojiao.utils;

import com.aliyun.oss.OSS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yodes
 */
public class PageExtractor {
    @Autowired
    private OSSUtil ossUtil;
    /**
     * 将字符串中的资源（图片、视频）提取上传至oss，并返回oss地址
     */
    public static String replaceResourceByOSS(String url) {
//        Elements imageList = getImageElements(htmlText);
//        for (Element element : imageList) {
//            while ("".equals(convertHtml2Text(element.parent().toString()))) {
//                element = element.parent();
//            }
//            Elements tempImage = getImageElements(element.toString());
//            htmlText = htmlText.replace(element.toString(), removeImgExtraTags(tempImage).html());
//        }
//        System.out.println(htmlText);


        return url;
    }


    /**
     * 根据条件过滤标签及脚本（保留文字、图片及视频）
     *
     * @param htmlText 网页文本
     * @return 过滤后的文本
     */
    public static String filterTagsInConditions(String htmlText) {
        //若出现空字符或NULL则不进行解析
        if (StringUtils.isBlank(htmlText)) {
            return "";
        }

        String htmlStr = StringUtils.replaceEach(htmlText, new String[]{"&amp;", "&quot;", "&lt;", "&gt;"},
                new String[]{"&", "\"", "<", ">"});
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            // }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            // }
            String regEx_html = "(?!<img.+?>)<.+?>"; // 定义HTML标签的正则表达式
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


            textStr = htmlStr.replaceAll("\n+", "\n")
                    .replaceAll("    +", "    ");
        } catch (Exception e) {
            System.err.println("Html2Text: " + htmlStr);
        }
        Elements elements = getImageElements(textStr);
        for (Element element : elements) {
            textStr = textStr.replace(element.toString(), removeImgExtraTags(new Elements(element)).toString());
        }
        System.out.println(textStr);
        return textStr;// 返回文本字符串
    }

    /**
     * 获取图片list
     *
     * @param str 网页html
     * @return 图片地址
     */
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

    /**
     * 获取最近时间（由配置文件确定）
     *
     * @param rate
     * @return
     */
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

    /**
     * 获取前number（Unit）单位对应的时间
     *
     * @param date   时间
     * @param unit   单位（DAY|MINUTE|SECOND）
     * @param number 数值
     * @return
     */
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

    /**
     * 去除html中的缩进
     *
     * @param str
     * @return
     */
    public static String removeIndent(String str) {
        StringBuffer sb = new StringBuffer("");
        for (String tempStr : str.split("\n")) {
            sb.append(tempStr.trim());
        }
        return sb.toString();
    }

    /**
     * 获取html字符串中的图片元素
     *
     * @param htmlText html代码
     * @return 图片元素集
     */
    public static Elements getImageElements(String htmlText) {
        Document doc = Jsoup.parse(htmlText);
        Elements imgList = doc.select("img[src$=.JPG]");
        imgList.addAll(doc.select("img[url$=.JPG]"));
        return imgList;
    }

    /**
     * 移除图片标签中多余的内容
     *
     * @param imgHtml 含有img标签的元素集
     * @return 更新后的html代码
     */
    public static Elements removeImgExtraTags(Elements imgHtml) {
        Pattern p_image;
        Matcher m_image;

        StringBuffer stringBuffer = new StringBuffer();
        String regEx_img = "(http|https)://(\\w+\\.)+(\\w+)[\\w/.\\-]*(jpg|jpeg|gif|png)"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);

        for (Element element : imgHtml) {
            m_image = p_image.matcher(element.toString());
            while (m_image.find()) {
                stringBuffer.append("<img src=\"" + m_image.group() + "\">");
            }
        }

        return getImageElements(stringBuffer.toString());
    }


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

            textStr = htmlStr.replaceAll("\n+", "\n")
                    .replaceAll("    +", "    ");


        } catch (Exception e) {
            System.err.println("Html2Text: " + inputString);
        }

        return textStr;// 返回文本字符串
    }
}
