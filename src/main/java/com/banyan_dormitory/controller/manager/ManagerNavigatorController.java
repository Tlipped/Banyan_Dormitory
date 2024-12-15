package com.banyan_dormitory.controller.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.util.Date;

public class ManagerNavigatorController {
    @FXML
    private StackPane public_container,checkin_container,student_management_container,examine_container;
    @FXML
    private Button public_announce,checkin,student_management,examine;
    private StackPane currentSelectedContainer;
    @FXML
    private TextArea accouncement_edit;
    @FXML
    private Label error;
    @FXML
    private DatePicker date_edit;
    public void initialize(){
        setupButton(public_announce, public_container);
        setupButton(checkin, checkin_container);
        setupButton(student_management, student_management_container);
        setupButton(examine, examine_container);

        // 默认选中第一个按钮
        selectButton(public_container);
    }
    public ManagerNavigatorController() {
        // 默认构造函数
    }

    private void setupButton(Button button, StackPane container) {
        button.setOnMouseEntered(event -> {
            if (currentSelectedContainer != container) {
                container.setStyle("-fx-border-color:  rgba(234,251,226,0.93);");
            }
        });

        button.setOnMouseExited(event -> {
            if (currentSelectedContainer != container) {
                container.setStyle("-fx-background-color: transparent;");
            }
        });

        button.setOnAction(event -> {
            selectButton(container);
        });
    }

    private void selectButton(StackPane newSelectedContainer) {
        if (currentSelectedContainer != null) {
            currentSelectedContainer.setStyle("-fx-background-color: transparent;");
        }
        currentSelectedContainer = newSelectedContainer;
        currentSelectedContainer.setStyle("-fx-border-color: rgba(234,251,226,0.93);");
    }

    public void release_announcement_action(ActionEvent actionEvent) {
        String accouncement=accouncement_edit.getText();
        String date=date_edit.getEditor().getText();
        if(accouncement.isEmpty())
        {
            error.setText("公告不能为空");
            error.setVisible(true);
            return;
        }
        else if (date.isEmpty())
        {
            error.setText("日期不能为空");
            error.setVisible(true);
            return;
        }
        else error.setVisible(false);
        System.out.println(date_edit.getEditor().getText()+accouncement_edit.getText());
    }
}
