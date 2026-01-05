package com.vehicle.management.controller;

import com.vehicle.management.dto.DriverDTO;
import com.vehicle.management.service.DriverService;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;
import com.vehicle.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 驾驶员管理控制器
 */
@RestController
@RequestMapping("/api/driver")
@Api(tags = "驾驶员管理")
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * 获取驾驶员列表
     */
    @GetMapping("/list")
    @ApiOperation("获取驾驶员列表")
    public Result<PageResult<DriverVO>> getDriverList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String status) {
        PageResult<DriverVO> result = driverService.getDriverList(page, size, name, status);
        return Result.success(result);
    }

    /**
     * 添加驾驶员
     */
    @PostMapping("/add")
    @ApiOperation("添加驾驶员")
    public Result<Long> addDriver(@Valid @RequestBody DriverDTO driverDTO) {
        Long driverId = driverService.addDriver(driverDTO);
        return Result.success(driverId);
    }

    /**
     * 更新驾驶员
     */
    @PutMapping("/update/{id}")
    @ApiOperation("更新驾驶员")
    public Result<Void> updateDriver(@PathVariable Long id,
                             @Valid @RequestBody DriverDTO driverDTO) {
        driverService.updateDriver(id, driverDTO);
        return Result.success();
    }

    /**
     * 删除驾驶员
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除驾驶员")
    public Result<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return Result.success();
    }

    /**
     * 更新驾驶员状态
     */
    @PutMapping("/update-status/{id}")
    @ApiOperation("更新驾驶员状态")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status) {
        driverService.updateStatus(id, status);
        return Result.success();
    }
}
