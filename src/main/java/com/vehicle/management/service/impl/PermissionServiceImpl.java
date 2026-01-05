
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vehicle.management.entity.Permission;
import com.vehicle.management.entity.RolePermission;
import com.vehicle.management.entity.UserRole;
import com.vehicle.management.mapper.PermissionMapper;
import com.vehicle.management.mapper.RolePermissionMapper;
import com.vehicle.management.mapper.UserRoleMapper;
import com.vehicle.management.service.PermissionService;
import com.vehicle.management.vo.PermissionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限服务实现类
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper,
                                RolePermissionMapper rolePermissionMapper,
                                UserRoleMapper userRoleMapper) {
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<PermissionVO> getUserPermissions(Long userId) {
        // 查询用户的角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);

        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        // 查询角色的权限关联
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.in("role_id", roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);

        if (rolePermissions.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("id", permissionIds);
        permissionQueryWrapper.eq("status", "ACTIVE");
        permissionQueryWrapper.orderByAsc("sort_order");
        List<Permission> permissions = permissionMapper.selectList(permissionQueryWrapper);

        // 转换为VO
        return permissions.stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            return permissionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getRolePermissions(Long roleId) {
        // 查询角色的权限关联
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.eq("role_id", roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);

        if (rolePermissions.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());

        // 查询权限信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("id", permissionIds);
        permissionQueryWrapper.eq("status", "ACTIVE");
        permissionQueryWrapper.orderByAsc("sort_order");
        List<Permission> permissions = permissionMapper.selectList(permissionQueryWrapper);

        // 转换为VO
        return permissions.stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            return permissionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getAllPermissionsTree() {
        // 查询所有权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "ACTIVE");
        queryWrapper.orderByAsc("sort_order");
        List<Permission> permissions = permissionMapper.selectList(queryWrapper);

        // 转换为VO
        List<PermissionVO> permissionVOList = permissions.stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            return permissionVO;
        }).collect(Collectors.toList());

        // 构建树形结构
        return buildPermissionTree(permissionVOList, 0L);
    }

    /**
     * 构建权限树
     * @param permissions 权限列表
     * @param parentId 父权限ID
     * @return 权限树
     */
    private List<PermissionVO> buildPermissionTree(List<PermissionVO> permissions, Long parentId) {
        List<PermissionVO> tree = new ArrayList<>();

        for (PermissionVO permission : permissions) {
            if (permission.getParentId() != null && permission.getParentId().equals(parentId)) {
                List<PermissionVO> children = buildPermissionTree(permissions, permission.getId());
                permission.setChildren(children);
                tree.add(permission);
            }
        }

        return tree;
    }

    @Override
    @Transactional
    public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        // 先删除角色的所有权限关联
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RolePermission> existingPermissions = rolePermissionMapper.selectList(queryWrapper);
        for (RolePermission rolePermission : existingPermissions) {
            rolePermissionMapper.deleteById(rolePermission.getId());
        }

        // 添加新的权限关联
        if (permissionIds != null && !permissionIds.isEmpty()) {
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermission.setCreatedAt(java.time.LocalDateTime.now());
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }
}
