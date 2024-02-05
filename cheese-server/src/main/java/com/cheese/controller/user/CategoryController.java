package com.cheese.controller.user;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheese.constant.StatusConstant;
import com.cheese.entity.Category;
import com.cheese.mapper.CategoryMapper;
import com.cheese.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 * @CreateTime 2023/7/30 19:27
 */
@RestController("userCategoryController")
@Slf4j
@Tag(name = "分类")
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 类别查询
     *
     * @param id 商户id
     * @return
     */
    @GetMapping("/list/{id}")
    @Operation(summary = "类别查询")
    public Result<List<Category>> list(@PathVariable Integer id) {
        //查询在售
        Category category = Category.builder()
                .status(StatusConstant.ENABLE)
                .build();
        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>()
                .eq(id != null, "merchant_id", id)
                .eq("status", StatusConstant.ENABLE)
                .orderByAsc("sort")
                .orderByDesc("create_time")
        );
        return Result.success(list);
    }
}
