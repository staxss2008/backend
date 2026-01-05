
package com.vehicle.management.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限VO类
 */
@Data
public class PermissionVO {

    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限类型：MENU-菜单，BUTTON-按钮
     */
    private String permissionType;

    /**
     * 父权限ID
     */
    private Long parentId;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 状态：ACTIVE-激活，DISABLED-禁用
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 子权限列表
     */
    private List<PermissionVO> children;
}
