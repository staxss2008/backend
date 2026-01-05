
package com.vehicle.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改密码DTO
 */
@Data
public class ChangePasswordDTO {

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
