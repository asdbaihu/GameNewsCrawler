package tv.duojiao.controller.rec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.core.Result;
import tv.duojiao.core.ResultGenerator;
import tv.duojiao.model.commons.Webpage;
import tv.duojiao.model.rec.RecommendEnity;
import tv.duojiao.service.rec.RecommendService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 * User: Yodes
 * Date: 2017/9/26
 */
@Controller
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Resource
    private RecommendService recommendService;

    @GetMapping("/")
    public Result selectRecommendList(@RequestParam int uid,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "1") int page){
        List<RecommendEnity> list = recommendService.getRecommendList(uid,size,page);
        return ResultGenerator.genSuccessResult(list);
    }

}
