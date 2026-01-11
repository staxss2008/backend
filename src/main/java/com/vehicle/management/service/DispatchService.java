package com.vehicle.management.service;

import com.vehicle.management.dto.DispatchOrderDTO;
import com.vehicle.management.entity.DispatchOrder;
import com.vehicle.management.vo.DispatchMonitorVO;
import com.vehicle.management.vo.SmartDispatchResultVO;
import com.vehicle.management.vo.SmartDispatchQuery;
import com.vehicle.management.vo.VehicleLocationVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 调度服务接口
 */
public interface DispatchService {
    
    /**
     * 获取监控数据
     * @return 监控数据
     */
    DispatchMonitorVO getMonitorData();
    
    /**
     * 创建调度单
     * @param orderDTO 调度单数据
     * @return 调度单号
     */
    String createDispatchOrder(DispatchOrderDTO orderDTO);
    
    /**
     * 智能调度推荐
     * @param query 查询条件
     * @return 推荐结果
     */
    SmartDispatchResultVO smartDispatch(SmartDispatchQuery query);
    
    /**
     * 获取实时位置
     * @return 车辆位置列表
     */
    List<VehicleLocationVO> getRealTimeLocation();

    /**
     * 更新开始里程（出车时填写）
     * @param orderId 调度单ID
     * @param startMileage 开始里程
     * @return 是否成功
     */
    boolean updateStartMileage(Long orderId, BigDecimal startMileage);

    /**
     * 更新结束里程（收车时填写）
     * @param orderId 调度单ID
     * @param endMileage 结束里程
     * @return 是否成功
     */
    boolean updateEndMileage(Long orderId, BigDecimal endMileage);

    /**
     * 获取调度列表（关联用车申请）
     * @return 调度列表
     */
    List<DispatchOrder> getDispatchList();
}