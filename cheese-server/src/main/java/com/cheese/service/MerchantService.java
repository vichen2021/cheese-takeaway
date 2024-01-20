package com.cheese.service;

import com.cheese.dto.MerchantLoginDTO;
import com.cheese.entity.Merchant;

public interface MerchantService
{
    /**
     * 商户登录
     * @param merchantLoginDTO
     * @return
     */
    Merchant login(MerchantLoginDTO merchantLoginDTO);
}
