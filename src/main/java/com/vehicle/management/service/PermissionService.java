
package com.vehicle.management.service;

import com.vehicle.management.vo.PermissionVO;

import java.util.List;

/**
 * 权限服务接口
 */
public interface PermissionService {

    /**
     * 获取用户的所有权限
     * @param userId 用户ID
     * @return 权限列表
     */
    List<PermissionVO> getUserPermissions(Long userId);

    /**
     * 获取角色的所有权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<PermissionVO> getRolePermissions(Long roleId);

    /**
     * 获取所有权限（树形结构）
     * @return 权限树
     */
    List<PermissionVO> getAllPermissionsTree();

    /**
     * 分配权限给角色
     * @param roleId 角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);
}
