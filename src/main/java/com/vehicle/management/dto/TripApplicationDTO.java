
package com.vehicle.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用车申请数据传输对象
 */
@Data
public class TripApplicationDTO {

    /**
     * 用车类型：OFFICIAL-公务,TRANSPORT-通勤,RECEIVE-接待
     */
    @NotBlank(message = "用车类型不能为空")
    private String tripType;

    /**
     * 用车事由
     */
    @NotBlank(message = "用车事由不能为空")
    private String purpose;

    /**
     * 乘车人数
     */
    @NotNull(message = "乘车人数不能为空")
    private Integer passengerCount;

    /**
     * 计划开始时间
     */
    @NotNull(message = "计划开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 计划结束时间
     */
    @NotNull(message = "计划结束时间不能为空")
    private LocalDateTime endTime;

    /**
     * 出发地点
     */
    @NotBlank(message = "出发地点不能为空")
    private String startLocation;

    /**
     * 目的地
     */
    @NotBlank(message = "目的地不能为空")
    private String endLocation;

    /**
     * 备注
     */
    private String remark;
}
