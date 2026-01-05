
package com.vehicle.management.service;

import com.vehicle.management.dto.RoleDTO;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;

import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {

    /**
     * 获取角色列表
     * @param page 页码
     * @param size 每页大小
     * @param roleName 角色名称
     * @param roleCode 角色编码
     * @param status 状态
     * @return 角色分页结果
     */
    PageResult<RoleVO> getRoleList(Integer page, Integer size, String roleName, String roleCode, String status);

    /**
     * 获取所有角色（不分页）
     * @return 角色列表
     */
    List<RoleVO> getAllRoles();

    /**
     * 新增角色
     * @param roleDTO 角色信息
     * @return 角色ID
     */
    Long addRole(RoleDTO roleDTO);

    /**
     * 更新角色信息
     * @param id 角色ID
     * @param roleDTO 角色信息
     */
    void updateRole(Long id, RoleDTO roleDTO);

    /**
     * 更新角色状态
     * @param id 角色ID
     * @param status 状态
     */
    void updateRoleStatus(Long id, String status);

    /**
     * 删除角色
     * @param id 角色ID
     */
    void deleteRole(Long id);
}
