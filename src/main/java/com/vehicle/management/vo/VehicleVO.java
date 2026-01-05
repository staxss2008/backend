package com.vehicle.management.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 车辆视图对象
 */
public class VehicleVO {
    
    private Long id;
    
    /**
     * 车牌号
     */
    private String plateNo;
    
    /**
     * 车辆类型
     */
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
     * 状态：IDLE-空闲,BUSY-使用中,MAINTENANCE-维修
     */
    private String status;
    
    /**
     * GPS设备ID
     */
    private String gpsDeviceId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    // 统计信息字段
    private Long totalVehicles;
    private Long idleVehicles;
    private Long busyVehicles;
    private Long offlineVehicles;
    private Long maintenanceVehicles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGpsDeviceId() {
        return gpsDeviceId;
    }

    public void setGpsDeviceId(String gpsDeviceId) {
        this.gpsDeviceId = gpsDeviceId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(Long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public Long getIdleVehicles() {
        return idleVehicles;
    }

    public void setIdleVehicles(Long idleVehicles) {
        this.idleVehicles = idleVehicles;
    }

    public Long getBusyVehicles() {
        return busyVehicles;
    }

    public void setBusyVehicles(Long busyVehicles) {
        this.busyVehicles = busyVehicles;
    }

    public Long getOfflineVehicles() {
        return offlineVehicles;
    }

    public void setOfflineVehicles(Long offlineVehicles) {
        this.offlineVehicles = offlineVehicles;
    }

    public Long getMaintenanceVehicles() {
        return maintenanceVehicles;
    }

    public void setMaintenanceVehicles(Long maintenanceVehicles) {
        this.maintenanceVehicles = maintenanceVehicles;
    }
}