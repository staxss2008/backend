
package com.vehicle.management.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用车申请实体类
 */
@Data
@TableName("trip_application")
public class TripApplication {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 申请单号
     */
    private String applicationNo;

    /**
     * 申请人ID
     */
    private Long applicantId;

    /**
     * 申请人姓名
     */
    private String applicantName;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 用车事由
     */
    private String purpose;

    /**
     * 用车类型：OFFICIAL-公务,TRANSPORT-通勤,RECEIVE-接待
     */
    private String tripType;

    /**
     * 乘车人数
     */
    private Integer passengerCount;

    /**
     * 计划开始时间
     */
    private LocalDateTime startTime;

    /**
     * 计划结束时间
     */
    private LocalDateTime endTime;

    /**
     * 出发地点
     */
    private String startLocation;

    /**
     * 目的地
     */
    private String endLocation;

    /**
     * 状态
     */
    private String status;

    /**
     * 当前审批人
     */
    private String currentApprover;

    /**
     * 备注
     */
    private String remark;

    /**
     * 车辆ID
     */
    private Long vehicleId;

    /**
     * 驾驶员ID
     */
    private Long driverId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
