package com.cheese.controller.admin;

import com.cheese.constant.JwtClaimsConstant;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.entity.Merchant;
import com.cheese.properties.JwtProperties;
import com.cheese.result.Result;
import com.cheese.service.MerchantService;
import com.cheese.utils.JwtUtil;
import com.cheese.vo.MerchantLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/merchant")
@Slf4j
@Tag(name = "商户相关接口")
public class MerchantController
{

    private final MerchantService merchantService;

    private final JwtProperties jwtProperties;


    @Autowired
    public MerchantController(MerchantService merchantService,JwtProperties jwtProperties){
        this.merchantService = merchantService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 商户登录
     * @param merchantLoginDTO
     * @return
     */
    @Operation(summary = "商户登录")
    @PostMapping("/login")
    public Result<MerchantLoginVO> login(@RequestBody MerchantLoginDTO merchantLoginDTO) {
        log.info("商户登录：{}",merchantLoginDTO);

        Merchant merchant = merchantService.login(merchantLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, merchant.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        MerchantLoginVO merchantLoginVO = MerchantLoginVO.builder()
                .id(merchant.getId())
                .userName(merchant.getUsername())
                .name(merchant.getName())
                .token(token)
                .build();

        return Result.success(merchantLoginVO);



    }
}
