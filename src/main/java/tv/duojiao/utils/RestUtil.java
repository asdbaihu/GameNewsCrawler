package tv.duojiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.BloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/15
 */
@Component
public class RestUtil {
    public static String oauth_token;
    public static String oauth_token_secret;

    @Value("${admin.userName}")
    public String adminUserName;
    @Value("${admin.password}")
    public String adminPassword;
    @Value("${api.version}")
    public String apiVersion;

    @Value("${host.name}")
    public String HOST_NAME;
    @Value("${duojiao.host}")
    public String DUOJIAO_HOST;

    private static RestTemplate restTemplate;

    /**
     * 获取RestTemplate单例
     * @return
     */
    public static RestTemplate getRestTemplate(){
        if(restTemplate == null){
            return new RestTemplate();
        }else{
            return restTemplate;
        }
    }

    /**
     * CURL POST API
     * @param url       链接
     * @param paramMap  参数map
     * @param param     需要返回的参数
     * @return          含有参数及其对应值的map
     */
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

    @Override
    public String toString() {
        return "RestUtil{" +
                "adminUserName='" + adminUserName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", HOST_NAME='" + HOST_NAME + '\'' +
                ", DUOJIAO_HOST='" + DUOJIAO_HOST + '\'' +
                '}';
    }
}
