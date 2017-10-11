package tv.duojiao.service.rec;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.dao.rec.UserMapper;
import tv.duojiao.core.AbstractService;
import tv.duojiao.model.rec.User;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
