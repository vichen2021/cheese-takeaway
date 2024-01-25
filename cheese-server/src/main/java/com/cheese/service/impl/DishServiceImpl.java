package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.dto.DishDTO;
import com.cheese.dto.DishPageQueryDTO;
import com.cheese.entity.Dish;
import com.cheese.entity.DishFlavor;
import com.cheese.mapper.DishFlavorMapper;
import com.cheese.mapper.DishMapper;
import com.cheese.mapper.SetmealDishMapper;
import com.cheese.mapper.SetmealMapper;
import com.cheese.service.DishFlavorService;
import com.cheese.service.DishService;
import com.cheese.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService
{
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加菜品及口味
     *
     * @param dishDTO
     */
    @Override
    public void saveWithFlavor(DishDTO dishDTO)
    {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表添加一条数据
        dishMapper.insert(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && flavors.size() > 0){
            //遍历，为每一个口味插入dish的id
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
            // 伪批量插入，添加口味信息
            dishFlavorService.saveBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public IPage<DishVO> dishPage(DishPageQueryDTO dishPageQueryDTO)
    {
        return null;
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Long> ids)
    {

    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getDishWithFlavorById(Long id)
    {
        return null;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Override
    public void updateDishAndFlavor(DishDTO dishDTO)
    {

    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @param name
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId, String name)
    {
        return null;
    }

    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id)
    {

    }

    /**
     * 根据分类id查询菜品及口味
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> getDishWithFlavorByCategoryId(Long categoryId)
    {
        return null;
    }
}
