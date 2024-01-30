package com.cheese.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheese.context.BaseContext;
import com.cheese.dto.RiderPageQueryDTO;
import com.cheese.entity.Rider;
import com.cheese.entity.Rider;
import com.cheese.mapper.RiderMapper;
import com.cheese.service.RiderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 13:30
 * @projectName cheese-takeaway
 */
@Service
public class RiderServiceImpl extends ServiceImpl<RiderMapper, Rider> implements RiderService
{
    @Autowired
    private RiderMapper riderMapper;
    /**
     * 骑手分页查询
     *
     * @param riderPageQueryDTO
     * @return
     */
    @Override
    public IPage<Rider> page(RiderPageQueryDTO riderPageQueryDTO)
    {
        return this.page(
                new Page<>(riderPageQueryDTO.getPage(),riderPageQueryDTO.getPageSize()),
                new QueryWrapper<Rider>()
                        .like(StringUtils.isNotEmpty(riderPageQueryDTO.getName()),"name",riderPageQueryDTO.getName())
                        .like(StringUtils.isNotEmpty(riderPageQueryDTO.getIdNumber()),"id_number",riderPageQueryDTO.getIdNumber())
                        .like(StringUtils.isNotEmpty(riderPageQueryDTO.getPhone()),"phone",riderPageQueryDTO.getPhone())
                        .orderByDesc("create_time")
        );
    }

    /**
     * 启用或禁用骑手状态
     *
     * @param status
     * @param id
     */
    public void updateOrStop(Integer status, Long id) {
        UpdateWrapper<Rider> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        Rider rider = Rider.builder()
                .status(status)
                .build();
        riderMapper.update(rider,updateWrapper);
    }
}
