package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.context.BaseContext;
import com.cheese.dto.ShoppingCartDTO;
import com.cheese.entity.Dish;
import com.cheese.entity.Setmeal;
import com.cheese.entity.ShoppingCart;
import com.cheese.mapper.DishMapper;
import com.cheese.mapper.SetmealMapper;
import com.cheese.mapper.ShoppingCartMapper;
import com.cheese.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway

 */

@Service
@Transactional
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = (Long)BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //查找当前用户添加的购物车商品,可能为空
        List<ShoppingCart> list = shoppingCartMapper.selectList(new QueryWrapper<ShoppingCart>()
                .eq(shoppingCart.getUserId() != null, "user_id", shoppingCart.getUserId())
                .eq(shoppingCart.getDishId() != null, "dish_id", shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, "setmeal_id", shoppingCart.getSetmealId())
                .eq(shoppingCart.getDishFlavor() != null, "dish_flavor", shoppingCart.getDishFlavor())
        );
        //判断当前加入到购物车中的商品是否已经存在
        if (list != null && list.size() > 0) {
            //如果已经存在，只需要将数量加一
            //因为是根据几种条件获取的值，所以要么查询到一条数据，要么查询不到数据，所以直接获取第一条数据即可
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateById(cart);
        } else {
            //如果不存在，需要插入一条购物车数据
            Long dishId = shoppingCart.getDishId();
            //如果dishId不为空就代表选择的是菜品
            if (dishId != null) {
                Dish dish = dishMapper.selectById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                //如果dishId为空就代表选的是套餐
                Long setmealId = shoppingCart.getSetmealId();
                Setmeal setmeal = setmealMapper.selectById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            //添加公共项
            //初始数量都为1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 删除购物车中一个商品
     *
     * @param shoppingCartDTO
     */
    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId((Long)BaseContext.getCurrentId())
                .build();
        //查找当前用户的购物车数据,list的方法，增加的方法可能为空，当前是已经添加了，所以是默认存在，用list的方法
        List<ShoppingCart> list = shoppingCartMapper.selectList(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, shoppingCart.getUserId()));
        //判断当前加入到购物车中的商品是否已经存在
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            //如果数量是一就直接删除
            if (cart.getNumber() == 1) {
                shoppingCartMapper.deleteById(cart.getId());
            } else {
                //如果不是就减去一个
                cart.setNumber(cart.getNumber() - 1);
                shoppingCartMapper.updateById(cart);
            }
        }
    }
}
