package tv.duojiao.core.SubMapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.provider.SpecialProvider;
import tv.duojiao.model.rec.Portrait;

import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/10/13
 */
public interface InsertPidMapper {
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    @InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
    int insert(Portrait record);
}