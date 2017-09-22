package db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tv.duojiao.spider.dao.rec.UserDAO;
import tv.duojiao.spider.model.rec.UserEnity;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/22
 */
public class UserDAOTest extends BaseTest{
    @Autowired
    private UserDAO userDAO;

    @Test
    public void testSelectById() throws Exception{
        int id = 1;
        UserEnity userEnity = userDAO.selectById(id);
        System.out.println(userEnity);
    }
}
