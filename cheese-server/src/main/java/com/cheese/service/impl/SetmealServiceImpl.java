package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.constant.MessageConstant;
import com.cheese.constant.StatusConstant;
import com.cheese.dto.SetmealDTO;
import com.cheese.dto.SetmealPageQueryDTO;
import com.cheese.entity.Dish;
import com.cheese.entity.Setmeal;
import com.cheese.entity.SetmealDish;
import com.cheese.exception.DeletionNotAllowedException;
import com.cheese.exception.SetmealEnableFailedException;
import com.cheese.mapper.SetmealDishMapper;
import com.cheese.mapper.SetmealMapper;
import com.cheese.service.DishService;
import com.cheese.service.SetmealDishService;
import com.cheese.service.SetmealService;
import com.cheese.vo.DishItemVO;
import com.cheese.vo.SetmealVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishService dishService;

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public IPage<SetmealVO> getPageWithCategory(SetmealPageQueryDTO setmealPageQueryDTO) {
        return setmealMapper.selectPageVO(
                new Page<>(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize()),
                new QueryWrapper<SetmealVO>()
                        .like(StringUtils.isNotEmpty(setmealPageQueryDTO.getName()), "s.name", setmealPageQueryDTO.getName())
                        .eq(setmealPageQueryDTO.getCategoryId() != null, "s.category_id", setmealPageQueryDTO.getCategoryId())
                        .eq(setmealPageQueryDTO.getStatus() != null, "s.status", setmealPageQueryDTO.getStatus())
                        .orderByAsc("s.create_time")
        );
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    public void saveSetmeal(SetmealDTO setmealDTO) {
        //添加套餐进入套餐表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);
        //保存套餐和菜品的关联关系进入关联表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            //保存套餐的id
            //不保存dish的id的原因是因为setmeal对象是new出来的，需要设置，而前端传来的有dish的id，所以不需要再设置
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 套餐起售、停售
     *
     * @param status
     * @param id
     */
    @Override
    public void changeStatus(Integer status, Long id) {
        //如果是起售，要判断其中是否含有没有起售的菜品
        if (status == StatusConstant.ENABLE) {
            List<SetmealDish> setmealDishesWithDishId = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>()
                    .select(SetmealDish::getDishId)
                    .eq(SetmealDish::getSetmealId, id)
            );
//            System.out.println(setmealDishesWithDishId);
            //[SetmealDish(id=null, setmealId=null, dishId=67, name=null, price=null, copies=null),
            // SetmealDish(id=null, setmealId=null, dishId=66, name=null, price=null, copies=null),
            // SetmealDish(id=null, setmealId=null, dishId=65, name=null, price=null, copies=null)]
            List<Long> dishIds = setmealDishesWithDishId.stream().map(SetmealDish::getDishId).collect(Collectors.toList());
//            System.out.println(dishIds);
            //[67, 66, 65]
            List<Dish> dishes = dishService.listByIds(dishIds);
            // 查询（根据ID 批量查询）
            //Collection<T> listByIds(Collection<? extends Serializable> idList);
            if (dishes != null && dishes.size() > 0) {
                dishes.forEach(dish -> {
                    if (dish.getStatus() == StatusConstant.DISABLE) {
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        //停售
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal, new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getId, id));
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        //遍历当前套餐id
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.selectById(id);
            //如果套餐起售就不能删除
            if (setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
            //删除套餐及套餐关系表
            setmealMapper.deleteById(id);
            setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id));
        }
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO getAllById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id));
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    @Override
    public void updateAll(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal, new LambdaQueryWrapper<Setmeal>().eq(Setmeal::getId, setmealDTO.getId()));
        setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setmealDTO.getId()));
        //保存套餐和菜品的关联关系进入关联表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            //保存套餐的id
            //不保存dish的id的原因是因为setmeal对象是new出来的，需要设置，而前端传来的有dish的id，所以不需要再设置
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     */
    @Override
    public List<Setmeal> getByCategoryId(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return setmealMapper.selectList(new LambdaQueryWrapper<Setmeal>()
                .eq(Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, setmeal.getStatus())
        );
    }

    /**
     * 根据套餐id查询包含的菜品
     *
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishById(Long id) {
        //因为mp比较麻烦，所以写成sql语句
        return setmealMapper.getDishWithCopies(id);
    }
}
