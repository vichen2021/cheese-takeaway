package com.cheese.controller.admin;

import com.cheese.constant.JwtClaimsConstant;
import com.cheese.dto.SystemUserLoginDTO;
import com.cheese.entity.SystemUser;
import com.cheese.properties.JwtProperties;
import com.cheese.result.Result;
import com.cheese.service.SystemUserService;
import com.cheese.utils.JwtUtil;
import com.cheese.vo.SystemUserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/systemUser")
@Slf4j
@Tag(name = "管理员相关接口")
public class SystemUserController
{
    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param systemUserLoginDTO
     * @return
     */
    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Result<SystemUserLoginVO> login(@RequestBody SystemUserLoginDTO systemUserLoginDTO) {
        log.info("管理员登录：{}",systemUserLoginDTO);

        SystemUser systemUser = systemUserService.login(systemUserLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, systemUser.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        SystemUserLoginVO systemUserLoginVO = SystemUserLoginVO.builder()
                .id(systemUser.getId())
                .userName(systemUser.getUsername())
                .name(systemUser.getName())
                .token(token)
                .build();

        return Result.success(systemUserLoginVO);
    }
}
