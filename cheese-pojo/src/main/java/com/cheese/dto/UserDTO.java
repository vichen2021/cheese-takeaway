package com.cheese.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 10:59
 * @projectName cheese-takeaway
 */
@Data
public class UserDTO
{
    // 用户ID
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    // 账号状态，1正常 2禁用
    private Integer status;
}
