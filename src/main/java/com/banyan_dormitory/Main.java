package com.banyan_dormitory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage=stage;
        stage.setTitle("榕园物业系统!");
        changeView("/com/banyan_dormitory/fxml/Login.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void changeView(String fxml){
        try {
            // 使用绝对路径以确保资源能够被正确找到
            Parent root = FXMLLoader.load(Main.class.getResource(fxml));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("无法加载 FXML 文件: " + fxml);
        }
    }
}