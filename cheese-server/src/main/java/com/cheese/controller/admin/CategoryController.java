package com.cheese.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheese.context.BaseContext;
import com.cheese.entity.Merchant;
import com.cheese.service.CategoryService;
import com.cheese.constant.StatusConstant;
import com.cheese.dto.CategoryDTO;
import com.cheese.entity.Category;
import com.cheese.mapper.CategoryMapper;
import com.cheese.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ja
 * @ProjectName sky-take-out
 * @CreateTime 2023/7/30 19:27
 */
@RestController("adminCategoryController")
@Slf4j
@Tag(name = "分类相关接口")
@RequestMapping("/admin/category")
public class CategoryController
{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 商品分类
     *
     * @param page
     * @param pageSize
     * @param category
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "商品分类")
    public Result page(int page, int pageSize, Category category) {
        return Result.success(categoryService.getPage(page, pageSize, category));
    }

    /**
     * 启用或禁用商品状态
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary ="修改状态")
    public Result updateOrStop(@PathVariable Integer status, Long id) {
        log.info("启用或禁用员工状态:{},{}", status, id);
        categoryService.updateOrStop(status, id);
        return Result.success();
    }

    /**
     * 更新商品分类
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @Operation(summary ="更新商品信息")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category = setCurrentUserAndDate(category,false);
        categoryService.updateById(category);
        return Result.success();
    }
    /**
     * 公共字段赋值方法
     * @param entity
     * @param isAdd 是否为新增
     * @return
     */
    public Category setCurrentUserAndDate(Category entity, boolean isAdd)
    {
        if (isAdd)
        {
            entity.setCreateTime(LocalDateTime.now());
            entity.setCreateUser(BaseContext.getCurrentId());
        }
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdateUser(BaseContext.getCurrentId());
        return entity;
    }
    /**
     * 新增商品
     *
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @Operation(summary ="新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.ENABLE);
        category.setMerchantId(BaseContext.getCurrentId());
        category = setCurrentUserAndDate(category,true);
        categoryService.save(category);
        return Result.success();
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary ="删除分类")
    public Result deleteById(Long id) {
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 类别查询
     * @param type
     * @return
     */
    @GetMapping("/list")
    @Operation(summary ="类别查询")
    public Result<List<Category>> list(Integer type) {
        return Result.success(categoryMapper.selectList(new QueryWrapper<Category>()
                .eq("type", type)
                .orderByAsc("sort")
                .orderByDesc("create_time")
        ));
    }
}
