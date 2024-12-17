package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.model.User;
import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class SendRequestPanelController {
    @FXML
    private TextArea RequestInput;

    @FXML
    private Button SubmitRequestButton;

    public void initialize() {
        SubmitRequestButton.setOnAction(event -> {
            DatabaseUtil.insertStudentRequest(UserPanelController.user.getId(),/* adminId*/"123456",RequestInput.getText());
            System.out.println("Request submitted");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("成功");
            alert.setContentText("请求已提交");
            alert.showAndWait();
            RequestInput.clear();
        });
    }
}
