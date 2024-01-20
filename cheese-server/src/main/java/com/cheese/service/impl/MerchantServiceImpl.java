package com.cheese.service.impl;

import com.cheese.constant.*;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.entity.Merchant;
import com.cheese.exception.*;
import com.cheese.mapper.MerchantMapper;
import com.cheese.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class MerchantServiceImpl implements MerchantService
{
    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 商户登录
     * @param merchantLoginDTO
     * @return
     */
    public Merchant login(MerchantLoginDTO merchantLoginDTO){
        String username = merchantLoginDTO.getUsername();
        String password = merchantLoginDTO.getPassword();

        // 根据用户名查询数据库中的用户数据
        Merchant merchant = merchantMapper.getByUsername(username);

        // 处理账号异常

        if (merchant ==null){
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 明文密码Md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(merchant.getPassword())){
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (merchant.getStatus() == StatusConstant.DISABLE){
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        return merchant;
    }
}
