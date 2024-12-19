package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.model.User;
import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class SendRequestPanelController {
    @FXML
    private TextArea RequestInput;

    @FXML
    private Button SubmitRequestButton;

    @FXML
    private ComboBox<String> requestTypeSelect;

    public void initialize() {
        requestTypeSelect.getItems().addAll("报修申请", "分数相关", "投诉", "建议", "其他");

        SubmitRequestButton.setOnAction(event -> {
            if(RequestInput.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("错误");
                alert.setContentText("请求内容不能为空");
                alert.showAndWait();
                return;
            }
            if(requestTypeSelect.getValue()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("错误");
                alert.setContentText("请选择请求类型");
                alert.showAndWait();
                return;
            }
            DatabaseUtil.insertStudentRequest(UserPanelController.user.getId(),/* adminId*/"123456",RequestInput.getText(),requestTypeSelect.getValue());
            System.out.println("Request submitted");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("成功");
            alert.setContentText("请求已提交");
            alert.showAndWait();
            RequestInput.clear();
        });
    }
}
