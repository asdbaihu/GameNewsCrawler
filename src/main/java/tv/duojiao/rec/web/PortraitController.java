package tv.duojiao.rec.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tv.duojiao.rec.core.Result;
import tv.duojiao.rec.core.ResultGenerator;
import tv.duojiao.rec.model.Portrait;
import tv.duojiao.rec.service.PortraitService;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/09/22.
*/
@RestController
@RequestMapping("/portrait")
public class PortraitController {
    @Resource
    private PortraitService portraitService;

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
