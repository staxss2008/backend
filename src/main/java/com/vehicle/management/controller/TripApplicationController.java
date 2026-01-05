
package com.vehicle.management.controller;

import com.vehicle.management.dto.AssignVehicleRequest;
import com.vehicle.management.dto.RemarkRequest;
import com.vehicle.management.dto.TripApplicationDTO;
import com.vehicle.management.service.TripApplicationService;
import com.vehicle.management.util.Result;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.vo.TripApplicationVO;
import com.vehicle.management.vo.VehicleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 用车申请控制器
 */
@RestController
@RequestMapping("/api/application")
@Api(tags = "用车申请管理")
public class TripApplicationController {

    @Autowired
    private TripApplicationService tripApplicationService;

    @GetMapping("/list")
    @ApiOperation("获取用车申请列表")
    public Result<PageResult<TripApplicationVO>> getApplicationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String status) {
        PageResult<TripApplicationVO> result = tripApplicationService.getApplicationList(page, size, status);
        return Result.success(result);
    }

    @PostMapping("/create")
    @ApiOperation("创建用车申请")
    public Result<Long> createApplication(@Valid @RequestBody TripApplicationDTO applicationDTO) {
        Long applicationId = tripApplicationService.createApplication(applicationDTO);
        return Result.success(applicationId);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation("获取申请详情")
    public Result<TripApplicationVO> getApplicationDetail(@PathVariable Long id) {
        TripApplicationVO applicationVO = tripApplicationService.getApplicationDetail(id);
        return Result.success(applicationVO);
    }

    @PutMapping("/approve/{id}")
    @ApiOperation("批准用车申请")
    public Result<Void> approveApplication(
            @PathVariable Long id,
            @RequestBody(required = false) RemarkRequest request) {
        tripApplicationService.approveApplication(id, request != null ? request.getRemark() : null);
        return Result.success();
    }

    @PutMapping("/reject/{id}")
    @ApiOperation("拒绝用车申请")
    public Result<Void> rejectApplication(
            @PathVariable Long id,
            @RequestBody(required = false) RemarkRequest request) {
        tripApplicationService.rejectApplication(id, request != null ? request.getRemark() : null);
        return Result.success();
    }

    @PostMapping("/assign-vehicle/{id}")
    @ApiOperation("指派车辆")
    public Result<Void> assignVehicle(
            @PathVariable Long id,
            @RequestBody AssignVehicleRequest request) {
        tripApplicationService.assignVehicle(id, request.getVehicleId(), request.getDriverId(), request.getDispatcherId());
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("完成申请")
    public Result<Void> completeApplication(@PathVariable Long id) {
        tripApplicationService.completeApplication(id);
        return Result.success();
    }

    @GetMapping("/available-drivers")
    @ApiOperation("获取可用的驾驶员列表")
    public Result<List<DriverVO>> getAvailableDrivers() {
        List<DriverVO> drivers = tripApplicationService.getAvailableDrivers();
        return Result.success(drivers);
    }

    @GetMapping("/available-vehicles")
    @ApiOperation("获取可用的车辆列表")
    public Result<List<VehicleVO>> getAvailableVehicles() {
        List<VehicleVO> vehicles = tripApplicationService.getAvailableVehicles();
        return Result.success(vehicles);
    }
}
