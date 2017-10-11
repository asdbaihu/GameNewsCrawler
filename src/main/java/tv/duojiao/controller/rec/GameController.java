package tv.duojiao.controller.rec;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import tv.duojiao.model.rec.Game;
import tv.duojiao.core.Result;
import tv.duojiao.core.ResultGenerator;
import tv.duojiao.service.rec.GameService;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/09/22.
*/
@RestController
@RequestMapping("/game")
public class GameController {
    @Resource
    private GameService gameService;

    @PostMapping
    public Result add(@RequestBody Game game) {
        gameService.save(game);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        gameService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Game game) {
        gameService.update(game);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Game game = gameService.findById(id);
        return ResultGenerator.genSuccessResult(game);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Game> list = gameService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
