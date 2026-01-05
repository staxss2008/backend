package com.vehicle.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

/**
 * 驾驶员数据传输对象
 */
@Data
public class DriverDTO {
    
    /**
     * 姓名
     */
    @NotBlank(message = "驾驶员姓名不能为空")
    private String name;
    
    /**
     * 工号
     */
    private String employeeNo;
    
    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String phone;
    
    /**
     * 驾驶证号
     */
    @NotBlank(message = "驾驶证号不能为空")
    private String licenseNo;
    
    /**
     * 准驾车型
     */
    private String licenseType;
    
    /**
     * 驾照有效期
     */
    private LocalDate licenseExpire;
    
    /**
     * 驾驶员状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}