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
public class SystemUser implements Serializable
{
    // 用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户姓名
    private String name;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 手机号
    private String phone;

    // 身份证号
    private String idNumber;

    // 账号状态，1正常 2禁用
    private Integer status;

    // 备注
    private String note;
}
