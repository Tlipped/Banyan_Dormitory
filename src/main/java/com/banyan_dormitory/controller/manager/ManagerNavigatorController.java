package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.ViewManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class ManagerNavigatorController {
    @FXML
    public ComboBox<String> logout;
    public Label name;
    @FXML
    private StackPane public_container,checkin_container,student_management_container,examine_container;
    @FXML
    private Button public_announce,checkin,student_management,examine;
    @FXML
    private StackPane content;
    private StackPane currentSelectedContainer;
    @FXML
    private Label error;

    public void initialize() throws SQLException {
        if (content == null) {
            throw new IllegalStateException("content is not injected by FXMLLoader");
        }
        setupButton(public_announce, public_container,"/com/banyan_dormitory/fxml/Manager/managerAccouncement.fxml");
        setupButton(checkin, checkin_container,"/com/banyan_dormitory/fxml/Manager/Visitor_Check.fxml");
        setupButton(student_management, student_management_container,"/com/banyan_dormitory/fxml/Manager/Student_manager.fxml");
        setupButton(examine, examine_container,"/com/banyan_dormitory/fxml/Manager/manager_handleMessages.fxml");

        // 默认选中
        selectButton(public_container);
        loadContent("/com/banyan_dormitory/fxml/Manager/managerAccouncement.fxml");
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
        logout.setCursor(Cursor.HAND);
        public_announce.setCursor(Cursor.HAND);
        checkin.setCursor(Cursor.HAND);
        student_management.setCursor(Cursor.HAND);
        examine.setCursor(Cursor.HAND);
    }

    private void performLogout() {
        System.out.println("管理员已登出");
        ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
    }

    public ManagerNavigatorController() {
        // 默认构造函数

    }
    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            if (content != null) {
                content.getChildren().setAll(view);
            } else {
                System.err.println("content is null when trying to load content.");
            }
        } catch (IOException e) {
            System.err.println("无法加载 FXML 文件: " + fxmlPath);
            e.printStackTrace();
            error.setText("无法加载页面，请稍后再试。");
        }
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

    private void selectButton(StackPane newSelectedContainer) {
        if (currentSelectedContainer != null) {
            currentSelectedContainer.setStyle("-fx-background-color: transparent;");
        }
        currentSelectedContainer = newSelectedContainer;
        currentSelectedContainer.setStyle("-fx-background-color: rgba(234,251,226,0.4);");
    }
}
