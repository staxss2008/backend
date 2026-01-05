
package com.vehicle.management.controller;

import com.vehicle.management.dto.MaintenanceRecordDTO;
import com.vehicle.management.service.MaintenanceRecordService;
import com.vehicle.management.util.Result;
import com.vehicle.management.vo.MaintenanceRecordVO;
import com.vehicle.management.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 车辆维修保养记录控制器
 */
@RestController
@RequestMapping("/api/maintenance")
@Api(tags = "车辆维修保养管理")
public class MaintenanceRecordController {

    @Autowired
    private MaintenanceRecordService maintenanceRecordService;

    @GetMapping("/list")
    @ApiOperation("获取维修保养记录列表")
    public Result<PageResult<MaintenanceRecordVO>> getMaintenanceRecordList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long vehicleId,
            @RequestParam(required = false) String type) {
        PageResult<MaintenanceRecordVO> result = maintenanceRecordService.getMaintenanceRecordList(page, size, vehicleId, type);
        return Result.success(result);
    }

    @PostMapping("/create")
    @ApiOperation("创建维修保养记录")
    public Result<Long> createMaintenanceRecord(@Valid @RequestBody MaintenanceRecordDTO recordDTO) {
        Long recordId = maintenanceRecordService.createMaintenanceRecord(recordDTO);
        return Result.success(recordId);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("获取记录详情")
    public Result<MaintenanceRecordVO> getMaintenanceRecordDetail(@PathVariable Long id) {
        MaintenanceRecordVO recordVO = maintenanceRecordService.getMaintenanceRecordDetail(id);
        return Result.success(recordVO);
    }

    @PutMapping("/update/{id}")
    @ApiOperation("更新维修保养记录")
    public Result<Void> updateMaintenanceRecord(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRecordDTO recordDTO) {
        maintenanceRecordService.updateMaintenanceRecord(id, recordDTO);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除维修保养记录")
    public Result<Void> deleteMaintenanceRecord(@PathVariable Long id) {
        maintenanceRecordService.deleteMaintenanceRecord(id);
        return Result.success();
    }
}
