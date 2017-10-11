package spider;

import org.springframework.beans.factory.annotation.Value;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/9
 */
public class TestValue {
    @Value("${spring.resources.add-mappings}")
    public static String springDatasourceUrl;

    public static void main(String[] args) {
        System.out.println(springDatasourceUrl);
    }
}
