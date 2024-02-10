package com.cheese.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理员登录返回的数据格式")
public class SystemUserLoginVO implements Serializable
{
    @Schema(description = "管理员Id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Schema(description = "管理员用户名")
    private String userName;

    @Schema(description = "管理员姓名")
    private String name;

    @Schema(description = "jwt令牌")
    private String token;
}