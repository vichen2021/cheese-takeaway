package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.MerchantDTO;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.dto.MerchantPageQueryDTO;
import com.cheese.entity.Merchant;
import com.cheese.result.PageResult;

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

    /**
     * 商户分页查询
     * @param merchantPageQueryDTO
     * @return
     */
    IPage<Merchant> pageQuery(MerchantPageQueryDTO merchantPageQueryDTO);
}
