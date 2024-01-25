package com.cheese.controller.admin;

import com.cheese.dto.DishDTO;
import com.cheese.dto.DishPageQueryDTO;
import com.cheese.entity.Dish;
import com.cheese.result.Result;
import com.cheese.service.DishService;
import com.cheese.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @author Wei Chen Guang
 * @ProjectName cheese-take-out
 */

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Tag(name = "菜品")
public class DishController
{

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品及口味
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @Operation(summary ="新增菜品及口味")
    public Result saveWithFlavor(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品信息,{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        //清理缓存数据
//        String key = "dish_" + dishDTO.getCategoryId();
//        cleanCache(key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @Operation(summary ="菜品分页查询")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询,{}",dishPageQueryDTO);
        return Result.success(dishService.dishPage(dishPageQueryDTO));
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary ="批量删除菜品")
    //@RequestParam可以处理简单类型得绑定
    public Result delete(@RequestParam List<Long> ids) {
        dishService.deleteByIds(ids);
        //将所有的菜品缓存数据清理掉
        // cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary ="根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        return Result.success(dishService.getDishWithFlavorById(id));
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @Operation(summary ="修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.updateDishAndFlavor(dishDTO);
        //将所有的菜品缓存数据清理掉
//        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary ="根据分类id查询菜品")
    //加入了name,以便于用菜品名查询
    public Result<List<Dish>> getByCategoryId(Long categoryId, String name) {
        List<Dish> dishList = dishService.getByCategoryId(categoryId, name);
        return Result.success(dishList);
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary ="修改状态")
    public Result changeStatus(@PathVariable Integer status, Long id) {
        dishService.updateStatus(status, id);
        //将所有的菜品缓存数据清理掉
//        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 清理缓存数据
     *
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
