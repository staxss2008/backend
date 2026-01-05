package com.vehicle.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vehicle.management.dto.DriverDTO;
import com.vehicle.management.entity.Driver;
import com.vehicle.management.mapper.DriverMapper;
import com.vehicle.management.service.DriverService;
import com.vehicle.management.vo.DriverVO;
import com.vehicle.management.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 驾驶员服务实现类
 */
@Service
public class DriverServiceImpl implements DriverService {
    
    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverMapper driverMapper) {
        this.driverMapper = driverMapper;
    }
    
    @Override
    public PageResult<DriverVO> getDriverList(Integer page, Integer size, String name, String status) {
        Page<Driver> driverPage = new Page<>(page, size);
        
        QueryWrapper<Driver> queryWrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            queryWrapper.like("name", name);
        }
        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }
        
        Page<Driver> resultPage = driverMapper.selectPage(driverPage, queryWrapper);
        
        // 将实体转换为VO
        List<DriverVO> driverVOList = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        // 构建分页结果
        PageResult<DriverVO> pageResult = new PageResult<>();
        pageResult.setPage(page);
        pageResult.setSize(size);
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setPages(Math.toIntExact(resultPage.getPages()));
        pageResult.setList(driverVOList);
        
        return pageResult;
    }
    
    /**
     * 将Driver实体转换为DriverVO
     * @param driver Driver实体
     * @return DriverVO对象
     */
    private DriverVO convertToVO(Driver driver) {
        DriverVO driverVO = new DriverVO();
        BeanUtils.copyProperties(driver, driverVO);
        return driverVO;
    }
    
    @Override
    public Long addDriver(DriverDTO driverDTO) {
        if (driverDTO == null) {
            throw new IllegalArgumentException("DriverDTO cannot be null");
        }
        Driver driver = new Driver();
        BeanUtils.copyProperties(driverDTO, driver);
        driver.setStatus("ACTIVE"); // 默认状态为激活
        driver.setCreatedAt(LocalDateTime.now());
        
        driverMapper.insert(driver);
        return driver.getId();
    }
    
    @Override
    public void updateDriver(Long id, DriverDTO driverDTO) {
        Driver driver = driverMapper.selectById(id);
        if (driver != null) {
            BeanUtils.copyProperties(driverDTO, driver);
            driverMapper.updateById(driver);
        }
    }
    
    @Override
    public void deleteDriver(Long id) {
        driverMapper.deleteById(id);
    }
    
    @Override
    public void updateStatus(Long id, String status) {
        if (id == null || status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("ID and status must not be null or empty");
        }
        Driver driver = driverMapper.selectById(id);
        if (driver != null) {
            driver.setStatus(status.trim());
            driverMapper.updateById(driver);
        }
    }
}