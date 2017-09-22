package tv.duojiao.rec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.rec.core.AbstractService;
import tv.duojiao.rec.dao.GameMapper;
import tv.duojiao.rec.model.Game;
import tv.duojiao.rec.service.GameService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Service
@Transactional
public class GameServiceImpl extends AbstractService<Game> implements GameService {
    @Resource
    private GameMapper gameMapper;

}
