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
