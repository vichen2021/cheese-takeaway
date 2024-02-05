package com.cheese.controller.user;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cheese.constant.StatusConstant;
import com.cheese.entity.Merchant;
import com.cheese.result.Result;
import com.cheese.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WEI CHEN GUANG
 * @date 2024/2/3 0:41
 * @projectName cheese-takeaway
 */
@RestController("userMerchantController")
@RequestMapping("/user/merchant")
@Slf4j
@Tag(name = "商户相关接口")
public class MerchantController
{
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/list")
    @Operation(summary = "类别查询")
    @InterceptorIgnore(tenantLine = "on")
    public Result<List<Merchant>> list(Integer type) {
        //查询在售
        Merchant cerchant = Merchant.builder()
                .status(StatusConstant.ENABLE)
                .build();
        List<Merchant> list = merchantService.list(new QueryWrapper<Merchant>()
                .eq("status", StatusConstant.ENABLE)
                .orderByDesc("update_time")
        );
        return Result.success(list);
    }

    @GetMapping("/merchantDetail/{id}")
    @Operation(summary = "根据id查询商户信息")
    public Result<Merchant> getById(@PathVariable Long id)
    {
        log.info("根据id查询商户信息，{}", id);
        Merchant merchant = merchantService.getById(id);
        // 防止密码泄露
        merchant.setPassword("*****");
        return Result.success(merchant);
    }

}
