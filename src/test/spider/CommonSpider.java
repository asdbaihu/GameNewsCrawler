package spider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonSpider {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}");
        String publishTime = "2017-8-03 10:12:17";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        System.out.println(publishDate);
    }
}