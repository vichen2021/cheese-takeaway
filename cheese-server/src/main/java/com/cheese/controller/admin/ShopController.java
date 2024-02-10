package com.cheese.controller.admin;

import com.cheese.context.BaseContext;
import com.cheese.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/29 21:22
 * @projectName cheese-takeaway
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Tag(name = "店铺相关接口")
@Slf4j
public class ShopController
{
    public static String KEY = "SHOP_STATUS_";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺的营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @Operation(summary = "设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status){
        String currentMerchantKey = KEY+(Long)BaseContext.getCurrentId();
        log.info("设置店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(currentMerchantKey,status);
        return Result.success();
    }
    @GetMapping("/status")
    @Operation(summary = "获取店铺的营业状态")
    public Result<Integer> getStatus(){
        String currentMerchantKey = KEY+(Long)BaseContext.getCurrentId();
        Integer status = (Integer) redisTemplate.opsForValue().get(currentMerchantKey);
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
