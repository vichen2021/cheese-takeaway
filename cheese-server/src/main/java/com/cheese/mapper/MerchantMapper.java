package com.cheese.mapper;

import com.cheese.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MerchantMapper
{
    /**
     * 根据用户名查询商户信息
     * @param username
     * @return
     */
    @Select("select * from merchant where username = #{username}")
    Merchant getByUsername(String username);
}
