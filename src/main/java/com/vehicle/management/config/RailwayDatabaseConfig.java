package com.vehicle.management.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Railway数据库配置
 * 处理Railway提供的mysql://格式URL转换为jdbc:mysql://格式
 */
@Configuration
public class RailwayDatabaseConfig {

    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        String url = System.getenv("MYSQL_URL");
        if (url != null && url.startsWith("mysql://")) {
            url = url.replace("mysql://", "jdbc:mysql://");
            properties.setUrl(url);
        }
        return properties;
    }
}
