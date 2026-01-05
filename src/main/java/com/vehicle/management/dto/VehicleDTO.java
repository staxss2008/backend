package com.vehicle.management.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * 车辆数据传输对象
 */
@Data
public class VehicleDTO {
    
    /**
     * 车牌号
     */
    @NotBlank(message = "车牌号不能为空")
    private String plateNo;
    
    /**
     * 车辆类型
     */
    @NotBlank(message = "车辆类型不能为空")
    private String vehicleType;
    
    /**
     * 品牌
     */
    private String brand;
    
    /**
     * 型号
     */
    private String model;
    
    /**
     * 颜色
     */
    private String color;
    
    /**
     * 座位数
     */
    private Integer seats;
    
    /**
     * 燃料类型
     */
    private String fuelType;
    
    /**
     * 购置日期
     */
    private LocalDate buyDate;
    
    /**
     * GPS设备ID
     */
    private String gpsDeviceId;
}