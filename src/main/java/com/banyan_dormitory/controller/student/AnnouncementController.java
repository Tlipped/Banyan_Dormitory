package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class AnnouncementController {
    @FXML
    private StackPane homepageContainer, applyContainer, feedbackContainer, user_centerContainer;
    @FXML
    private Button homepage;
    @FXML
    private Button apply;
    @FXML
    private Button feedback;
    @FXML
    private Button user_center;
    private StackPane currentSelectedContainer;
    @FXML
    private StackPane content; // 右边显示内容的 StackPane
    public void initialize(){
        setupButton(homepage, homepageContainer);
        setupButton(apply, applyContainer);
        setupButton(feedback, feedbackContainer);
        setupButton(user_center, user_centerContainer);

        // 默认选中第一个按钮
        selectButton(homepageContainer);
    }
    public AnnouncementController() {
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
            Main.changeView("/com/banyan_dormitory/fxml/Student/UserPanel.fxml");
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
