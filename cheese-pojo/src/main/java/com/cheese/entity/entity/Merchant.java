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
public class Merchant implements Serializable
{
    // 商家ID
    private Long id;

    // 商家名称
    private String name;

    // 密码
    private String password;

    // 手机号
    private String phone;

    // 商家地址
    private String address;

    // 商家公告
    private String announcement;

    // 运营状态，1正常 0休息
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
