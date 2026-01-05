package com.vehicle.management.vo;

import lombok.Data;

/**
 * 车辆位置视图对象
 */
@Data
public class VehicleLocationVO {
    
    /**
     * 车辆ID
     */
    private Long vehicleId;
    
    /**
     * 车牌号
     */
    private String plateNo;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 经度
     */
    private Double longitude;
    
    /**
     * 纬度
     */
    private Double latitude;
    
    /**
     * 驾驶员姓名
     */
    private String driverName;
}