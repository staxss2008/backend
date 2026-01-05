
package com.vehicle.management.service;

import com.vehicle.management.dto.UserDTO;
import com.vehicle.management.vo.UserVO;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 获取用户列表
     * @param page 页码
     * @param size 每页大小
     * @param username 用户名
     * @param realName 真实姓名
     * @param status 状态
     * @return 用户分页结果
     */
    PageResult<UserVO> getUserList(Integer page, Integer size, String username, String realName, String status);

    /**
     * 新增用户
     * @param userDTO 用户信息
     * @return 用户ID
     */
    Long addUser(UserDTO userDTO);

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param userDTO 用户信息
     */
    void updateUser(Long id, UserDTO userDTO);

    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态
     */
    void updateUserStatus(Long id, String status);

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 分配角色给用户
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户的角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleVO> getUserRoles(Long userId);

    /**
     * 修改密码
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
    void changePassword(String oldPassword, String newPassword);
}
