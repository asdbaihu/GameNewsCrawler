package tv.duojiao.rec.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.duojiao.rec.core.AbstractService;
import tv.duojiao.rec.dao.PortraitMapper;
import tv.duojiao.rec.model.Portrait;
import tv.duojiao.rec.service.PortraitService;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Service
@Transactional
public class PortraitServiceImpl extends AbstractService<Portrait> implements PortraitService {
    @Resource
    private PortraitMapper portraitMapper;

}
