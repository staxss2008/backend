
package com.vehicle.management.service;

import com.vehicle.management.dto.MaintenanceRecordDTO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.vo.MaintenanceRecordVO;

/**
 * 车辆维修保养记录服务接口
 */
public interface MaintenanceRecordService {

    /**
     * 获取维修保养记录列表
     * @param page 页码
     * @param size 每页大小
     * @param vehicleId 车辆ID
     * @param type 类型
     * @return 分页结果
     */
    PageResult<MaintenanceRecordVO> getMaintenanceRecordList(Integer page, Integer size, Long vehicleId, String type);

    /**
     * 创建维修保养记录
     * @param recordDTO 维修保养记录DTO
     * @return 记录ID
     */
    Long createMaintenanceRecord(MaintenanceRecordDTO recordDTO);

    /**
     * 获取记录详情
     * @param id 记录ID
     * @return 记录详情
     */
    MaintenanceRecordVO getMaintenanceRecordDetail(Long id);

    /**
     * 更新维修保养记录
     * @param id 记录ID
     * @param recordDTO 维修保养记录DTO
     */
    void updateMaintenanceRecord(Long id, MaintenanceRecordDTO recordDTO);

    /**
     * 删除维修保养记录
     * @param id 记录ID
     */
    void deleteMaintenanceRecord(Long id);
}
