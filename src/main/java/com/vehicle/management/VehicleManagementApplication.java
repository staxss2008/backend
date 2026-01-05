package com.vehicle.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 派车管理系统主应用类
 */
@SpringBootApplication
@MapperScan("com.vehicle.management.mapper")
public class VehicleManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(VehicleManagementApplication.class, args);
    }
}