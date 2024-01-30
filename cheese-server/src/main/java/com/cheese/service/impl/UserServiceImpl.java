package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.dto.UserPageQueryDTO;
import com.cheese.entity.Users;
import com.cheese.mapper.UserMapper;
import com.cheese.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户分页查询
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public IPage<Users> page(UserPageQueryDTO userPageQueryDTO)
    {
        return this.page(
                new Page<>(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize()),
                new QueryWrapper<Users>()
                        .like(StringUtils.isNotEmpty(userPageQueryDTO.getName()),"name",userPageQueryDTO.getName())
                        .like(StringUtils.isNotEmpty(userPageQueryDTO.getIdNumber()),"id_number",userPageQueryDTO.getIdNumber())
                        .like(StringUtils.isNotEmpty(userPageQueryDTO.getStuNumber()),"stu_number",userPageQueryDTO.getStuNumber())
                        .like(StringUtils.isNotEmpty(userPageQueryDTO.getPhone()),"phone",userPageQueryDTO.getPhone())
                        .orderByDesc("create_time")
        );
    }

    /**
     * 启用或禁用学生状态
     *
     * @param status
     * @param id
     */
    public void updateOrStop(Integer status, Long id) {
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Users user = Users.builder()
                .status(status)
                .build();
        userMapper.update(user,updateWrapper);
    }
}
