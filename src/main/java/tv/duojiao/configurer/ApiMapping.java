package tv.duojiao.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/19
 */
@Component
@ConfigurationProperties(prefix= "API")
public class ApiMapping {
    private String prefix;
    private Map<String,String> User;
    private Map<String,String> Weibo;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
