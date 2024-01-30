package com.cheese.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cheese.dto.RiderPageQueryDTO;
import com.cheese.entity.Rider;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 13:29
 * @projectName cheese-takeaway
 */
public interface RiderService extends IService<Rider>
{
    /**
     * 骑手分页查询
     * @param riderPageQueryDTO
     * @return
     */
    IPage<Rider> page(RiderPageQueryDTO riderPageQueryDTO);

    /**
     * 启用或禁用骑手状态
     *
     * @param status
     * @param id
     */
    void updateOrStop(Integer status, Long id);
}
