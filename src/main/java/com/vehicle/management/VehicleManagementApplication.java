package com.vehicle.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.core.env.Environment;

/**
 * 派车管理系统主应用类
 */
@SpringBootApplication
@MapperScan("com.vehicle.management.mapper")
public class VehicleManagementApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VehicleManagementApplication.class);
        app.addListeners((event) -> {
            if (event instanceof org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent) {
                Environment env = ((org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent) event).getEnvironment();
                System.out.println("=== Database Configuration ===");
                System.out.println("DATABASE_URL: " + env.getProperty("DATABASE_URL"));
                System.out.println("RAILWAY_DATABASE_URL: " + env.getProperty("RAILWAY_DATABASE_URL"));
                System.out.println("MYSQLHOST: " + env.getProperty("MYSQLHOST"));
                System.out.println("RAILWAY_MYSQLHOST: " + env.getProperty("RAILWAY_MYSQLHOST"));
                System.out.println("MYSQLUSER: " + env.getProperty("MYSQLUSER"));
                System.out.println("RAILWAY_MYSQLUSER: " + env.getProperty("RAILWAY_MYSQLUSER"));
                System.out.println("MYSQLPASSWORD: " + (env.getProperty("MYSQLPASSWORD") != null ? "***" : "null"));
                System.out.println("RAILWAY_MYSQLPASSWORD: " + (env.getProperty("RAILWAY_MYSQLPASSWORD") != null ? "***" : "null"));
                System.out.println("=============================");
            }
        });
        app.run(args);
    }
}
