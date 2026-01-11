package com.vehicle.management.service.impl;

import com.vehicle.management.dto.DispatchOrderDTO;
import com.vehicle.management.entity.DispatchOrder;
import com.vehicle.management.entity.Driver;
import com.vehicle.management.entity.Vehicle;
import com.vehicle.management.mapper.DispatchOrderMapper;
import com.vehicle.management.mapper.DriverMapper;
import com.vehicle.management.mapper.VehicleMapper;
import com.vehicle.management.service.DispatchService;
import com.vehicle.management.vo.DispatchMonitorVO;
import com.vehicle.management.vo.SmartDispatchResultVO;
import com.vehicle.management.vo.SmartDispatchQuery;
import com.vehicle.management.vo.VehicleLocationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 调度服务实现类
 */
@Service
public class DispatchServiceImpl implements DispatchService {
    
    private final DispatchOrderMapper dispatchOrderMapper;
    
    private final VehicleMapper vehicleMapper;

    private final DriverMapper driverMapper;
    

    
    public DispatchServiceImpl(DispatchOrderMapper dispatchOrderMapper,
                              VehicleMapper vehicleMapper,
                              DriverMapper driverMapper) {
        this.dispatchOrderMapper = dispatchOrderMapper;
        this.vehicleMapper = vehicleMapper;
        this.driverMapper = driverMapper;
    }

    @Override
    public DispatchMonitorVO getMonitorData() {
        // 获取车辆统计信息
        DispatchMonitorVO monitorData = new DispatchMonitorVO();
        
        // 查询所有车辆并计算统计信息
        List<Vehicle> allVehicles = vehicleMapper.selectList(null);
        monitorData.setTotalVehicles((long) allVehicles.size());
        monitorData.setIdleVehicles(allVehicles.stream().filter(v -> "IDLE".equals(v.getStatus())).count());
        monitorData.setBusyVehicles(allVehicles.stream().filter(v -> "BUSY".equals(v.getStatus())).count());
        monitorData.setOfflineVehicles(allVehicles.stream().filter(v -> "OFFLINE".equals(v.getStatus())).count());
        
        return monitorData;
    }
    
    @Override
    public String createDispatchOrder(DispatchOrderDTO orderDTO) {
        DispatchOrder dispatchOrder = new DispatchOrder();
        BeanUtils.copyProperties(orderDTO, dispatchOrder);
        
        // 生成调度单号
        String orderNo = "PD" + System.currentTimeMillis();
        dispatchOrder.setOrderNo(orderNo);
        dispatchOrder.setStatus("PENDING");
        dispatchOrder.setCreatedAt(LocalDateTime.now());
        
        // 保存调度单
        dispatchOrderMapper.insert(dispatchOrder);
        
        // 更新车辆状态为使用中
        Vehicle vehicle = vehicleMapper.selectById(orderDTO.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus("BUSY");
            vehicleMapper.updateById(vehicle);
        }
        
        return orderNo;
    }
    
    @Override
    public SmartDispatchResultVO smartDispatch(SmartDispatchQuery query) {
        // 实现智能调度逻辑
        // 这里为演示目的，返回模拟数据
        SmartDispatchResultVO result = new SmartDispatchResultVO();
        result.setRecommendedVehicleId(1L);
        result.setRecommendedDriverId(1L);
        result.setRecommendationReason("距离最近，状态空闲");
        
        return result;
    }
    
    @Override
    public List<VehicleLocationVO> getRealTimeLocation() {
        // 查询所有车辆及其位置信息
        // 这里为演示目的，返回模拟数据
        return vehicleMapper.selectList(null).stream().map(vehicle -> {
            VehicleLocationVO locationVO = new VehicleLocationVO();
            locationVO.setVehicleId(vehicle.getId());
            locationVO.setPlateNo(vehicle.getPlateNo());
            locationVO.setStatus(vehicle.getStatus());
            locationVO.setLongitude(116.397428); // 模拟位置
            locationVO.setLatitude(39.90923);    // 模拟位置
            locationVO.setDriverName("张三");
            return locationVO;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean updateStartMileage(Long orderId, BigDecimal startMileage) {
        DispatchOrder dispatchOrder = dispatchOrderMapper.selectById(orderId);
        if (dispatchOrder == null) {
            return false;
        }
        dispatchOrder.setActualStartMileage(startMileage);
        dispatchOrder.setActualStartTime(LocalDateTime.now());
        dispatchOrder.setStatus("IN_PROGRESS");
        return dispatchOrderMapper.updateById(dispatchOrder) > 0;
    }

    @Override
    public boolean updateEndMileage(Long orderId, BigDecimal endMileage) {
        DispatchOrder dispatchOrder = dispatchOrderMapper.selectById(orderId);
        if (dispatchOrder == null) {
            return false;
        }

        // 验证收车公里数必须大于出车公里数
        if (dispatchOrder.getActualStartMileage() != null && 
            endMileage.compareTo(dispatchOrder.getActualStartMileage()) <= 0) {
            throw new IllegalArgumentException("收车公里数必须大于出车公里数");
        }

        dispatchOrder.setActualEndMileage(endMileage);
        dispatchOrder.setActualEndTime(LocalDateTime.now());
        dispatchOrder.setStatus("COMPLETED");

        // 更新车辆状态为空闲
        Vehicle vehicle = vehicleMapper.selectById(dispatchOrder.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus("IDLE");
            vehicleMapper.updateById(vehicle);
        }

        return dispatchOrderMapper.updateById(dispatchOrder) > 0;
    }

    @Override
    public List<DispatchOrder> getDispatchList() {
        // 查询所有调度单，按创建时间倒序
        List<DispatchOrder> dispatchOrders = dispatchOrderMapper.selectList(null);

        // 为每个调度单关联车辆和驾驶员信息
        return dispatchOrders.stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .peek(order -> {
                    // 关联车辆信息
                    if (order.getVehicleId() != null) {
                        Vehicle vehicle = vehicleMapper.selectById(order.getVehicleId());
                        order.setVehicle(vehicle);
                    }
                    // 关联驾驶员信息
                    if (order.getDriverId() != null) {
                        Driver driver = driverMapper.selectById(order.getDriverId());
                        order.setDriver(driver);
                    }
                })
                .collect(Collectors.toList());
    }
}