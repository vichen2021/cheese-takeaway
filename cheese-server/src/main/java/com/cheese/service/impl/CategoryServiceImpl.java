package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.context.BaseContext;
import com.cheese.service.CategoryService;
import com.cheese.constant.MessageConstant;
import com.cheese.entity.Category;
import com.cheese.entity.Dish;
import com.cheese.entity.Setmeal;
import com.cheese.exception.DeletionNotAllowedException;
import com.cheese.mapper.CategoryMapper;
import com.cheese.mapper.DishMapper;
import com.cheese.mapper.SetmealMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService
{
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 商品分类
     *
     * @param page
     * @param pageSize
     * @param category
     * @return
     */
    @Override
    public IPage<Category> getPage(int page, int pageSize, Category category) {
        return this.page(new Page<>(page, pageSize), new QueryWrapper<Category>()
                .like(StringUtils.isNotEmpty(category.getName()), "name", category.getName())
                .eq(category.getType() != null, "type", category.getType())
                .eq(true,"merchant_id", BaseContext.getCurrentId())
                .orderByAsc("sort")
                .orderByDesc("create_time")
        );
    }

    /**
     * 启用或禁用商品状态
     *
     * @param status
     * @param id
     */
    public void updateOrStop(Integer status, Long id) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Category category = Category.builder()
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category, updateWrapper);
    }

    /**
     * 删除分类
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        // Integer selectCount(@Param("ew") Wrapper<T> queryWrapper);
        // 因为category表的id就是dish和setmeal表中的category_id,所以直接传category的id即可

        //查询当前分类是否关联有菜品，如果关联了就输出业务异常
        Integer count = Math.toIntExact(dishMapper.selectCount(new QueryWrapper<Dish>().eq("category_id", id)));
        // sql相当于
        // @Select("select count(id) from dish where category_id = #{categoryId}")
        if (count > 0) {
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        //查询当前分类是否关联有菜品，如果关联了就输出业务异常
        count = Math.toIntExact(setmealMapper.selectCount(new QueryWrapper<Setmeal>().eq("category_id", id)));
        // sql相当于
        // @Select("select count(id) from setmeal where category_id = #{categoryId}")
        if (count > 0) {
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        //删除分类
        categoryMapper.deleteById(id);
    }


}
