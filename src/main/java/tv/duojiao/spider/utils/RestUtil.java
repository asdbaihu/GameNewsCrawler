package tv.duojiao.spider.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */
public class RestUtil {
    private static RestTemplate restTemplate;
    public static RestTemplate getRestTemplate(){
        if(restTemplate == null){
            return new RestTemplate();
        }else{
            return restTemplate;
        }
    }
    public static Map<String, Object> postMessage(String url, MultiValueMap paramMap, String... param) {
        HttpEntity<MultiValueMap<String, String>> request;
        restTemplate = getRestTemplate();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        request = new HttpEntity<MultiValueMap<String, String>>(paramMap, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                url,
                request,
                String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody(), JSONObject.class);
//        System.out.println(jsonObject.toJSONString());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (String p : param) {
            resultMap.put(p, jsonObject.get(p));
        }
        return resultMap;
    }
}
