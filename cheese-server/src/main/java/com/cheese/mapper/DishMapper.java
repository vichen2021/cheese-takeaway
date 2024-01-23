package com.cheese.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cheese.entity.Dish;
import com.cheese.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ja
 * @ProjectName sky-take-out
 * @CreateTime 2023/7/30 19:25
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    /**
     * 菜品分页查询
     *
     * @param page
     * @param wrapper
     * @return
     */
    IPage<DishVO> selectPageVo(IPage<DishVO> page, @Param(Constants.WRAPPER) Wrapper<DishVO> wrapper);

}
