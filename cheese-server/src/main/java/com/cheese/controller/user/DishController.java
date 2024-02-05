package com.cheese.controller.user;

import com.cheese.result.Result;
import com.cheese.service.DishService;
import com.cheese.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 * @CreateTime 2023/8/1 10:28
 */

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Tag(name = "用户端菜品")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品及口味
     *
     * @return
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<DishVO>> getByCategoryId(Long categoryId) {
        //构造redis中的key,规则:dish_分类id
        String key = "dish_" + categoryId;
        //查询redis中是否存在菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if (list != null && list.size() > 0) {
            //如果存在,直接返回
            return Result.success(list);
        }
        //如果不存在，查询数据库,并将数据放入redis中
        list = dishService.getDishWithFlavorByCategoryId(categoryId);
        redisTemplate.opsForValue().set(key, list);
        return Result.success(list);
    }


}
