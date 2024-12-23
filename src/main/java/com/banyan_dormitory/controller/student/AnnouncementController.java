package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.Main;
import com.banyan_dormitory.model.User;
import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class AnnouncementController {
    @FXML
    public ComboBox<String> logout;
    public Label name;
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
        setupButton(homepage, homepageContainer, "/com/banyan_dormitory/fxml/Student/Homepage_announcement.fxml");
        setupButton(apply, applyContainer, "/com/banyan_dormitory/fxml/Student/SendRequestPanel.fxml");
        setupButton(feedback, feedbackContainer, "/com/banyan_dormitory/fxml/Student/RequestFeedbackPanel.fxml");
        setupButton(user_center, user_centerContainer, "/com/banyan_dormitory/fxml/Student/UserPanel.fxml");
        name.setText(UserPanelController.user.getName());
        // 默认选中第一个按钮
        selectButton(homepageContainer);
        loadContent("/com/banyan_dormitory/fxml/Student/Homepage_announcement.fxml");
        logout.getItems().add("登出");
        logout.setOnAction(event -> {
            String selectedItem = logout.getSelectionModel().getSelectedItem();
            if ("登出".equals(selectedItem)) {
                // 延迟一下
                PauseTransition delay = new PauseTransition(Duration.seconds(0.2));
                delay.setOnFinished(event1 -> performLogout());
                delay.play();
            }
        });
    }

    private void performLogout() {
        System.out.println("用户已登出");
        ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
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
