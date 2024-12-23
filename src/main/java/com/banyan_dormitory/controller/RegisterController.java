package com.banyan_dormitory.controller;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class RegisterController {
    @FXML
    public Button RegisterButton;  // 注意这里应该是 RegisterController 而不是 LoginController
    public Label error;
    @FXML
    public Button BackButton;
    @FXML
    private TextField reAccount;
    @FXML
    private TextField rePassword;
    @FXML
    private TextField rename;
    @FXML
    private TextField reschool;
    @FXML
    private Label rank;
    @FXML
    private StackPane content;

    @FXML
    public void initialize() {
        // 初始化时隐藏 rank 标签
        rank.setVisible(false);
        RegisterButton.setCursor(Cursor.HAND);
        BackButton.setCursor(Cursor.HAND);
        error.setVisible(false);
        // 添加监听器到密码字段
        rePassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String passwordStrength = checkPasswordStrength(newValue);
                Platform.runLater(() -> {
                    rank.setText("密码等级: " + passwordStrength);
                    rank.setVisible(true);  // 显示 rank 标签
                });
            } else {
                rank.setVisible(false);  // 如果密码为空，隐藏 rank 标签
            }
        });
    }

    private String checkPasswordStrength(String password) {
        // 定义正则表达式
        String regexOnlyDigits = "^\\d+$";  // 只包含数字
        String regexDigitsAndLetters = "^(?=.*[0-9])(?=.*[a-zA-Z]).*$";  // 包含数字和字母
        String regexStrong = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^\\w\\s]).*$";  // 包含数字、字母和特殊字符

        if (password.matches(regexOnlyDigits)) {
            return "弱";
        } else if (password.matches(regexDigitsAndLetters)) {
            return "中等";
        } else if (password.matches(regexStrong)) {
            return "强";
        } else {
            return "未知";
        }
    }

    @FXML
    public void backLogin(ActionEvent event) {
        ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
    }

    @FXML
    public void register(ActionEvent actionEvent) {
        if (StringUtil.isEmpty(reAccount.getText().trim())) {
            showError("请输入账号！");
            return;
        }
        int account;
        try {
            account = Integer.parseInt(reAccount.getText().trim());
        } catch (NumberFormatException e) {
            showError("账号必须是数字！");
            return;
        }
        if (DatabaseUtil.isAccountExists(account)) {
            showError("该账号已存在！");
            return;
        }
        String password = rePassword.getText().trim();
        String name = rename.getText().trim();
        String school = reschool.getText().trim();

        if (StringUtil.isEmpty(password)) {
            showError("请输入密码！");
            return;
        }
        if (StringUtil.isEmpty(name)) {
            showError("请输入名字！");
            return;
        }
        if (StringUtil.isEmpty(school)) {
            showError("请输入学院！");
            return;
        }
        if (DatabaseUtil.registerUser(account, password, name, school)) {
            showSuccessAlert("注册成功！");
            ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
        } else {
            showError("注册失败，请重试！");
        }
    }

    private void showError(String message) {
        error.setText(message);
        error.setVisible(true);
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("成功");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}