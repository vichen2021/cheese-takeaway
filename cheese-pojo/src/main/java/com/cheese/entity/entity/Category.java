package com.cheese.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    // 分类ID
    private Long id;

    // 分类名称
    private String name;

    // 分类类型，1菜品分类 2套餐分类
    private Integer type;

    // 排序字段，用于分类数据的排序
    private Integer sort;

    // 状态，1启用 0禁用
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
