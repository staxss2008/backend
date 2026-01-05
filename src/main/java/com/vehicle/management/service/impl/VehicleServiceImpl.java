package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.VehicleDTO;
import com.vehicle.management.entity.MaintenanceRecord;
import com.vehicle.management.entity.Vehicle;
import com.vehicle.management.mapper.MaintenanceRecordMapper;
import com.vehicle.management.mapper.VehicleMapper;
import com.vehicle.management.service.VehicleService;
import com.vehicle.management.vo.VehicleVO;
import com.vehicle.management.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 车辆服务实现类
 */
@Service
public class VehicleServiceImpl implements VehicleService {
    
    private final VehicleMapper vehicleMapper;
    private final MaintenanceRecordMapper maintenanceRecordMapper;

    public VehicleServiceImpl(VehicleMapper vehicleMapper,
                            MaintenanceRecordMapper maintenanceRecordMapper) {
        this.vehicleMapper = vehicleMapper;
        this.maintenanceRecordMapper = maintenanceRecordMapper;
    }
    
    @Override
    public PageResult<VehicleVO> getVehicleList(Integer page, Integer size, String plateNo, String status) {
        Page<Vehicle> vehiclePage = new Page<>(page, size);
        
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        if (plateNo != null && !plateNo.isEmpty()) {
            queryWrapper.like("plate_no", plateNo);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        
        Page<Vehicle> resultPage = vehicleMapper.selectPage(vehiclePage, queryWrapper);
        
        // 将实体转换为VO
        List<VehicleVO> vehicleVOList = resultPage.getRecords().stream().map(vehicle -> {
            VehicleVO vehicleVO = new VehicleVO();
            BeanUtils.copyProperties(vehicle, vehicleVO);
            return vehicleVO;
        }).collect(Collectors.toList());
        
        // 构建分页结果
        PageResult<VehicleVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(vehicleVOList);
        
        return pageResult;
    }
    
    @Override
    public Long addVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);
        vehicle.setStatus("IDLE"); // 默认状态为空闲
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setUpdatedAt(LocalDateTime.now());
        
        vehicleMapper.insert(vehicle);
        return vehicle.getId();
    }
    
    @Override
    public void updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        if (vehicle != null) {
            BeanUtils.copyProperties(vehicleDTO, vehicle);
            vehicle.setUpdatedAt(LocalDateTime.now());
            vehicleMapper.updateById(vehicle);
        }
    }
    
    @Override
    public void updateStatus(Long id, String status) {
        Vehicle vehicle = vehicleMapper.selectById(id);
        if (vehicle != null) {
            String oldStatus = vehicle.getStatus();

            // 如果状态从非维修中变为维修中，创建维修记录
            if (!"MAINTENANCE".equals(oldStatus) && "MAINTENANCE".equals(status)) {
                MaintenanceRecord record = new MaintenanceRecord();
                record.setVehicleId(id);
                record.setPlateNo(vehicle.getPlateNo());
                record.setType("MAINTENANCE");
                record.setDate(LocalDateTime.now());
                record.setContent("车辆维修");
                record.setMileage(new BigDecimal("0"));
                record.setCost(new BigDecimal("0"));
                maintenanceRecordMapper.insert(record);
            }
            // 如果状态从维修中变为非维修中，删除该车辆的维修记录
            else if ("MAINTENANCE".equals(oldStatus) && !"MAINTENANCE".equals(status)) {
                QueryWrapper<MaintenanceRecord> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("vehicle_id", id);
                List<MaintenanceRecord> records = maintenanceRecordMapper.selectList(queryWrapper);
                for (MaintenanceRecord record : records) {
                    maintenanceRecordMapper.deleteById(record.getId());
                }
            }

            vehicle.setStatus(status);
            vehicle.setUpdatedAt(LocalDateTime.now());
            vehicleMapper.updateById(vehicle);
        }
    }
    
    @Override
    public VehicleVO getVehicleStats() {
        // 临时实现，实际应该查询数据库统计信息
        // 这里我们查询所有车辆并计算统计信息
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        List<Vehicle> allVehicles = vehicleMapper.selectList(queryWrapper);
        
        VehicleVO stats = new VehicleVO();
        stats.setTotalVehicles((long) allVehicles.size());
        stats.setIdleVehicles(allVehicles.stream().filter(v -> "IDLE".equals(v.getStatus())).count());
        stats.setBusyVehicles(allVehicles.stream().filter(v -> "BUSY".equals(v.getStatus())).count());
        stats.setOfflineVehicles(allVehicles.stream().filter(v -> "OFFLINE".equals(v.getStatus())).count());
        stats.setMaintenanceVehicles(allVehicles.stream().filter(v -> "MAINTENANCE".equals(v.getStatus())).count());
        
        return stats;
    }

    @Override
    public void syncMaintenanceRecords() {
        // 查询所有状态为维修中的车辆
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
        vehicleQueryWrapper.eq("status", "MAINTENANCE");
        List<Vehicle> maintenanceVehicles = vehicleMapper.selectList(vehicleQueryWrapper);

        // 查询所有维修记录
        QueryWrapper<MaintenanceRecord> recordQueryWrapper = new QueryWrapper<>();
        List<MaintenanceRecord> allRecords = maintenanceRecordMapper.selectList(recordQueryWrapper);

        // 获取已有维修记录的车辆ID集合
        Set<Long> vehicleIdsWithRecords = allRecords.stream()
                .map(MaintenanceRecord::getVehicleId)
                .collect(Collectors.toSet());

        // 为没有维修记录的维修中车辆创建维修记录
        for (Vehicle vehicle : maintenanceVehicles) {
            if (!vehicleIdsWithRecords.contains(vehicle.getId())) {
                MaintenanceRecord record = new MaintenanceRecord();
                record.setVehicleId(vehicle.getId());
                record.setPlateNo(vehicle.getPlateNo());
                record.setType("MAINTENANCE");
                record.setDate(LocalDateTime.now());
                record.setContent("车辆维修");
                record.setMileage(new BigDecimal("0"));
                record.setCost(new BigDecimal("0"));
                maintenanceRecordMapper.insert(record);
            }
        }
    }

    @Override
    public void deleteVehicle(Long id) {
        // 删除车辆前，先删除该车辆的所有维修记录
        QueryWrapper<MaintenanceRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vehicle_id", id);
        List<MaintenanceRecord> records = maintenanceRecordMapper.selectList(queryWrapper);
        for (MaintenanceRecord record : records) {
            maintenanceRecordMapper.deleteById(record.getId());
        }

        // 删除车辆
        vehicleMapper.deleteById(id);
    }
}