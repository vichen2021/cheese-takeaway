package com.cheese.controller.admin;

import com.cheese.dto.SetmealDTO;
import com.cheese.dto.SetmealPageQueryDTO;
import com.cheese.result.Result;
import com.cheese.service.SetmealService;
import com.cheese.vo.SetmealVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/26 4:11
 * @projectName cheese-takeaway
 */
@RestController()
@Tag(name = "套餐相关接口")
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController
{
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public Result Page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}",setmealPageQueryDTO);
        return Result.success(setmealService.getPageWithCategory(setmealPageQueryDTO));
    }
    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "新增套餐")
    public Result saveSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}",setmealDTO);
        setmealService.saveSetmeal(setmealDTO);
        return Result.success();
    }
    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        return Result.success(setmealService.getAllById(id));
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Operation(summary = "批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @Operation(summary = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.updateAll(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐起售、停售
     *
     * @param status
     * @return
     */
    @PostMapping("status/{status}")
    @Operation(summary = "套餐起售、停售")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result status(@PathVariable Integer status, Long id) {
        setmealService.changeStatus(status, id);
        return Result.success();
    }

}
