
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vehicle.management.dto.LoginDTO;
import com.vehicle.management.entity.*;
import com.vehicle.management.mapper.*;
import com.vehicle.management.service.AuthService;
import com.vehicle.management.util.JwtUtil;
import com.vehicle.management.vo.LoginVO;
import com.vehicle.management.vo.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 认证服务实现类
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthServiceImpl(UserMapper userMapper,
                          UserRoleMapper userRoleMapper,
                          RoleMapper roleMapper,
                          RolePermissionMapper rolePermissionMapper,
                          PermissionMapper permissionMapper,
                          JwtUtil jwtUtil,
                          @org.springframework.beans.factory.annotation.Autowired(required = false) RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 查询用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", loginDTO.getUsername());
        User user = userMapper.selectOne(userQueryWrapper);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (user.getPassword() == null || !user.getPassword().trim().equals(loginDTO.getPassword().trim())) {
            throw new RuntimeException("密码错误");
        }

        // 检查用户状态
        if (!"ACTIVE".equals(user.getStatus())) {
            throw new RuntimeException("用户已被禁用");
        }

        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        // 将Token存入Redis
        if (redisTemplate != null) {
            redisTemplate.opsForValue().set("token:" + user.getId(), token, 24, TimeUnit.HOURS);
        }

        // 获取用户角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);

        Set<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());

        // 获取角色信息
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("id", roleIds);
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);

        List<RoleVO> roleVOList = roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());

        // 获取权限ID
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.in("role_id", roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);

        Set<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());

        // 获取权限信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("id", permissionIds);
        List<Permission> permissions = permissionMapper.selectList(permissionQueryWrapper);

        List<String> permissionCodes = permissions.stream()
                .map(Permission::getPermissionCode)
                .collect(Collectors.toList());

        // 构建登录响应
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setEmail(user.getEmail());
        loginVO.setPhone(user.getPhone());
        loginVO.setDepartment(user.getDepartment());
        loginVO.setPosition(user.getPosition());
        loginVO.setToken(token);
        loginVO.setRoles(roleVOList);
        loginVO.setPermissions(permissionCodes);

        return loginVO;
    }

    @Override
    public void logout(String token) {
        try {
            Long userId = jwtUtil.getUserIdFromToken(token);
            // 从Redis中删除Token
            if (redisTemplate != null) {
                redisTemplate.delete("token:" + userId);
            }
        } catch (Exception e) {
            // 登出失败不影响前端操作，只记录日志
            logger.error("登出失败", e);
        }
    }

    @Override
    public LoginVO getCurrentUser(String token) {
        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token无效或已过期");
        }

        Long userId = jwtUtil.getUserIdFromToken(token);

        // 从Redis中获取Token
        if (redisTemplate != null) {
            String cachedToken = (String) redisTemplate.opsForValue().get("token:" + userId);
            if (cachedToken == null || !cachedToken.equals(token)) {
                throw new RuntimeException("Token无效或已过期");
            }
        }

        // 查询用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 获取用户角色
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id", user.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);

        Set<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());

        // 获取角色信息
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("id", roleIds);
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);

        List<RoleVO> roleVOList = roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());

        // 获取权限ID
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.in("role_id", roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rolePermissionQueryWrapper);

        Set<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());

        // 获取权限信息
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("id", permissionIds);
        List<Permission> permissions = permissionMapper.selectList(permissionQueryWrapper);

        List<String> permissionCodes = permissions.stream()
                .map(Permission::getPermissionCode)
                .collect(Collectors.toList());

        // 构建登录响应
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setRealName(user.getRealName());
        loginVO.setEmail(user.getEmail());
        loginVO.setPhone(user.getPhone());
        loginVO.setDepartment(user.getDepartment());
        loginVO.setPosition(user.getPosition());
        loginVO.setToken(token);
        loginVO.setRoles(roleVOList);
        loginVO.setPermissions(permissionCodes);

        return loginVO;
    }
}
