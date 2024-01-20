package com.cheese.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Rider implements Serializable
{
    // 用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户姓名
    private String name;

    // 手机号
    private String phone;

    // 性别
    private String sex;

    // 年龄
    private Integer age;

    // 身份证号
    private String idNumber;

    // 订单完成数量
    private Integer orderFinishNumber;

    // 账号状态，1正常 2禁用
    private Integer status;

    // 删除标记，null 正常 非空 已删除
    @TableId("is_delete")
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
