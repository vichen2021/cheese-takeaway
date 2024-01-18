package com.cheese.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    // 菜品ID
    private Long id;

    // 菜品名称
    private String name;

    // 分类ID，逻辑外键
    private Long categoryId;

    // 菜品价格
    private BigDecimal price;

    // 图片路径
    private String image;

    // 菜品描述
    private String description;

    // 售卖状态，1起售 0停售
    private Integer status;

    // 创建时间
    private LocalDateTime createTime;

    // 最后修改时间
    private LocalDateTime updateTime;

    // 创建人ID
    private Long createUser;

    // 最后修改人ID
    private Long updateUser;

}
