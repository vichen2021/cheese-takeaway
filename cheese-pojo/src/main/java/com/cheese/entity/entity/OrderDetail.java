package com.cheese.entity.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //名称
    private String name;

    //订单id
    private Long orderId;

    //菜品id
    private Long dishId;

    //套餐id
    private Long setmealId;

    //口味
    private String dishFlavor;

    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;

    // 删除标记，null 正常 非空 已删除
    private LocalDateTime isDelete;

    // 创建时间
    private LocalDateTime createTime;

    // 最后修改时间
    private LocalDateTime updateTime;

    // 创建人ID
    private Long createUser;

    // 最后修改人ID
    private Long updateUser;

    // 备注
    private String note;
}
