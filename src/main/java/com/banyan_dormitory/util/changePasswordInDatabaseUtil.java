package com.banyan_dormitory.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class changePasswordInDatabaseUtil {
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
}
