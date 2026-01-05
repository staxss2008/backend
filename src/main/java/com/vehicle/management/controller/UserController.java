
package com.vehicle.management.controller;

import com.vehicle.management.annotation.RequirePermission;
import com.vehicle.management.dto.UserDTO;
import com.vehicle.management.service.UserService;
import com.vehicle.management.vo.UserVO;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
@RequirePermission(roles = {"ADMIN", "MANAGER"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public Result<PageResult<UserVO>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String realName,
            @RequestParam(required = false) String status) {
        PageResult<UserVO> result = userService.getUserList(page, size, username, realName, status);
        return Result.success(result);
    }

    @PostMapping("/add")
    @ApiOperation("新增用户")
    public Result<Long> addUser(@Valid @RequestBody UserDTO userDTO) {
        Long userId = userService.addUser(userDTO);
        return Result.success(userId);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("更新用户信息")
    public Result<Void> updateUser(@PathVariable Long id,
                                  @Valid @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return Result.success();
    }

    @PutMapping("/update-status/{id}")
    @ApiOperation("更新用户状态")
    public Result<Void> updateUserStatus(@PathVariable Long id,
                                         @RequestParam String status) {
        userService.updateUserStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除用户")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @PostMapping("/assign-roles")
    @ApiOperation("分配角色给用户")
    public Result<Void> assignRoles(@RequestBody java.util.Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        @SuppressWarnings("unchecked")
        List<Long> roleIds = (List<Long>) params.get("roleIds");
        userService.assignRoles(userId, roleIds);
        return Result.success();
    }

    @GetMapping("/roles/{userId}")
    @ApiOperation("获取用户的角色列表")
    public Result<List<RoleVO>> getUserRoles(@PathVariable Long userId) {
        List<RoleVO> roles = userService.getUserRoles(userId);
        return Result.success(roles);
    }

    @PostMapping("/change-password")
    @ApiOperation("修改密码")
    public Result<Void> changePassword(@RequestBody com.vehicle.management.dto.ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        return Result.success();
    }
}
