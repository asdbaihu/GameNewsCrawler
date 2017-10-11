package tv.duojiao.dao.rec;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tv.duojiao.model.rec.Game;
import tv.duojiao.core.Mapper;

import java.util.List;

@Component
public interface GameMapper extends Mapper<Game> {
}