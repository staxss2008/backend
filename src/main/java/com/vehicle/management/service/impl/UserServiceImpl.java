
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.UserDTO;
import com.vehicle.management.entity.User;
import com.vehicle.management.entity.UserRole;
import com.vehicle.management.entity.Role;
import com.vehicle.management.mapper.UserMapper;
import com.vehicle.management.mapper.UserRoleMapper;
import com.vehicle.management.mapper.RoleMapper;
import com.vehicle.management.service.UserService;
import com.vehicle.management.vo.UserVO;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;

    public UserServiceImpl(UserMapper userMapper,
                          UserRoleMapper userRoleMapper,
                          RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public PageResult<UserVO> getUserList(Integer page, Integer size, String username, String realName, String status) {
        Page<User> userPage = new Page<>(page, size);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            queryWrapper.like("username", username);
        }
        if (realName != null && !realName.isEmpty()) {
            queryWrapper.like("real_name", realName);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }

        Page<User> resultPage = userMapper.selectPage(userPage, queryWrapper);

        // 将实体转换为VO
        List<UserVO> userVOList = resultPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());

        // 构建分页结果
        PageResult<UserVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(userVOList);

        return pageResult;
    }

    @Override
    @Transactional
    public Long addUser(UserDTO userDTO) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User existingUser = userMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setStatus("ACTIVE"); // 默认状态为激活
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userMapper.insert(user);

        // 分配角色
        if (userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), userDTO.getRoleIds());
        }

        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO) {
        logger.debug("=== updateUser start ===");
        logger.debug("userId: {}", id);
        logger.debug("userDTO: {}", userDTO);
        
        User user = userMapper.selectById(id);
        logger.debug("user before update: {}", user);
        
        if (user != null) {
            BeanUtils.copyProperties(userDTO, user);
            user.setUpdatedAt(LocalDateTime.now());
            logger.debug("user after copy: {}", user);
            
            int result = userMapper.updateById(user);
            logger.debug("updateById result: {}", result);

            // 更新角色
            if (userDTO.getRoleIds() != null) {
                assignRoles(id, userDTO.getRoleIds());
            }
        }
        logger.debug("=== updateUser end ===");
    }

    @Override
    public void updateUserStatus(Long id, String status) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setStatus(status);
            user.setUpdatedAt(LocalDateTime.now());
            userMapper.updateById(user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 删除用户前，先删除该用户的角色关联
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        for (UserRole userRole : userRoles) {
            userRoleMapper.deleteById(userRole.getId());
        }

        // 删除用户
        userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 先删除用户的所有角色关联
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRole> existingRoles = userRoleMapper.selectList(queryWrapper);
        for (UserRole userRole : existingRoles) {
            userRoleMapper.deleteById(userRole.getId());
        }

        // 添加新的角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Object roleIdObj : roleIds) {
                Long roleId = roleIdObj instanceof Long ? (Long) roleIdObj : Long.valueOf(roleIdObj.toString());
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreatedAt(LocalDateTime.now());
                userRoleMapper.insert(userRole);
            }
        }
    }

    @Override
    public List<RoleVO> getUserRoles(Long userId) {
        // 查询用户的角色关联
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);

        // 查询角色信息
        if (userRoles.isEmpty()) {
            return List.of();
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("id", roleIds);
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);

        // 转换为VO
        return roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        // 获取当前登录用户
        String username = com.vehicle.management.util.SecurityUtil.getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("未登录");
        }

        // 查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证原密码
        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }

        // 更新密码
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
    }
}
