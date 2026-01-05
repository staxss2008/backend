
package com.vehicle.management.service;

import com.vehicle.management.dto.TripApplicationDTO;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.vo.TripApplicationVO;
import com.vehicle.management.vo.VehicleVO;

import java.util.List;

/**
 * 用车申请服务接口
 */
public interface TripApplicationService {

    /**
     * 获取用车申请列表
     * @param page 页码
     * @param size 每页大小
     * @param status 状态
     * @return 分页结果
     */
    PageResult<TripApplicationVO> getApplicationList(Integer page, Integer size, String status);

    /**
     * 创建用车申请
     * @param applicationDTO 用车申请DTO
     * @return 申请ID
     */
    Long createApplication(TripApplicationDTO applicationDTO);

    /**
     * 获取申请详情
     * @param id 申请ID
     * @return 申请详情
     */
    TripApplicationVO getApplicationDetail(Long id);

    /**
     * 批准申请
     * @param id 申请ID
     * @param remark 备注
     */
    void approveApplication(Long id, String remark);

    /**
     * 拒绝申请
     * @param id 申请ID
     * @param remark 拒绝理由
     */
    void rejectApplication(Long id, String remark);

    /**
     * 指派车辆
     * @param id 申请ID
     * @param vehicleId 车辆ID
     * @param driverId 驾驶员ID
     * @param dispatcherId 调度员ID
     */
    void assignVehicle(Long id, Long vehicleId, Long driverId, Long dispatcherId);

    /**
     * 完成申请
     * @param id 申请ID
     */
    void completeApplication(Long id);

    /**
     * 获取可用的驾驶员列表
     * @return 可用的驾驶员列表
     */
    List<DriverVO> getAvailableDrivers();

    /**
     * 获取可用的车辆列表
     * @return 可用的车辆列表
     */
    List<VehicleVO> getAvailableVehicles();
}
