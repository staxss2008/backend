
package com.vehicle.management.controller;

import com.vehicle.management.annotation.RequirePermission;
import com.vehicle.management.dto.RoleDTO;
import com.vehicle.management.service.RoleService;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 角色管理控制器
 */
@RestController
@RequestMapping("/api/role")
@Api(tags = "角色管理")
@RequirePermission(roles = {"ADMIN"})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation("获取角色列表")
    public Result<PageResult<RoleVO>> getRoleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String roleCode,
            @RequestParam(required = false) String status) {
        PageResult<RoleVO> result = roleService.getRoleList(page, size, roleName, roleCode, status);
        return Result.success(result);
    }

    @GetMapping("/all")
    @ApiOperation("获取所有角色（不分页）")
    public Result<List<RoleVO>> getAllRoles() {
        List<RoleVO> roles = roleService.getAllRoles();
        return Result.success(roles);
    }

    @PostMapping("/add")
    @ApiOperation("新增角色")
    public Result<Long> addRole(@Valid @RequestBody RoleDTO roleDTO) {
        Long roleId = roleService.addRole(roleDTO);
        return Result.success(roleId);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("更新角色信息")
    public Result<Void> updateRole(@PathVariable Long id,
                                    @Valid @RequestBody RoleDTO roleDTO) {
        roleService.updateRole(id, roleDTO);
        return Result.success();
    }

    @PutMapping("/update-status/{id}")
    @ApiOperation("更新角色状态")
    public Result<Void> updateRoleStatus(@PathVariable Long id,
                                          @RequestParam String status) {
        roleService.updateRoleStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除角色")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return Result.success();
    }
}
