package spider;

import org.junit.Test;
import tv.duojiao.utils.spider.PageExtractor;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
public class UtilTest {
    @Test
    public void testStringUtil() {
        List<String> list = PageExtractor.getImageList("http://img1.tuwandata.com/v2/thumb/all/NTc5MiwxMDAwLDEwMCw0LDMsMSwtMSwxLCwsOTA=/u/www.tuwan.com/uploads/allimg/1710/23/691_171023204642_1.jpg", Integer.MAX_VALUE);
        System.out.println(list.toString());
    }
}
