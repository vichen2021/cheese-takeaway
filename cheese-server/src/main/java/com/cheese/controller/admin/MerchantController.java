package com.cheese.controller.admin;

import com.cheese.constant.JwtClaimsConstant;
import com.cheese.constant.PasswordConstant;
import com.cheese.constant.StatusConstant;
import com.cheese.context.BaseContext;
import com.cheese.dto.MerchantDTO;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.dto.MerchantPageQueryDTO;
import com.cheese.entity.Merchant;
import com.cheese.properties.JwtProperties;
import com.cheese.result.Result;
import com.cheese.service.MerchantService;
import com.cheese.utils.JwtUtil;
import com.cheese.vo.MerchantLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController("adminMerchantController")
@RequestMapping("/admin/merchant")
@Slf4j
@Tag(name = "商户相关接口")
public class MerchantController
{

    private final MerchantService merchantService;

    private final JwtProperties jwtProperties;


    @Autowired
    public MerchantController(MerchantService merchantService, JwtProperties jwtProperties)
    {
        this.merchantService = merchantService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 商户登录
     *
     * @param merchantLoginDTO
     * @return
     */
    @Operation(summary = "商户登录")
    @PostMapping("/login")
    public Result<MerchantLoginVO> login(@RequestBody MerchantLoginDTO merchantLoginDTO)
    {
        log.info("商户登录：{}", merchantLoginDTO);

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

    @PostMapping("/logout")
    @Operation(summary = "退出登录")
    public Result<String> logout() {
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商户")
    public Result removeById(@PathVariable("id") Long id)
    {
        log.info("删除商户：{}", id);
        merchantService.removeById(id);
        return Result.success();
    }

    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id)
    {
        log.info("启用或禁用员工状态:{},{}", status, id);
        merchantService.updateOrStop(status, id);
        return Result.success();
    }


    @PostMapping
    @Operation(summary = "添加商户")
    public Result addMerchant(@RequestBody MerchantDTO merchantDTO)
    {
        log.info("添加商户：{}", merchantDTO);
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant);
        merchant.setStatus(StatusConstant.ENABLE);
        merchant.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        merchantService.save(merchant);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "商户分页查询")
    public Result page(MerchantPageQueryDTO merchantPageQueryDTO)
    {
        log.info("商户分页查询，{}", merchantPageQueryDTO);
        return Result.success(merchantService.pageQuery(merchantPageQueryDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询商户信息")
    public Result<Merchant> getById(@PathVariable Long id)
    {
        log.info("根据id查询商户信息，{}", id);
        Merchant merchant = merchantService.getById(id);
        // 防止密码泄露
        merchant.setPassword("*****");
        return Result.success(merchant);
    }
    @GetMapping("/detail")
    @Operation(summary = "查询当前商户信息")
    public Result<Merchant> getByCurrentId()
    {
        Merchant merchant = merchantService.getById(BaseContext.getCurrentId());
        // 防止密码泄露
        merchant.setPassword("*****");
        return Result.success(merchant);
    }

    @PutMapping
    @Operation(summary = "更新商户信息")
    public Result UpdateMerchant(@RequestBody MerchantDTO merchantDTO)
    {
        log.info("更新商户信息：{}", merchantDTO);
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDTO, merchant);
        merchantService.updateById(merchant);

        return Result.success();
    }

}
