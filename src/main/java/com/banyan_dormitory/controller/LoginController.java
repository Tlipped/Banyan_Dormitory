package com.banyan_dormitory.controller;
import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import com.banyan_dormitory.controller.student.UserPanelController;
import javafx.scene.layout.StackPane;

public class LoginController {
    @FXML
    public TextField reAccount;
    @FXML
    public TextField rePassword;
    @FXML
    public TextField rename;
    @FXML
    public TextField reschool;
    @FXML
    private TextField account;
    @FXML
    private TextField password;
    @FXML
    private Label error;
    @FXML
    private StackPane content;
    @FXML

    public void initialize(){

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
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Register.fxml"));
//            Node registerNode = loader.load();
//            content.getChildren().setAll(registerNode);
    }
    @FXML
    public void backLogin(ActionEvent event) {
        ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
    }
    @FXML
    public void register(ActionEvent actionEvent) {
        String account = reAccount.getText().trim();
        String password = rePassword.getText().trim();
        String name=rename.getText().trim();
        String school =reschool.getText().trim();
        if (StringUtil.isEmpty(account)) {
            showError("请输入账号！");
            return;
        }
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
        if (DatabaseUtil.registerUser(account, password,name,school)) {
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

