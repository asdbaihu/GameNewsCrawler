package spider;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/14
 */
public class TestREST {
    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        String url = "http://localhost:8080/GameNews/commons/webpage/searchByGame?query=&gameName=英雄&category=攻略";
        ResponseEntity<JSONObject> responseEntity = template.getForEntity(url, JSONObject.class);
        JSONObject data = responseEntity.getBody();
        JsonEntiy js = data.toJavaObject(JsonEntiy.class);
        System.out.println(js.count);
        ArrayList<ResultList> resultList = js.resultList;
        resultList.forEach(items -> System.out.println(items.id));
    }
}
class JsonEntiy {
    public ArrayList<ResultList> resultList;
    public String keyword;
    public Integer count;
    public Integer time;
    public boolean success;
}

class ResultList {
    public String id;
    public Integer processTime;
}