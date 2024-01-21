package com.cheese.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.SystemUserLoginDTO;
import com.cheese.entity.SystemUser;

public interface SystemUserService extends IService<SystemUser>
{
    /**
     * 管理员登录
     * @param systemUserDTO
     * @return
     */
    SystemUser login(SystemUserLoginDTO systemUserDTO);
}
