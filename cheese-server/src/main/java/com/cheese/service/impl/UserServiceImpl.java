package com.cheese.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.constant.MessageConstant;
import com.cheese.dto.UserLoginDTO;
import com.cheese.dto.UserPageQueryDTO;
import com.cheese.entity.Users;
import com.cheese.exception.LoginFailedException;
import com.cheese.mapper.UserMapper;
import com.cheese.properties.WeChatProperties;
import com.cheese.service.UserService;
import com.cheese.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wei Chen Guang
 * @ProjectName cheese-takeaway
 */

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {

    private static final String URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
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

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public Users login(UserLoginDTO userLoginDTO)
    {
        //获得当前微信用户的openid
        String openid = getOpenid(userLoginDTO.getCode());
        //判断当前openid是否为空，如果为空表示登录失败，抛出业务异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //判断当前用户是否为新用户
        Users users = userMapper.selectOne(new LambdaQueryWrapper<Users>().eq(Users::getOpenid, openid));
        //如果是新用户，自动完成注册
        if (users == null) {
            users = Users.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(users);
        }
        //返回这个对象
        return users;
    }

    /**
     * 调用微信接口服务，获取微信用户的openid
     *
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(URL, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
