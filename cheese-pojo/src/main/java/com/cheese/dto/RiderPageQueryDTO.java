package com.cheese.dto;

import lombok.Data;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 13:32
 * @projectName cheese-takeaway
 */
@Data
public class RiderPageQueryDTO
{
    private int page;

    private int pageSize;

    // 姓名
    private String name;

    //身份证号
    private String idNumber;

    // 手机号
    private String phone;
}
