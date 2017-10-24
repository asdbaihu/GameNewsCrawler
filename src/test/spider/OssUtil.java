package spider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tv.duojiao.utils.OSSUtil;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OSSUtil.class)
public class OssUtil {
    @Autowired
    private OSSUtil ossUtil;

    @Before
    public void init() {
        ossUtil.init();
    }

    @Test
    public void upload() {
        System.out.println(ossUtil.uploadImg2OssFromSite(
                "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png"));
    }

    @After
    public void destory() {
        ossUtil.destory();
    }
}
