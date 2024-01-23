package com.cheese.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.MerchantDTO;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.entity.Merchant;

public interface MerchantService extends IService<Merchant>
{
    /**
     * 商户登录
     * @param merchantLoginDTO
     * @return
     */
    Merchant login(MerchantLoginDTO merchantLoginDTO);

    /**
     * 添加商户
     * @param merchantDTO
     * @return
     */
    boolean addMerchant(MerchantDTO merchantDTO);
}
