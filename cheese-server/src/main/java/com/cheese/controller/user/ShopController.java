package com.cheese.controller.user;

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
 * @date 2024/1/29 21:30
 * @projectName cheese-takeaway
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Tag(name = "店铺相关接口")
@Slf4j
public class ShopController
{
    public static final String KEY = "SHOP_STATUS_";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status/{id}")
    @Operation(summary = "获取店铺的营业状态")
    public Result<Integer> getStatus(@PathVariable Long id){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY+id);// TODO 用户获取商户营业状态
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
