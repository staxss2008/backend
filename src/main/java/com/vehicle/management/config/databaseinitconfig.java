package com.vehicle.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * 数据库初始化配置类
 * 仅在开发环境启用
 */
@Component
@Profile("!railway")
public class DatabaseInitConfig implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            // 检查表是否已存在
            Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'vehicle'",
                Integer.class
            );

            if (tableCount != null && tableCount > 0) {
                System.out.println("数据库表已存在,跳过初始化");
                return;
            }

            // 执行初始化脚本
            executeScript("init.sql");
            System.out.println("数据库初始化完成");

        } catch (Exception e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            throw e;
        }
    }

    private void executeScript(String scriptPath) throws Exception {
        ClassPathResource resource = new ClassPathResource(scriptPath);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String script = reader.lines().collect(Collectors.joining("\n"));

            try (Connection conn = jdbcTemplate.getDataSource().getConnection();
                 Statement stmt = conn.createStatement()) {

                // 分割SQL语句并执行
                String[] sqlStatements = script.split(";");
                for (String sql : sqlStatements) {
                    sql = sql.trim();
                    if (!sql.isEmpty()) {
                        stmt.execute(sql);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("执行SQL脚本失败: " + e.getMessage(), e);
        }
    }
}
