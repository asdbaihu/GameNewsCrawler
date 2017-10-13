package API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tv.duojiao.utils.RestUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/11
 */
@Component
public class testAutoApi {

    private static int count = 0;
    @Autowired
    private static RestUtil restUtil;

    public static JSONArray testAPI() {
        String data = "[{\n" +
                "      \"id\": \"" + count + "\",\n" +
                "      \"title\": \"" + count + "\",\n" +
                "      \"content\": \"<p>鸡腿的味道你知道吗？<br></p><img src=\\\"http://admin.duojiao.tv/data/upload/2017/1011/15/59ddc1bff1658038f29a.jpg\\\"><br><p> <br></p><p> <br></p>\",\n" +
                "      \"category\": \"话题\",\n" +
                "      \"gameName\": \"\",\n" +
                "      \"publishTime\": \"2017-10-" + count++ + " 15:01:27\"\n" +
                "    },\n" +
                "      {\"id\": \"" + count + "\",\n" +
                "      \"title\": \"" + count + "\",\n" +
                "      \"content\": \"<p>鸡腿的味道你知道吗？<br></p><img src=\\\"http://admin.duojiao.tv/data/upload/2017/1011/15/59ddc1bff1658038f29a.jpg\\\"><br><p> <br></p><p> <br></p>\",\n" +
                "      \"category\": \"话题\",\n" +
                "      \"gameName\": \"\",\n" +
                "      \"publishTime\": \"2017-10-" + count++ + " 15:01:27\"\n" +
                "    }]";

        JSONArray jsonArray = JSONObject.parseObject(data, JSONArray.class);
        return jsonArray;
    }
}
