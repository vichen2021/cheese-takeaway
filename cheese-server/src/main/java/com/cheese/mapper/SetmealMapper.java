package com.cheese.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cheese.entity.Setmeal;
import com.cheese.entity.Setmeal;
import com.cheese.vo.DishItemVO;
import com.cheese.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    /**
     * 套餐分页查询
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Select(" select s.*, c.name as categoryName from setmeal s " +
            "left outer join category c " +
            "on s.category_id = c.id ${ew.customSqlSegment}")
    IPage<SetmealVO> selectPageVO(IPage<SetmealVO> page, @Param(Constants.WRAPPER) Wrapper<SetmealVO> wrapper);

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description from setmeal_dish sd " +
            "left outer join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{id}")
    List<DishItemVO> getDishWithCopies(Long id);
}
