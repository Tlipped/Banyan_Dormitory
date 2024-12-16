package com.banyan_dormitory.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ViewManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        ViewManager.stage = stage;
    }

    public static void changeView(String fxml){
        try {
            // 使用绝对路径以确保资源能够被正确找到
            Parent root = FXMLLoader.load(ViewManager.class.getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 FXML 文件: " + fxml);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load view.");
            alert.setContentText("The system could not load the requested view. Please try again or contact support.");
            alert.showAndWait();
        }
    }
}