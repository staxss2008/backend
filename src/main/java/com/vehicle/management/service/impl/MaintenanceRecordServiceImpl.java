
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.MaintenanceRecordDTO;
import com.vehicle.management.entity.MaintenanceRecord;
import com.vehicle.management.entity.Vehicle;
import com.vehicle.management.mapper.MaintenanceRecordMapper;
import com.vehicle.management.mapper.VehicleMapper;
import com.vehicle.management.service.MaintenanceRecordService;
import com.vehicle.management.vo.MaintenanceRecordVO;
import com.vehicle.management.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车辆维修保养记录服务实现类
 */
@Service
public class MaintenanceRecordServiceImpl implements MaintenanceRecordService {

    private final MaintenanceRecordMapper maintenanceRecordMapper;
    private final VehicleMapper vehicleMapper;

    public MaintenanceRecordServiceImpl(MaintenanceRecordMapper maintenanceRecordMapper,
                                    VehicleMapper vehicleMapper) {
        this.maintenanceRecordMapper = maintenanceRecordMapper;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public PageResult<MaintenanceRecordVO> getMaintenanceRecordList(Integer page, Integer size, Long vehicleId, String type) {
        Page<MaintenanceRecord> recordPage = new Page<>(page, size);

        QueryWrapper<MaintenanceRecord> queryWrapper = new QueryWrapper<>();
        if (vehicleId != null) {
            queryWrapper.eq("vehicle_id", vehicleId);
        }
        if (type != null && !type.isEmpty()) {
            queryWrapper.eq("type", type);
        }
        queryWrapper.orderByDesc("date");

        Page<MaintenanceRecord> resultPage = maintenanceRecordMapper.selectPage(recordPage, queryWrapper);

        // 将实体转换为VO
        List<MaintenanceRecordVO> recordVOList = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 构建分页结果
        PageResult<MaintenanceRecordVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(recordVOList);

        return pageResult;
    }

    @Override
    public Long createMaintenanceRecord(MaintenanceRecordDTO recordDTO) {
        if (recordDTO == null) {
            throw new IllegalArgumentException("RecordDTO cannot be null");
        }

        // 获取车辆信息
        Vehicle vehicle = vehicleMapper.selectById(recordDTO.getVehicleId());
        if (vehicle == null) {
            throw new IllegalArgumentException("车辆不存在");
        }

        MaintenanceRecord record = new MaintenanceRecord();
        BeanUtils.copyProperties(recordDTO, record);

        // 设置车牌号
        record.setPlateNo(vehicle.getPlateNo());

        // 设置创建时间
        record.setCreatedAt(LocalDateTime.now());

        maintenanceRecordMapper.insert(record);

        // 更新车辆状态为维修中
        vehicle.setStatus("MAINTENANCE");
        vehicle.setUpdatedAt(LocalDateTime.now());
        vehicleMapper.updateById(vehicle);

        return record.getId();
    }

    @Override
    public MaintenanceRecordVO getMaintenanceRecordDetail(Long id) {
        MaintenanceRecord record = maintenanceRecordMapper.selectById(id);
        if (record == null) {
            throw new IllegalArgumentException("记录不存在");
        }
        return convertToVO(record);
    }

    @Override
    public void updateMaintenanceRecord(Long id, MaintenanceRecordDTO recordDTO) {
        MaintenanceRecord record = maintenanceRecordMapper.selectById(id);
        if (record == null) {
            throw new IllegalArgumentException("记录不存在");
        }

        // 获取车辆信息
        Vehicle vehicle = vehicleMapper.selectById(recordDTO.getVehicleId());
        if (vehicle == null) {
            throw new IllegalArgumentException("车辆不存在");
        }

        BeanUtils.copyProperties(recordDTO, record);
        record.setPlateNo(vehicle.getPlateNo());

        maintenanceRecordMapper.updateById(record);
    }

    @Override
    public void deleteMaintenanceRecord(Long id) {
        // 获取维修保养记录
        MaintenanceRecord record = maintenanceRecordMapper.selectById(id);
        if (record != null) {
            // 恢复车辆状态为空闲
            Vehicle vehicle = vehicleMapper.selectById(record.getVehicleId());
            if (vehicle != null) {
                vehicle.setStatus("IDLE");
                vehicle.setUpdatedAt(LocalDateTime.now());
                vehicleMapper.updateById(vehicle);
            }
        }
        maintenanceRecordMapper.deleteById(id);
    }

    /**
     * 将MaintenanceRecord实体转换为MaintenanceRecordVO
     * @param record MaintenanceRecord实体
     * @return MaintenanceRecordVO对象
     */
    private MaintenanceRecordVO convertToVO(MaintenanceRecord record) {
        MaintenanceRecordVO recordVO = new MaintenanceRecordVO();
        BeanUtils.copyProperties(record, recordVO);

        // 获取车辆状态
        Vehicle vehicle = vehicleMapper.selectById(record.getVehicleId());
        if (vehicle != null) {
            recordVO.setVehicleStatus(vehicle.getStatus());
        }

        return recordVO;
    }
}
