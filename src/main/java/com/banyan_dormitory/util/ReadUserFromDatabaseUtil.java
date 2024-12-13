package com.banyan_dormitory.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadUserFromDatabaseUtil {

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        // SQL 查询语句
        String sql = "SELECT * FROM `user`";

        // 执行查询
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // 提取数据
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("password"),
                        rs.getString("school"),
                        rs.getString("score"),
                        rs.getString("room"),
                        rs.getString("bed")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
}

