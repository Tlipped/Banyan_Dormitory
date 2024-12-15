package com.banyan_dormitory.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadUserFromDatabaseUtil {

    public User getUser(String userid) {
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
}

