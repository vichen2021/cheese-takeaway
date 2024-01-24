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
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;

    // 用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 微信用户的唯一标识
    private String openid;

    // 用户姓名
    private String name;

    // 手机号
    private String phone;

    // 性别
    private String sex;

    // 学号
    private String stuNumber;

    // 身份证号
    private String idNumber;

    // 微信用户头像路径
    private String avatar;

    // 账号状态，1正常 2禁用
    private Integer status;

    // 删除标记，null 正常 非空 已删除
    @TableId("is_delete")
    private LocalDateTime isDelete;

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

    // 备注
    private String note;
}
