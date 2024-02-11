package com.cheese.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheese.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
