package com.cheese.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MerchantDTO implements Serializable
{
    // 商家ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 商家名称
    private String name;

    // 用户名
    private String username;

    // 手机号
    private String phone;

    // 商家地址
    private String address;

    // 商家负责人身份证号码
    private String idNumber;


}
