package com.vehicle.management.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 调度订单数据传输对象
 */
@Data
public class DispatchOrderDTO {
    
    /**
     * 关联申请ID
     */
    @NotNull(message = "申请ID不能为空")
    private Long applicationId;
    
    /**
     * 车辆ID
     */
    @NotNull(message = "车辆ID不能为空")
    private Long vehicleId;
    
    /**
     * 驾驶员ID
     */
    @NotNull(message = "驾驶员ID不能为空")
    private Long driverId;
    
    /**
     * 调度员ID
     */
    private Long dispatcherId;
    
    /**
     * 计划开始里程
     */
    private BigDecimal planStartMileage;
    
    /**
     * 计划结束里程
     */
    private BigDecimal planEndMileage;
}