package com.banyan_dormitory.controller;
import com.banyan_dormitory.Main;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class LoginController {
    @FXML
    private TextField account;

    @FXML
    private TextField password;
    @FXML
    private Label error;
    @FXML
    public void initialize(){

    }
    @FXML
    public void doLogin(){
        String accountText=account.getText().trim();
        String passwordText= password.getText().trim();
        if(StringUtil.isEmpty(accountText)){
            error.setText("请输入账户！！");
            error.setVisible(true);
            return;
        }
        if(StringUtil.isEmpty(passwordText)){
            error.setText("请输入密码！！");
            error.setVisible(true);
            return;
        }
        if(verifyCredentials(accountText, passwordText)){
            System.out.println("登录成功");
            Main.changeView("/com/banyan_dormitory/fxml/Student/Announcement.fxml");
        }else{
            error.setText("账户或密码错误");
            error.setVisible(true);
        }
    }
    private boolean verifyCredentials(String account, String password) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM account_password WHERE account = ? AND password = ?";
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
}
