package com.vehicle.management.vo;

import lombok.Data;

/**
 * 智能调度查询条件
 */
@Data
public class SmartDispatchQuery {
    
    /**
     * 申请ID
     */
    private Long applicationId;
    
    /**
     * 出发地点
     */
    private String startLocation;
    
    /**
     * 目的地
     */
    private String endLocation;
    
    /**
     * 需要座位数
     */
    private Integer requiredSeats;
    
    /**
     * 车辆类型要求
     */
    private String vehicleType;
}