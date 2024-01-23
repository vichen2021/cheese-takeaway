package com.cheese.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheese.entity.Category;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper extends BaseMapper<Category>
{

}
