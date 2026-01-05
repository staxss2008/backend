package com.vehicle.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 驾驶员实体类
 */
@Data
@TableName("driver")
public class Driver {
    
    @TableId(type = IdType.AUTO)
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