package spider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonSpider {
    public static void main(String[] args) {
        System.out.println(getDateBySystem("2017-8-23 15:12:43    文章来源：游久uuu9    作者：冥\n" +
                "\n", "yyyy-MM-dd hh:mm:ss"));
    }

    public static Date getDateBySystem(String publishTime, String timePattern) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
        Date publishDate = null;

        //
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
            publishDate = Calendar.getInstance().getTime();
        }
        return (publishDate);
    }
}