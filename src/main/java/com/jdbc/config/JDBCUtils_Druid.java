package com.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class JDBCUtils_Druid {
    @Value("${spring.datasource.driver-class-name}")
    private static String driverClassName;
    @Value("${spring.datasource.url}")
    private static String url;
    @Value("${spring.datasource.username}")
    private static String username;
    @Value("${spring.datasource.password}")
    private static String password;;
    private static DataSource dataSource;

    @Bean
    public static void druidDataSource(){
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setUrl(url);
        dataSource1.setUsername(username);
        dataSource1.setPassword(password);
        dataSource1.setDriverClassName(driverClassName);
        dataSource=dataSource1;
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null){
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try{
                stmt.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
