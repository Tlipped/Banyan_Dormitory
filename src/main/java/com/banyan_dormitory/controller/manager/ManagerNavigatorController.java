package com.banyan_dormitory.controller.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ManagerNavigatorController {
    @FXML
    private StackPane public_container,checkin_container,student_management_container,examine_container;
    @FXML
    private Button public_announce,checkin,student_management,examine;
    private StackPane currentSelectedContainer;
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
}
