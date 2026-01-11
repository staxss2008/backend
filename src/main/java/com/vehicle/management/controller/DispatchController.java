package com.vehicle.management.controller;

import com.vehicle.management.dto.DispatchOrderDTO;
import com.vehicle.management.entity.DispatchOrder;
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

    @PostMapping("/update-start-mileage")
    @ApiOperation("更新开始里程（出车时填写）")
    public Result<Boolean> updateStartMileage(@RequestParam Long orderId, 
                                             @RequestParam java.math.BigDecimal startMileage) {
        boolean success = dispatchService.updateStartMileage(orderId, startMileage);
        return Result.success(success);
    }

    @PostMapping("/update-end-mileage")
    @ApiOperation("更新结束里程（收车时填写）")
    public Result<Boolean> updateEndMileage(@RequestParam Long orderId, 
                                           @RequestParam java.math.BigDecimal endMileage) {
        boolean success = dispatchService.updateEndMileage(orderId, endMileage);
        return Result.success(success);
    }

    @GetMapping("/list")
    @ApiOperation("获取调度列表")
    public Result<java.util.List<DispatchOrder>> getDispatchList() {
        java.util.List<DispatchOrder> list = dispatchService.getDispatchList();
        return Result.success(list);
    }
}