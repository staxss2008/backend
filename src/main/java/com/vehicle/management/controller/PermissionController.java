
package com.vehicle.management.controller;

import com.vehicle.management.annotation.RequirePermission;
import com.vehicle.management.service.PermissionService;
import com.vehicle.management.util.JwtUtil;
import com.vehicle.management.util.Result;
import com.vehicle.management.vo.PermissionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器
 */
@RestController
@RequestMapping("/api/permission")
@Api(tags = "权限管理")
@RequirePermission(roles = {"ADMIN"})
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user")
    @ApiOperation("获取当前用户的权限")
    @RequirePermission(roles = {"ADMIN", "MANAGER", "USER"})
    public Result<List<PermissionVO>> getUserPermissions(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Long userId = jwtUtil.getUserIdFromToken(token);
        List<PermissionVO> permissions = permissionService.getUserPermissions(userId);
        return Result.success(permissions);
    }

    @GetMapping("/role/{roleId}")
    @ApiOperation("获取角色的权限")
    public Result<List<PermissionVO>> getRolePermissions(@PathVariable Long roleId) {
        List<PermissionVO> permissions = permissionService.getRolePermissions(roleId);
        return Result.success(permissions);
    }

    @GetMapping("/tree")
    @ApiOperation("获取所有权限树")
    public Result<List<PermissionVO>> getAllPermissionsTree() {
        List<PermissionVO> permissions = permissionService.getAllPermissionsTree();
        return Result.success(permissions);
    }

    @PostMapping("/assign")
    @ApiOperation("分配权限给角色")
    public Result<Void> assignPermissionsToRole(@RequestParam Long roleId,
                                             @RequestBody List<Long> permissionIds) {
        permissionService.assignPermissionsToRole(roleId, permissionIds);
        return Result.success();
    }
}
