package com.vehicle.management.vo;

import lombok.Data;

/**
 * 智能调度结果视图对象
 */
@Data
public class SmartDispatchResultVO {
    
    /**
     * 推荐车辆ID
     */
    private Long recommendedVehicleId;
    
    /**
     * 推荐驾驶员ID
     */
    private Long recommendedDriverId;
    
    /**
     * 推荐理由
     */
    private String recommendationReason;
}