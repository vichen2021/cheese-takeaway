package com.cheese.dto;

import lombok.Data;

@Data
public class MerchantPageQueryDTO
{
    //查询内容
    private String content;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}
