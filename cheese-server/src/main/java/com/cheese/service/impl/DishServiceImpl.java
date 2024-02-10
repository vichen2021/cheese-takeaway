package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.constant.MessageConstant;
import com.cheese.constant.StatusConstant;
import com.cheese.context.BaseContext;
import com.cheese.dto.DishDTO;
import com.cheese.dto.DishPageQueryDTO;
import com.cheese.entity.Dish;
import com.cheese.entity.DishFlavor;
import com.cheese.entity.Setmeal;
import com.cheese.entity.SetmealDish;
import com.cheese.exception.DeletionNotAllowedException;
import com.cheese.mapper.DishFlavorMapper;
import com.cheese.mapper.DishMapper;
import com.cheese.mapper.SetmealDishMapper;
import com.cheese.mapper.SetmealMapper;
import com.cheese.service.DishFlavorService;
import com.cheese.service.DishService;
import com.cheese.vo.DishVO;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        dish.setMerchantId((Long)BaseContext.getCurrentId());
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
        return dishMapper.selectPageVo(
                new Page<>(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize()),
                new QueryWrapper<DishVO>()
                        .like(StringUtils.isNotEmpty(dishPageQueryDTO.getName()), "d.name", dishPageQueryDTO.getName())
                        .eq((Long)BaseContext.getCurrentId() != null, "d.merchant_id", (Long)BaseContext.getCurrentId())
                        .eq(dishPageQueryDTO.getCategoryId() != null, "d.category_id", dishPageQueryDTO.getCategoryId())
                        .eq(dishPageQueryDTO.getStatus() != null, "d.status", dishPageQueryDTO.getStatus())
                        .orderByAsc("d.create_time")
        );
//        return dishMapper.selectPageVo(
//                new Page<>(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize()),
//                            new LambdaQueryWrapper<DishVO>()
//                                .like(StringUtils.isNotEmpty(dishPageQueryDTO.getName()),DishVO::getName,dishPageQueryDTO.getName())
//                                .eq(dishPageQueryDTO.getStatus()!= null,DishVO::getStatus,dishPageQueryDTO.getStatus())
//                                .eq(dishPageQueryDTO.getCategoryId()!= null,DishVO::getCategoryId,dishPageQueryDTO.getCategoryId())
//                                    .orderByDesc(DishVO::getUpdateTime) // 改成上面非lambda形式不报错
//                            );
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Long> ids)
    {

        //判断当前菜品是否存在起售中的菜品
        for (Long id : ids)
        {
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus() == StatusConstant.ENABLE)
            {
                //当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否被关联套餐
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SetmealDish::getSetmealId)
                .in(SetmealDish::getDishId,ids);
        System.out.println("判断当前菜品是否被关联套餐"+ids);
        List<List<Long>> dishIds = Arrays.asList(ids);
        System.out.println("判断当前菜品是否被关联套餐"+dishIds);
        List<Long> setmealIds = setmealDishMapper.selectList(queryWrapper)
                .stream()
                .map(SetmealDish::getSetmealId).toList();

        //删除菜品表中的菜品数据，并删除关联的口味数据
        if (setmealIds != null && setmealIds.size() > 0) {
            //当前菜品被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表中的菜品数据
        for (Long id : ids) {
            dishMapper.deleteById(id);
            //删除菜品关联的口味数据
            dishFlavorMapper.deleteById(id);
        }
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
        //根据id查询菜品数据
        Dish dish = dishMapper.selectById(id);
        //根据id查询口味数据
        List<DishFlavor> dishFlavors = new ArrayList<>(dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id)));
        //将查询得到的数据返回VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Override
    public void updateDishAndFlavor(DishDTO dishDTO)
    {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //修改菜品基本信息
        dishMapper.update(dish, new QueryWrapper<Dish>().eq("id", dishDTO.getId()));
        //删除原有得口味数据
        dishFlavorMapper.delete(new QueryWrapper<DishFlavor>().eq("dish_id", dishDTO.getId()));
        //重新插入口味数据
        //向口味表添加多条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            //遍历，为每一个口味插入dish的id
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
        }
        //批量添加
        dishFlavorService.saveBatch(flavors);
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
        Dish dish = Dish.builder()
                .name(name)
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.selectList(new QueryWrapper<Dish>()
                .eq(dish.getCategoryId() != null, "category_id", dish.getCategoryId())
                .eq(dish.getStatus() != null, "status", dish.getStatus())
                .like(dish.getName() != null, "name", dish.getName())
                .orderByAsc("create_time")
        );
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
        //更新菜品状态
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.update(dish, new QueryWrapper<Dish>()
                .eq(dish.getId() != null, "id", dish.getId())
        );
        //如果是停用
        if (status == StatusConstant.DISABLE) {
            //更新套餐状态
            //根据dish_id得到setmeal_id
            List<Long> setmealIds = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>()
                            .select(SetmealDish::getSetmealId)
                            .eq(SetmealDish::getDishId, id))
                    .stream()
                    .map(SetmealDish::getSetmealId)
                    .collect(Collectors.toList()
                    );
            //如果存在就更改相关套餐的状态
            if (setmealIds != null && setmealIds.size() > 0) {
                for (Long setmealId : setmealIds) {
                    Setmeal setmeal = Setmeal.builder()
                            .id(setmealId)
                            .status(StatusConstant.DISABLE)
                            .build();
                    setmealMapper.update(setmeal, new QueryWrapper<Setmeal>()
                            .eq(setmeal.getId() != null, "id", setmeal.getId())
                    );
                }
            }
        }
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
        List<Dish> dishList = dishMapper.selectList(new LambdaQueryWrapper<Dish>().eq(Dish::getCategoryId, categoryId));
        List<DishVO> list = new ArrayList<>();
        for (Dish dish : dishList) {
            //查询起售中的菜品
            if (dish.getStatus() == StatusConstant.ENABLE) {
                DishVO dishVO = new DishVO();
                BeanUtils.copyProperties(dish, dishVO);
                //根据菜品id查询对应的口味
                List<DishFlavor> dishFlavors = new ArrayList<>(dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, dish.getId())));
                dishVO.setFlavors(dishFlavors);
                list.add(dishVO);
            }
        }
        return list;
    }
}
