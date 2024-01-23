package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.constant.MessageConstant;
import com.cheese.constant.StatusConstant;
import com.cheese.dto.MerchantDTO;
import com.cheese.dto.SystemUserLoginDTO;
import com.cheese.entity.SystemUser;
import com.cheese.exception.AccountLockedException;
import com.cheese.exception.AccountNotFoundException;
import com.cheese.exception.PasswordErrorException;
import com.cheese.mapper.SystemUserMapper;
import com.cheese.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService
{

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 管理员登录
     *
     * @param systemUserLoginDTO
     * @return
     */
    @Override
    public SystemUser login(SystemUserLoginDTO systemUserLoginDTO)
    {
        String username = systemUserLoginDTO.getUsername();
        String password = systemUserLoginDTO.getPassword();

        // mybatis-plus 根据用户名查询数据库中的用户数据
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemUser::getUsername,username);
        SystemUser systemUser = systemUserMapper.selectOne(wrapper);

        // 处理账号异常
        if (systemUser ==null){
            // 账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 明文密码Md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(systemUser.getPassword())){
            // 密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (systemUser.getStatus() == StatusConstant.DISABLE){
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        return systemUser;
    }

}
