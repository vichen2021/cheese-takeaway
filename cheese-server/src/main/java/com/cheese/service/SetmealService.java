package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.SetmealDTO;
import com.cheese.dto.SetmealPageQueryDTO;
import com.cheese.entity.Setmeal;
import com.cheese.vo.DishItemVO;
import com.cheese.vo.SetmealVO;

import java.util.List;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

public interface SetmealService extends IService<Setmeal> {

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    IPage<SetmealVO> getPageWithCategory(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    void saveSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     *
     * @param status
     * @param id
     */
    void changeStatus(Integer status, Long id);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO getAllById(Long id);

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    void updateAll(SetmealDTO setmealDTO);

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     * @return
     */
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishById(Long id);
}
