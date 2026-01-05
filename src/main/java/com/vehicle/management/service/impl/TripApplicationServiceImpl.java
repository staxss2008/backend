
package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.DispatchOrderDTO;
import com.vehicle.management.dto.TripApplicationDTO;
import com.vehicle.management.entity.Driver;
import com.vehicle.management.entity.TripApplication;
import com.vehicle.management.entity.Vehicle;
import com.vehicle.management.mapper.DriverMapper;
import com.vehicle.management.mapper.TripApplicationMapper;
import com.vehicle.management.mapper.VehicleMapper;
import com.vehicle.management.service.DispatchService;
import com.vehicle.management.service.TripApplicationService;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.vo.TripApplicationVO;
import com.vehicle.management.vo.VehicleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用车申请服务实现类
 */
@Service
public class TripApplicationServiceImpl implements TripApplicationService {

    private final TripApplicationMapper tripApplicationMapper;
    private final DispatchService dispatchService;
    private final VehicleMapper vehicleMapper;
    private final DriverMapper driverMapper;

    public TripApplicationServiceImpl(TripApplicationMapper tripApplicationMapper,
                                  DispatchService dispatchService,
                                  VehicleMapper vehicleMapper,
                                  DriverMapper driverMapper) {
        this.tripApplicationMapper = tripApplicationMapper;
        this.dispatchService = dispatchService;
        this.vehicleMapper = vehicleMapper;
        this.driverMapper = driverMapper;
    }

    @Override
    public PageResult<TripApplicationVO> getApplicationList(Integer page, Integer size, String status) {
        Page<TripApplication> applicationPage = new Page<>(page, size);

        QueryWrapper<TripApplication> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        // 按创建时间倒序排列，最新的申请排在第一位
        queryWrapper.orderByDesc("created_at");

        Page<TripApplication> resultPage = tripApplicationMapper.selectPage(applicationPage, queryWrapper);

        // 将实体转换为VO
        List<TripApplicationVO> applicationVOList = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 构建分页结果
        PageResult<TripApplicationVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(applicationVOList);

        return pageResult;
    }

    @Override
    public Long createApplication(TripApplicationDTO applicationDTO) {
        if (applicationDTO == null) {
            throw new IllegalArgumentException("ApplicationDTO cannot be null");
        }

        // 验证结束时间是否大于开始时间
        if (applicationDTO.getEndTime().isBefore(applicationDTO.getStartTime()) || 
            applicationDTO.getEndTime().isEqual(applicationDTO.getStartTime())) {
            throw new IllegalArgumentException("结束时间必须大于开始时间");
        }

        TripApplication application = new TripApplication();
        BeanUtils.copyProperties(applicationDTO, application);

        // 生成申请单号
        String applicationNo = generateApplicationNo();
        application.setApplicationNo(applicationNo);

        // 设置默认值
        application.setStatus("PENDING"); // 默认状态为待审批
        application.setApplicantId(1L); // TODO: 从当前登录用户获取
        application.setApplicantName("张三"); // TODO: 从当前登录用户获取
        application.setDepartmentId(1L); // TODO: 从当前登录用户获取
        application.setDepartmentName("行政部"); // TODO: 从当前登录用户获取
        application.setCurrentApprover("李四"); // TODO: 从审批流程配置获取
        application.setCreatedAt(LocalDateTime.now());

        tripApplicationMapper.insert(application);
        return application.getId();
    }

