package com.vehicle.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 调度单实体类
 */
@Data
@TableName("dispatch_order")
public class DispatchOrder {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 调度单号
     */
    private String orderNo;
    
    /**
     * 关联申请ID
     */
    private Long applicationId;
    
    /**
     * 车辆ID
     */
    private Long vehicleId;
    
    /**
     * 驾驶员ID
     */
    private Long driverId;
    
    /**
     * 调度员ID
     */
    private Long dispatcherId;
    
    /**
     * 调度时间
     */
    private LocalDateTime dispatchTime;
    
    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;
    
    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;
    
    /**
     * 计划开始里程
     */
    private BigDecimal planStartMileage;
    
    /**
     * 计划结束里程
     */
    private BigDecimal planEndMileage;
    
    /**
     * 实际开始里程
     */
    private BigDecimal actualStartMileage;
    
    /**
     * 实际结束里程
     */
    private BigDecimal actualEndMileage;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}