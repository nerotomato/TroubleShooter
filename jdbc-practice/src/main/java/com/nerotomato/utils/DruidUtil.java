package com.nerotomato.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Druid连接池工具类
 * Created by nero on 2021/4/27.
 */
public class DruidUtil {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        // 如何获得属性文件的输入流？
        // 通常情况下使用类的加载器的方式进行获取：
        Properties prop = new Properties();
        try {
            InputStream inputStream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            prop.load(inputStream);
            //数据源工厂.创建数据源
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        if (dataSource != null) {
            conn = dataSource.getConnection();
        } else {
            dataSource = getDataSource();
            conn = dataSource.getConnection();
        }
        return conn;
    }

    /**
     * 开始事务
     * @param conn
     */
    public static void beginTransaction(Connection conn){
        if(conn!=null){
            try {
                if(conn.getAutoCommit()){
                    conn.setAutoCommit(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提交事务
     * @param conn
     */
    public static void commitTransaction(Connection conn){
        if(conn!=null){
            try {
                if(!conn.getAutoCommit()){
                    conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 回滚事务
     * @param conn
     */
    public static void rollBackTransaction(Connection conn){
        if(conn!=null){
            try {
                if(!conn.getAutoCommit()){
                    conn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
