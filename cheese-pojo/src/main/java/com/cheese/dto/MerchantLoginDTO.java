package com.cheese.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "商户登录信息")
public class MerchantLoginDTO implements Serializable
{
    @Schema(description = "商户用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
