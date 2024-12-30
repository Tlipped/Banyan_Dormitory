package com.banyan_dormitory.controller;
import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.IOException;
import com.banyan_dormitory.controller.student.UserPanelController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

public class LoginController {
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField account;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;
    @FXML
    private StackPane content;

    @FXML
    public void initialize(){
        registerButton.setCursor(Cursor.HAND);
        loginButton.setCursor(Cursor.HAND);
        // 为账号和密码字段添加键盘事件监听器
        account.setOnKeyPressed(this::handleKeyPress);
        password.setOnKeyPressed(this::handleKeyPress);

        loginButton.setDefaultButton(true);
    }
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            doLogin(new ActionEvent());
        }
    }
    @FXML
    public void doLogin(ActionEvent actionEvent){
        try {
            String accountText = account.getText().trim();
            String passwordText = password.getText().trim();

            if (StringUtil.isEmpty(accountText)) {
                showError("请输入账号");
                return;
            }
            if (StringUtil.isEmpty(passwordText)) {
                showError("请输入密码");
                return;
            }
            if (DatabaseUtil.verifyCredentials(accountText, passwordText) && account.getLength() == 8) {
                System.out.println("学生端登录成功");
                UserPanelController.receiveUserID(accountText);
                ViewManager.changeView("/com/banyan_dormitory/fxml/Student/Announcement.fxml");
            } else if (DatabaseUtil.verifyCredentials(accountText, passwordText) && account.getLength() == 6) {
                System.out.println("管理端登录成功");
//                ViewManager.changeView("/com/banyan_dormitory/fxml/Manager/manager_handleMessages.fxml");
                ViewManager.changeView("/com/banyan_dormitory/fxml/Manager/Manager_Navigator.fxml");
            } else {
                showError("账号或密码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("登录过程中出现错误: " + e.getMessage());
        }
    }

    @FXML
    public void doregister(ActionEvent actionEvent) {
        ViewManager.changeView("/com/banyan_dormitory/fxml/Register.fxml");
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

