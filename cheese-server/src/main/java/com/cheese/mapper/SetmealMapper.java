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
    IPage<SetmealVO> selectPageVO(IPage<SetmealVO> page, @Param(Constants.WRAPPER) Wrapper<SetmealVO> wrapper);

    /**
     * 根据套餐id查询包含的菜品列表
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishWithCopies(Long id);
}
