package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.DishDTO;
import com.cheese.dto.DishPageQueryDTO;
import com.cheese.entity.Dish;
import com.cheese.vo.DishVO;

import java.util.List;


public interface DishService extends IService<Dish>
{

    /**
     * 添加菜品及口味
     *
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    IPage<DishVO> dishPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    DishVO getDishWithFlavorById(Long id);

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    void updateDishAndFlavor(DishDTO dishDTO);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> getByCategoryId(Long categoryId, String name);

    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);

    /**
     * 根据分类id查询菜品及口味
     *
     * @param categoryId
     * @return
     */
    List<DishVO> getDishWithFlavorByCategoryId(Long categoryId);


}