    @Override
    public TripApplicationVO getApplicationDetail(Long id) {
        TripApplication application = tripApplicationMapper.selectById(id);
        if (application == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        return convertToVO(application);
    }

    @Override
    public void approveApplication(Long id, String remark) {
        TripApplication application = tripApplicationMapper.selectById(id);
        if (application == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        if (!"PENDING".equals(application.getStatus())) {
            throw new IllegalArgumentException("只有待审批的申请才能批准");
        }

        application.setStatus("APPROVED");
        application.setRemark(remark);
        tripApplicationMapper.updateById(application);
    }

    @Override
    public void rejectApplication(Long id, String remark) {
        TripApplication application = tripApplicationMapper.selectById(id);
        if (application == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        if (!"PENDING".equals(application.getStatus())) {
            throw new IllegalArgumentException("只有待审批的申请才能拒绝");
        }

        application.setStatus("REJECTED");
        application.setRemark(remark);
        tripApplicationMapper.updateById(application);
    }

    @Override
    public void assignVehicle(Long id, Long vehicleId, Long driverId, Long dispatcherId) {
        TripApplication application = tripApplicationMapper.selectById(id);
        if (application == null) {
            throw new IllegalArgumentException("申请不存在");
        }
        
        // 只有待审批或已批准的申请才能指派车辆
        if (!"PENDING".equals(application.getStatus()) && !"APPROVED".equals(application.getStatus())) {
            throw new IllegalArgumentException("只有待审批或已批准的申请才能指派车辆");
        }

        // 检查驾驶员状态是否可用
        if (driverId != null) {
            Driver driver = driverMapper.selectById(driverId);
            if (driver == null) {
                throw new IllegalArgumentException("驾驶员不存在");
            }
            if (!"ACTIVE".equals(driver.getStatus())) {
                throw new IllegalArgumentException("该驾驶员当前不可用，状态为：" + driver.getStatus());
            }
        }

        // 检查车辆状态是否可用
        if (vehicleId != null) {
            Vehicle vehicle = vehicleMapper.selectById(vehicleId);
            if (vehicle == null) {
                throw new IllegalArgumentException("车辆不存在");
            }
            if (!"IDLE".equals(vehicle.getStatus())) {
                throw new IllegalArgumentException("该车辆当前不可用，状态为：" + vehicle.getStatus());
            }
        }

        // 如果申请状态为待审批，则先批准
        if ("PENDING".equals(application.getStatus())) {
            application.setStatus("APPROVED");
            tripApplicationMapper.updateById(application);
        }

        // 调用DispatchService创建调度单
        DispatchOrderDTO dispatchOrderDTO = new DispatchOrderDTO();
        dispatchOrderDTO.setApplicationId(id);
        dispatchOrderDTO.setVehicleId(vehicleId);
        dispatchOrderDTO.setDriverId(driverId);
        dispatchOrderDTO.setDispatcherId(dispatcherId);
        
        dispatchService.createDispatchOrder(dispatchOrderDTO);
        
        // 更新驾驶员状态为已出车
        if (driverId != null) {
            Driver driver = driverMapper.selectById(driverId);
            if (driver != null) {
                driver.setStatus("ON_TRIP");
                driverMapper.updateById(driver);
            }
        }

        // 更新车辆状态为使用中
        if (vehicleId != null) {
            Vehicle vehicle = vehicleMapper.selectById(vehicleId);
            if (vehicle != null) {
                vehicle.setStatus("IN_USE");
                vehicleMapper.updateById(vehicle);
            }
        }

        // 更新申请状态为已指派，并设置车辆和驾驶员信息
        application.setStatus("ASSIGNED");
        application.setVehicleId(vehicleId);
        application.setDriverId(driverId);
        tripApplicationMapper.updateById(application);
    }

    /**
     * 生成申请单号
     * @return 申请单号
     */
    private String generateApplicationNo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "SQ" + timestamp;
    }

    /**
     * 将TripApplication实体转换为TripApplicationVO
     * @param application TripApplication实体
     * @return TripApplicationVO对象
     */
    private TripApplicationVO convertToVO(TripApplication application) {
        TripApplicationVO applicationVO = new TripApplicationVO();
        BeanUtils.copyProperties(application, applicationVO);
        
        // 查询并设置车辆信息
        if (application.getVehicleId() != null) {
            Vehicle vehicle = vehicleMapper.selectById(application.getVehicleId());
            if (vehicle != null) {
                applicationVO.setVehicleId(vehicle.getId());
                applicationVO.setVehiclePlateNo(vehicle.getPlateNo());
            }
        }
        
        // 查询并设置驾驶员信息
        if (application.getDriverId() != null) {
            Driver driver = driverMapper.selectById(application.getDriverId());
            if (driver != null) {
                applicationVO.setDriverId(driver.getId());
                applicationVO.setDriverName(driver.getName());
            }
        }
        
        return applicationVO;
    }

    @Override
    public List<DriverVO> getAvailableDrivers() {
        // 查询所有状态为ACTIVE的驾驶员
        QueryWrapper<Driver> driverQueryWrapper = new QueryWrapper<>();
        driverQueryWrapper.eq("status", "ACTIVE");
        List<Driver> availableDrivers = driverMapper.selectList(driverQueryWrapper);

        // 转换为VO
        return availableDrivers.stream()
                .map(this::convertDriverToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleVO> getAvailableVehicles() {
        // 查询所有状态为IDLE的车辆
        QueryWrapper<Vehicle> vehicleQueryWrapper = new QueryWrapper<>();
        vehicleQueryWrapper.eq("status", "IDLE");
        List<Vehicle> availableVehicles = vehicleMapper.selectList(vehicleQueryWrapper);

        // 转换为VO
        return availableVehicles.stream()
                .map(this::convertVehicleToVO)
                .collect(Collectors.toList());
    }

    /**
     * 将Driver实体转换为DriverVO
     * @param driver Driver实体
     * @return DriverVO对象
     */
    private DriverVO convertDriverToVO(Driver driver) {
        DriverVO driverVO = new DriverVO();
        BeanUtils.copyProperties(driver, driverVO);
        return driverVO;
    }

    @Override
    public void completeApplication(Long id) {
        TripApplication application = tripApplicationMapper.selectById(id);
        if (application == null) {
            throw new IllegalArgumentException("申请不存在");
        }

        // 只有已指派或进行中的申请才能完成
        if (!"ASSIGNED".equals(application.getStatus()) && !"IN_PROGRESS".equals(application.getStatus())) {
            throw new IllegalArgumentException("只有已指派或进行中的申请才能完成");
        }

        // 恢复驾驶员状态为可用
        if (application.getDriverId() != null) {
            Driver driver = driverMapper.selectById(application.getDriverId());
            if (driver != null) {
                driver.setStatus("ACTIVE");
                driverMapper.updateById(driver);
            }
        }

        // 恢复车辆状态为空闲
        if (application.getVehicleId() != null) {
            Vehicle vehicle = vehicleMapper.selectById(application.getVehicleId());
            if (vehicle != null) {
                vehicle.setStatus("IDLE");
                vehicleMapper.updateById(vehicle);
            }
        }

        // 更新申请状态为已完成
        application.setStatus("COMPLETED");
        tripApplicationMapper.updateById(application);
    }

    /**
     * 将Vehicle实体转换为VehicleVO
     * @param vehicle Vehicle实体
     * @return VehicleVO对象
     */
    private VehicleVO convertVehicleToVO(Vehicle vehicle) {
        VehicleVO vehicleVO = new VehicleVO();
        BeanUtils.copyProperties(vehicle, vehicleVO);
        return vehicleVO;
    }
}
