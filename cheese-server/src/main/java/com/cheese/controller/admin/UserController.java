package com.cheese.controller.admin;

import com.cheese.constant.JwtClaimsConstant;
import com.cheese.dto.UserDTO;
import com.cheese.dto.UserLoginDTO;
import com.cheese.dto.UserPageQueryDTO;
import com.cheese.entity.Users;
import com.cheese.properties.JwtProperties;
import com.cheese.result.Result;
import com.cheese.service.UserService;
import com.cheese.utils.JwtUtil;
import com.cheese.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WEI CHEN GUANG
 * @date 2024/1/29 22:51
 * @projectName cheese-takeaway
 */
@RestController("adminUserController")
@RequestMapping("/admin/user")
@Slf4j
@Tag(name = "学生相关接口")
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
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
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信用户登录：{}",userLoginDTO.getCode());

        //微信登录
        Users user = userService.login(userLoginDTO);//后绪步骤实现

        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
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
