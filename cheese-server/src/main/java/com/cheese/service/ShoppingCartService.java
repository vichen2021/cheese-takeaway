package com.cheese.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.ShoppingCartDTO;
import com.cheese.entity.ShoppingCart;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 删除购物车中一个商品
     *
     * @param shoppingCartDTO
     */
    void delete(ShoppingCartDTO shoppingCartDTO);
}
