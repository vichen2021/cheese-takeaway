package com.cheese.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheese.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Mapper
public interface UserMapper extends BaseMapper<Users> {
}
