package com.vehicle.management.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 驾驶员视图对象
 */
@Data
public class DriverVO {
    
    private Long id;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 工号
     */
    private String employeeNo;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 驾驶证号
     */
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
     * 状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}