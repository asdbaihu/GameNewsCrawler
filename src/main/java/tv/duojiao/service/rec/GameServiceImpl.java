package tv.duojiao.service.rec;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.core.AbstractService;
import tv.duojiao.dao.rec.GameMapper;
import tv.duojiao.model.rec.Game;

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
