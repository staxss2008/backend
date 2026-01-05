package com.vehicle.management.controller;

import com.vehicle.management.dto.DispatchOrderDTO;
import com.vehicle.management.service.DispatchService;
import com.vehicle.management.vo.DispatchMonitorVO;
import com.vehicle.management.vo.SmartDispatchResultVO;
import com.vehicle.management.vo.SmartDispatchQuery;
import com.vehicle.management.vo.VehicleLocationVO;
import com.vehicle.management.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 调度管理控制器
 */
@RestController
@RequestMapping("/api/dispatch")
@Api(tags = "调度管理")
public class DispatchController {
    
    @Autowired
    private DispatchService dispatchService;
    
    @GetMapping("/monitor-data")
    @ApiOperation("获取监控数据")
    public Result<DispatchMonitorVO> getMonitorData() {
        DispatchMonitorVO monitorData = dispatchService.getMonitorData();
        return Result.success(monitorData);
    }
    
    @PostMapping("/create-order")
    @ApiOperation("创建调度单")
    public Result<String> createDispatchOrder(@Valid @RequestBody DispatchOrderDTO orderDTO) {
        String orderNo = dispatchService.createDispatchOrder(orderDTO);
        return Result.success(orderNo);
    }
    
    @PostMapping("/smart-dispatch")
    @ApiOperation("智能调度推荐")
    public Result<SmartDispatchResultVO> smartDispatch(@Valid @RequestBody SmartDispatchQuery query) {
        SmartDispatchResultVO result = dispatchService.smartDispatch(query);
        return Result.success(result);
    }
    
    @GetMapping("/real-time-location")
    @ApiOperation("获取实时位置")
    public Result<List<VehicleLocationVO>> getRealTimeLocation() {
        List<VehicleLocationVO> locations = dispatchService.getRealTimeLocation();
        return Result.success(locations);
    }
}