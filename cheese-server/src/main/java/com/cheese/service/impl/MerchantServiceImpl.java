package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.constant.*;
import com.cheese.context.BaseContext;
import com.cheese.dto.MerchantDTO;
import com.cheese.dto.MerchantLoginDTO;
import com.cheese.dto.MerchantPageQueryDTO;
import com.cheese.entity.Merchant;
import com.cheese.exception.*;
import com.cheese.mapper.MerchantMapper;
import com.cheese.service.MerchantService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerchantService
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


    /**
     * 商户分页查询
     * @param merchantPageQueryDTO
     * @return
     */
    @Override
    public IPage<Merchant> pageQuery(MerchantPageQueryDTO merchantPageQueryDTO)
    {
        String content = merchantPageQueryDTO.getContent();
        return this.page(
                new Page<>(merchantPageQueryDTO.getPage(),
                        merchantPageQueryDTO.getPageSize()),
                new QueryWrapper<Merchant>().lambda()
                    .like(StringUtils.isNotEmpty(content), Merchant::getName, content)
                    .or().like(StringUtils.isNotEmpty(content), Merchant::getIdNumber, content)
                    .or().like(StringUtils.isNotEmpty(content), Merchant::getPhone, content)
                    .or().like(StringUtils.isNotEmpty(content), Merchant::getAddress, content)
                    .orderByDesc(Merchant::getCreateTime));

    }

    /**
     * 启用或禁用商户状态
     *
     * @param status
     * @param id
     */
    @Override
    public void updateOrStop(Integer status, Long id)
    {
        LambdaUpdateWrapper<Merchant> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Merchant::getId, id);
        Merchant merchant = Merchant.builder()
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        merchantMapper.update(merchant, updateWrapper);
    }
}
