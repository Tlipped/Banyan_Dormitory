package com.banyan_dormitory;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {
    private static Stage stage;

    // 添加静态初始化块来测试资源加载
    static {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find database.properties");
            } else {
                System.out.println("database.properties found and loaded successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Main.stage=stage;
        stage.setTitle("榕园物业系统!");

        // 在启动时测试数据库连接
        testDatabaseConnection();

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
    /**
     * 测试数据库连接的方法。
     */
    private void testDatabaseConnection() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("数据库连接成功！");
            } else {
                System.err.println("数据库连接失败或已关闭！");
            }
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}