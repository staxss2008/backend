
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.RoleDTO;
import com.vehicle.management.entity.Role;
import com.vehicle.management.mapper.RoleMapper;
import com.vehicle.management.service.RoleService;
import com.vehicle.management.vo.RoleVO;
import com.vehicle.management.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现类
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public PageResult<RoleVO> getRoleList(Integer page, Integer size, String roleName, String roleCode, String status) {
        Page<Role> rolePage = new Page<>(page, size);

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (roleName != null && !roleName.isEmpty()) {
            queryWrapper.like("role_name", roleName);
        }
        if (roleCode != null && !roleCode.isEmpty()) {
            queryWrapper.like("role_code", roleCode);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }

        Page<Role> resultPage = roleMapper.selectPage(rolePage, queryWrapper);

        // 将实体转换为VO
        List<RoleVO> roleVOList = resultPage.getRecords().stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());

        // 构建分页结果
        PageResult<RoleVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(roleVOList);

        return pageResult;
    }

    @Override
    public List<RoleVO> getAllRoles() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "ACTIVE");
        List<Role> roles = roleMapper.selectList(queryWrapper);

        return roles.stream().map(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            return roleVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Long addRole(RoleDTO roleDTO) {
        // 检查角色编码是否已存在
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", roleDTO.getRoleCode());
        Role existingRole = roleMapper.selectOne(queryWrapper);
        if (existingRole != null) {
            throw new RuntimeException("角色编码已存在");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        role.setStatus("ACTIVE"); // 默认状态为激活
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());

        roleMapper.insert(role);
        return role.getId();
    }

    @Override
    public void updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleMapper.selectById(id);
        if (role != null) {
            BeanUtils.copyProperties(roleDTO, role);
            role.setUpdatedAt(LocalDateTime.now());
            roleMapper.updateById(role);
        }
    }

    @Override
    public void updateRoleStatus(Long id, String status) {
        Role role = roleMapper.selectById(id);
        if (role != null) {
            role.setStatus(status);
            role.setUpdatedAt(LocalDateTime.now());
            roleMapper.updateById(role);
        }
    }

    @Override
    public void deleteRole(Long id) {
        roleMapper.deleteById(id);
    }
}
