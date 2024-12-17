package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;

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
        setupButton(homepage, homepageContainer, "/com/banyan_dormitory/fxml/Student/UserPanel.fxml");
        setupButton(apply, applyContainer, "/com/banyan_dormitory/fxml/Student/SendRequestPanel.fxml");
        setupButton(feedback, feedbackContainer, "/com/banyan_dormitory/fxml/Student/RequestFeedbackPanel.fxml");
        setupButton(user_center, user_centerContainer, "/com/banyan_dormitory/fxml/Student/UserPanel.fxml");

        // 默认选中第一个按钮
        selectButton(homepageContainer);
//        loadContent("/com/banyan_dormitory/fxml/Student/HomePage.fxml");
    }
    public AnnouncementController(){

    }
    private void setupButton(Button button, StackPane container, String fxmlPath) {
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
            loadContent(fxmlPath);
        });
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            content.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void selectButton(StackPane newSelectedContainer) {
        if (currentSelectedContainer != null) {
            currentSelectedContainer.setStyle("-fx-background-color: transparent;");
        }
        currentSelectedContainer = newSelectedContainer;
        currentSelectedContainer.setStyle("-fx-border-color: rgba(234,251,226,0.93);");
    }
}
