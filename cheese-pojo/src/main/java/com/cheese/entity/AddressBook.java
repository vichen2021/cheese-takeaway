package com.cheese.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    // 地址ID
    private Long id;

    // 用户ID，逻辑外键
    private Long userId;

    // 收货人
    private String consignee;

    // 性别
    private String sex;

    // 手机号
    private String phone;

    // 学校编码
    private String schoolCode;

    // 学校名称
    private String schoolName;

    // 区域编码
    private String regionCode;

    // 区域名称
    private String regionName;

    // 宿舍楼编码
    private String dormitoryCode;

    // 宿舍楼名称
    private String dormitoryName;

    // 详细地址信息，具体到门牌号
    private String detail;

    // 是否默认地址，1是 0否
    private Boolean isDefault;
}
