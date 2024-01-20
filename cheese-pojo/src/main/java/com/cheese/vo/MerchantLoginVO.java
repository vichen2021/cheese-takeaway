package com.cheese.vo;

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
@Schema(description = "商户登录返回的数据格式")
public class MerchantLoginVO implements Serializable
{
    @Schema(description = "商户Id")
    private Long id;

    @Schema(description = "商户用户名")
    private String userName;

    @Schema(description = "商户店名")
    private String name;

    @Schema(description = "jwt令牌")
    private String token;
}
