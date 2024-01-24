package com.cheese.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(value = "id", type = IdType.AUTO)
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
