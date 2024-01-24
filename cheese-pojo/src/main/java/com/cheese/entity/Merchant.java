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
public class Merchant implements Serializable
{
    // 商家ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 商家名称
    private String name;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 手机号
    private String phone;

    // 商家地址
    private String address;

    // 商家负责人身份证号码
    private String idNumber;

    // 商家公告
    private String announcement;

    // 运营状态，1正常 0休息
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
}
