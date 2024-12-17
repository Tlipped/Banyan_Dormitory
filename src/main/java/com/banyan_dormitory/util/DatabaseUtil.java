package com.banyan_dormitory.util;

import com.banyan_dormitory.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;/**
 * DatabaseUtil 类提供静态方法来获取数据库连接。
 * 它从 resources 文件夹中的 database.properties 文件加载数据库配置，
 */
public class DatabaseUtil {

    // 静态变量用于存储数据库连接信息
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String DB_DRIVER;

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

    public static void changePassword(String id, String password) {
        // 从数据库中修改密码
        String sql = "UPDATE `user` SET `password` = ? WHERE `id` = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean verifyCredentials(String account, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM `user` WHERE id = ? AND password = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, account);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("数据库查询失败: " + e.getMessage());
        }
        return false;
    }
    public static boolean registerUser(String account, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO user (id, password) VALUES (?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, account);
                pstmt.setString(2, password);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("数据库插入失败: " + e.getMessage());
        }
        return false;
    }
    public static User getUser(String userid) {
        // SQL 查询语句
        String sql = "SELECT * FROM `user` where `id` = "+userid;

        User user = null;

        // 执行查询
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 提取数据
            rs.next();
            user = new User(
                    rs.getString("id"),
                    rs.getString("password"),
                    rs.getString("school"),
                    rs.getString("score"),
                    rs.getString("room"),
                    rs.getString("bed"),
                    rs.getString("name")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void updateUser(User user){
        String sql = "UPDATE `user` SET `name` = ? , school = ? WHERE `id` = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getSchool());
            pstmt.setString(3, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertStudentRequest(String from, String to, String content){
        String sql = "INSERT INTO message (`from`, `to`, content, status) VALUES (?, ?, ?, ?)";
        try (Connection conn =DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, from);
            pstmt.setString(2, to);
            pstmt.setString(3, content);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}