package com.vehicle.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vehicle.management.entity.Vehicle;
import com.vehicle.management.vo.VehicleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 车辆Mapper接口
 */
@Repository
public interface VehicleMapper extends BaseMapper<Vehicle> {
    
    /**
     * 获取车辆列表
     * @param plateNo 车牌号
     * @param status 状态
     * @return 车辆列表
     */
    List<VehicleVO> selectVehicleList(@Param("plateNo") String plateNo, 
                                      @Param("status") String status);
    
}