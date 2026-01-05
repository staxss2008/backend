
package com.vehicle.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vehicle.management.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限Mapper接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
