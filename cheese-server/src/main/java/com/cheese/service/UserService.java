package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.UserPageQueryDTO;
import com.cheese.entity.Users;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

public interface UserService extends IService<Users> {

    /**
     * 学生分页查询
     * @param userPageQueryDTO
     * @return
     */
    IPage<Users> page(UserPageQueryDTO userPageQueryDTO);

    /**
     * 启用或禁用学生账号
     *
     * @param status
     * @param id
     */
    void updateOrStop(Integer status, Long id);
}
