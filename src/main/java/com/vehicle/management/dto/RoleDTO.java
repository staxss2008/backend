
package com.vehicle.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 角色DTO类
 */
@Data
public class RoleDTO {

    /**
     * 角色ID（更新时使用）
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态：ACTIVE-激活，DISABLED-禁用
     */
    private String status;
}
