package com.vehicle.management.vo;

import lombok.Data;

/**
 * 调度监控数据视图对象
 */
@Data
public class DispatchMonitorVO {
    
    /**
     * 总车辆数
     */
    private Long totalVehicles;
    
    /**
     * 空闲车辆数
     */
    private Long idleVehicles;
    
    /**
     * 使用中车辆数
     */
    private Long busyVehicles;
    
    /**
     * 离线车辆数
     */
    private Long offlineVehicles;
}