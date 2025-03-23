package com.backofli.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@Profile("dev") // 只在开发环境启用
public class DatabaseInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeDatabase() {
        try {
            // 从URL中提取数据库名称和基础URL
            String[] urlParts = extractDatabaseNameAndBaseUrl(dbUrl);
            String baseUrl = urlParts[0];
            String dbName = urlParts[1];

            // 创建数据库连接（不指定数据库）
            try (Connection conn = DriverManager.getConnection(baseUrl, username, password)) {
                // 检查数据库是否存在
                if (!databaseExists(conn, dbName)) {
                    // 创建数据库
                    createDatabase(conn, dbName);
                    logger.info("数据库 {} 创建成功", dbName);
                }
            }

            // 创建表
            createTables();
            logger.info("数据库表创建成功");

        } catch (SQLException e) {
            logger.error("数据库初始化失败", e);
            throw new RuntimeException("数据库初始化失败", e);
        }
    }

    private String[] extractDatabaseNameAndBaseUrl(String url) {
        // 移除URL参数
        String urlWithoutParams = url.split("\\?")[0];
        // 分割基础URL和数据库名
        String[] parts = urlWithoutParams.split("/");
        String dbName = parts[parts.length - 1];
        String baseUrl = urlWithoutParams.substring(0, urlWithoutParams.lastIndexOf("/"));
        return new String[]{baseUrl, dbName};
    }

    private boolean databaseExists(Connection conn, String dbName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("SELECT 1 FROM information_schema.schemata WHERE schema_name = '" + dbName + "'");
            return stmt.getResultSet().next();
        }
    }

    private void createDatabase(Connection conn, String dbName) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE DATABASE " + dbName);
        }
    }

    private void createTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        DatabasePopulatorUtils.execute(populator, dataSource);
    }
} 