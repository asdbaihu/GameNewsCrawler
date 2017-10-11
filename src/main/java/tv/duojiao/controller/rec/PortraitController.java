package tv.duojiao.controller.rec;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tv.duojiao.core.Result;
import tv.duojiao.core.ResultGenerator;
import tv.duojiao.model.rec.Portrait;
import tv.duojiao.service.rec.PortraitService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2017/09/22.
 */
@Controller
@RestController
@RequestMapping("/portrait")
public class PortraitController {
    @Resource
    private PortraitService portraitService;

    @GetMapping("/CoreKeyword/{uid}")
    public Result selectCoreKeyWord(@PathVariable Integer uid) {
        List<String> list = portraitService.selectCoreKeywords(uid);
        PageInfo<List<String>> pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/AllKeyword/{uid}")
    public Result selectAllKeyword(@PathVariable Integer uid) {
        List<String> list = portraitService.selectAllKeywords(uid);
        PageInfo<List<String>> pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/selectByKeyword/{keyword}")
    public Result selectByKeyword(@PathVariable String keyword) {
        List<Portrait> list = portraitService.selectByKeyword(keyword);
        PageInfo<List<Portrait>> pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @PostMapping
    public Result add(@RequestBody Portrait portrait) {
        portraitService.save(portrait);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        portraitService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Portrait portrait) {
        portraitService.update(portrait);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Portrait portrait = portraitService.findById(id);
        return ResultGenerator.genSuccessResult(portrait);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Portrait> list = portraitService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
