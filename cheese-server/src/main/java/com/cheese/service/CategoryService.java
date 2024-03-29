package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.CategoryPageQueryDTO;
import com.cheese.entity.Category;


/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

public interface CategoryService extends IService<Category> {

    /**
     * 商品分类
     *
     * @param categoryPageQueryDTO
     * @return
     */
    IPage<Category> getPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用或禁用商品状态
     *
     * @param status
     * @param id
     */
    void updateOrStop(Integer status, Long id);

    /**
     * 删除分类
     *
     * @param id
     */
    void deleteById(Long id);

}
