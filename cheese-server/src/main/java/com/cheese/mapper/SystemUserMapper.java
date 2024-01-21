package com.cheese.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheese.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser>
{
}
