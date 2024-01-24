package com.cheese.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(value = "id", type = IdType.AUTO)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 最后修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 创建人ID
    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private Long createUser;

    // 最后修改人ID
        @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    // 关联商户ID
    private Long merchantId;

}
