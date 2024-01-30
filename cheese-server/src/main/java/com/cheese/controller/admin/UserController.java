package com.cheese.controller.admin;

import com.cheese.dto.UserDTO;
import com.cheese.dto.UserPageQueryDTO;
import com.cheese.entity.Users;
import com.cheese.result.Result;
import com.cheese.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/29 22:51
 * @projectName cheese-takeaway
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
@Tag(name = "学生相关接口")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/page")
    @Operation(summary = "学生分页查询")
    public Result page(UserPageQueryDTO userPageQueryDTO){
        return Result.success(userService.page(userPageQueryDTO));
    }

    /**
     * 启用或禁用学生账号
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @Operation(summary ="修改状态")
    public Result updateOrStop(@PathVariable Integer status, Long id) {
        log.info("启用或禁用学生状态:{},{}", status, id);
        userService.updateOrStop(status, id);
        return Result.success();
    }

    /**
     * 更新学生信息
     *
     * @param userDTO
     * @return
     */
    @PutMapping
    @Operation(summary ="更新学生")
    public Result update(@RequestBody UserDTO userDTO) {
        Users user = new Users();
        BeanUtils.copyProperties(userDTO, user);
        userService.updateById(user);
        return Result.success();
    }
//    /**
//     * 新增学生
//     *
//     * @param userDTO
//     * @return
//     */
////    @PostMapping
////    @Operation(summary ="新增学生")
////    public Result save(@RequestBody UserDTO userDTO) {
////        Users user = new Users();
////        BeanUtils.copyProperties(userDTO, user);
////        user.setStatus(StatusConstant.ENABLE);
////        userService.save(user);
////        return Result.success();
////    }

    /**
     * 删除学生
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @Operation(summary ="删除学生")
    public Result deleteById(Long id) {
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询配送员信息")
    public Result<Users> getById(@PathVariable Long id)
    {
        log.info("根据id查询商户信息，{}", id);
        Users user = userService.getById(id);
        return Result.success(user);
    }
}
