package com.vehicle.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 指派车辆请求数据传输对象
 */
@Data
public class AssignVehicleRequest {

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
    @NotNull(message = "调度员ID不能为空")
    private Long dispatcherId;
}
