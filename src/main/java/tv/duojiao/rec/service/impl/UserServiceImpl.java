package tv.duojiao.rec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.rec.core.AbstractService;
import tv.duojiao.rec.dao.UserMapper;
import tv.duojiao.rec.model.User;
import tv.duojiao.rec.service.UserService;

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
