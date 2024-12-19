package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class ChangePasswordController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField confirmInput;

    @FXML
    private TextField passwordInput;

    public void initialize(){
        cancelButton.setOnAction(actionEvent -> {
            confirmInput.clear();
            passwordInput.clear();

            // 获取当前窗口并关闭
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });
        confirmButton.setOnAction(actionEvent -> {
            if(confirmInput.getText().equals(passwordInput.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "修改成功");
                alert.showAndWait();

               DatabaseUtil.changePassword(UserPanelController.user.getId(), passwordInput.getText());
               UserPanelController.user.setPassword(passwordInput.getText());

                // 获取当前窗口并关闭
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "两次输入密码不一致");
                alert.showAndWait();
            }
        });
    }
}
