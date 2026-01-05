
package com.vehicle.management.controller;

import com.vehicle.management.dto.LoginDTO;
import com.vehicle.management.service.AuthService;
import com.vehicle.management.service.UserService;
import com.vehicle.management.util.JwtUtil;
import com.vehicle.management.util.Result;
import com.vehicle.management.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@Api(tags = "认证管理")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthService authService,
                      JwtUtil jwtUtil,
                      UserService userService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success(loginVO);
    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result<Void> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        authService.logout(token);
        return Result.success();
    }

    @GetMapping("/current")
    @ApiOperation("获取当前登录用户信息")
    public Result<LoginVO> getCurrentUser(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        LoginVO loginVO = authService.getCurrentUser(token);
        return Result.success(loginVO);
    }

    @PutMapping("/update-current")
    @ApiOperation("更新当前用户信息")
    public Result<Void> updateCurrentUser(@RequestHeader("Authorization") String token,
                                         @Valid @RequestBody com.vehicle.management.dto.UserDTO userDTO) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Long userId = jwtUtil.getUserIdFromToken(token);
        userService.updateUser(userId, userDTO);
        return Result.success();
    }
}
