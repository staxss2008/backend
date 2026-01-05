package com.vehicle.management.service;

import com.vehicle.management.dto.VehicleDTO;
import com.vehicle.management.vo.VehicleVO;
import com.vehicle.management.vo.PageResult;

/**
 * 车辆服务接口
 */
public interface VehicleService {
    
    /**
     * 获取车辆列表
     * @param page 页码
     * @param size 每页大小
     * @param plateNo 车牌号
     * @param status 状态
     * @return 车辆分页结果
     */
    PageResult<VehicleVO> getVehicleList(Integer page, Integer size, String plateNo, String status);
    
    /**
     * 新增车辆
     * @param vehicleDTO 车辆信息
     * @return 车辆ID
     */
    Long addVehicle(VehicleDTO vehicleDTO);
    
    /**
     * 更新车辆信息
     * @param id 车辆ID
     * @param vehicleDTO 车辆信息
     */
    void updateVehicle(Long id, VehicleDTO vehicleDTO);
    
    /**
     * 更新车辆状态
     * @param id 车辆ID
     * @param status 状态
     */
    void updateStatus(Long id, String status);
    
    /**
     * 获取车辆统计信息
     * @return 车辆统计信息
     */
    VehicleVO getVehicleStats();

    /**
     * 同步维修记录：为状态为维修中但没有维修记录的车辆创建维修记录
     */
    void syncMaintenanceRecords();

    /**
     * 删除车辆
     * @param id 车辆ID
     */
    void deleteVehicle(Long id);
}