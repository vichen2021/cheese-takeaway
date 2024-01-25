package com.cheese.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cheese.entity.Dish;
import com.cheese.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
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
    @Select("select d.*, c.name as categoryName from dish d left outer join category c on d.category_id = c.id ${ew.customSqlSegment}")
    IPage<DishVO> selectPageVo(IPage<DishVO> page, @Param(Constants.WRAPPER) Wrapper<DishVO> wrapper);

}
