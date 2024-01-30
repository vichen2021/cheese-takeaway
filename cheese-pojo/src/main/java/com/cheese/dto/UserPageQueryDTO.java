package com.cheese.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 10:56
 * @projectName cheese-takeaway
 */
@Data
public class UserPageQueryDTO implements Serializable
{
    private int page;

    private int pageSize;

    // 学生姓名
    private String name;

    //学号
    private String stuNumber;

    // 手机号
    private String phone;

    //身份证号
    private String idNumber;
}
