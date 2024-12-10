package com.banyan_dormitory.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;/**
 * DatabaseUtil 类提供静态方法来获取数据库连接。
 * 它从 resources 文件夹中的 database.properties 文件加载数据库配置，
 */
public class DatabaseUtil {

    // 静态变量用于存储数据库连接信息
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_DRIVER;

    /**
     * 静态初始化块，在类加载时执行一次。
     * 该块负责加载数据库配置，并设置静态变量。
     */
    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("Sorry, unable to find database.properties");
            }

            Properties prop = new Properties();
            prop.load(input);

            // 设置静态变量，全部从 properties 文件中读取
            DB_URL = prop.getProperty("db.url");
            DB_USER = prop.getProperty("db.username");
            DB_PASSWORD = prop.getProperty("db.password");
            DB_DRIVER = prop.getProperty("db.driver");

            // 加载 JDBC 驱动程序
            Class.forName(DB_DRIVER);

        } catch (IOException | ClassNotFoundException ex) {
            // 捕获并打印可能发生的 IO 或类找不到异常
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * 获取与数据库的连接。
     *
     * @return 数据库连接对象
     * @throws SQLException 如果发生 SQL 错误
     */
    public static Connection getConnection() throws SQLException {
        // 使用静态变量创建并返回一个数据库连接
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}