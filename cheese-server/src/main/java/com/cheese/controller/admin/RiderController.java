package com.cheese.controller.admin;

import com.cheese.constant.StatusConstant;
import com.cheese.dto.RiderPageQueryDTO;
import com.cheese.dto.RiderDTO;
import com.cheese.entity.Merchant;
import com.cheese.entity.Rider;
import com.cheese.result.Result;
import com.cheese.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/30 13:43
 * @projectName cheese-takeaway
 */
@RestController
@RequestMapping("/admin/rider")
@Slf4j
@Tag(name = "配送员相关接口")
public class RiderController
{
    @Autowired
    private RiderService riderService;

    @GetMapping("/page")
    @Operation(summary = "配送员分页查询")
    public Result page(RiderPageQueryDTO riderPageQueryDTO){
        return Result.success(riderService.page(riderPageQueryDTO));
    }

    /**
     * 启用或禁用配送员账号
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary ="修改状态")
    public Result updateOrStop(@PathVariable Integer status, Long id) {
        log.info("启用或禁用配送员状态:{},{}", status, id);
        riderService.updateOrStop(status, id);
        return Result.success();
    }

    /**
     * 更新配送员信息
     *
     * @param riderDTO
     * @return
     */
    @PutMapping
    @Operation(summary ="更新配送员")
    public Result update(@RequestBody RiderDTO riderDTO) {
        Rider rider = new Rider();
        BeanUtils.copyProperties(riderDTO, rider);
        riderService.updateById(rider);
        return Result.success();
    }
    /**
     * 新增配送员
     *
     * @param riderDTO
     * @return
     */
    @PostMapping
    @Operation(summary ="新增配送员")
    public Result save(@RequestBody RiderDTO riderDTO) {
        Rider rider = new Rider();
        BeanUtils.copyProperties(riderDTO, rider);
        rider.setStatus(StatusConstant.ENABLE);
        riderService.save(rider);
        return Result.success();
    }

    /**
     * 删除配送员
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary ="删除配送员")
    public Result deleteById(Long id) {
        riderService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询配送员信息")
    public Result<Rider> getById(@PathVariable Long id)
    {
        log.info("根据id查询商户信息，{}", id);
        Rider rider = riderService.getById(id);
        // 防止密码泄露
        rider.setPassword("*****");
        return Result.success(rider);
    }
}
