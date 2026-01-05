package com.vehicle.management.service;

import com.vehicle.management.dto.DriverDTO;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;

/**
 * 驾驶员服务接口
 */
public interface DriverService {
    
    /**
     * 获取驾驶员列表
     * @param page 页码
     * @param size 每页大小
     * @param name 姓名
     * @param status 状态
     * @return 驾驶员分页结果
     */
    PageResult<DriverVO> getDriverList(Integer page, Integer size, String name, String status);
    
    /**
     * 新增驾驶员
     * @param driverDTO 驾驶员信息
     * @return 驾驶员ID
     */
    Long addDriver(DriverDTO driverDTO);
    
    /**
     * 更新驾驶员信息
     * @param id 驾驶员ID
     * @param driverDTO 驾驶员信息
     */
    void updateDriver(Long id, DriverDTO driverDTO);
    
    /**
     * 删除驾驶员
     * @param id 驾驶员ID
     */
    void deleteDriver(Long id);
    
    /**
     * 更新驾驶员状态
     * @param id 驾驶员ID
     * @param status 状态
     */
    void updateStatus(Long id, String status);
}