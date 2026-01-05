package com.vehicle.management.controller;

import com.vehicle.management.dto.VehicleDTO;
import com.vehicle.management.service.VehicleService;
import com.vehicle.management.vo.VehicleVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * 车辆管理控制器
 */
@RestController
@RequestMapping("/api/vehicle")
@Api(tags = "车辆管理")
public class VehicleController {
    
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
    
    @GetMapping("/list")
    @ApiOperation("获取车辆列表")
    public Result<PageResult<VehicleVO>> getVehicleList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String plateNo,
            @RequestParam(required = false) String status) {
        PageResult<VehicleVO> result = vehicleService.getVehicleList(page, size, plateNo, status);
        return Result.success(result);
    }
    
    @PostMapping("/add")
    @ApiOperation("新增车辆")
    public Result<Long> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        Long vehicleId = vehicleService.addVehicle(vehicleDTO);
        return Result.success(vehicleId);
    }
    
    @PutMapping("/update/{id}")
    @ApiOperation("更新车辆信息")
    public Result<Void> updateVehicle(@PathVariable Long id, 
                                      @Valid @RequestBody VehicleDTO vehicleDTO) {
        vehicleService.updateVehicle(id, vehicleDTO);
        return Result.success();
    }
    
    @PutMapping("/update-status/{id}")
    @ApiOperation("更新车辆状态")
    public Result<Void> updateVehicleStatus(@PathVariable Long id,
                                            @RequestParam String status) {
        vehicleService.updateStatus(id, status);
        return Result.success();
    }
    
    @GetMapping("/stats")
    @ApiOperation("获取车辆统计")
    public Result<VehicleVO> getVehicleStats() {
        VehicleVO stats = vehicleService.getVehicleStats();
        return Result.success(stats);
    }

    @PostMapping("/sync-maintenance")
    @ApiOperation("同步维修记录")
    public Result<Void> syncMaintenanceRecords() {
        vehicleService.syncMaintenanceRecords();
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除车辆")
    public Result<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return Result.success();
    }
}