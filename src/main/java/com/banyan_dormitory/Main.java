package com.banyan_dormitory;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class Main extends Application {
    private static Stage stage;
    private static CountDownLatch latch = new CountDownLatch(1);
    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.stage = primaryStage;
        primaryStage.setTitle("榕园物业系统");
        // 在启动时测试数据库连接
        testDatabaseConnection();
        URL resource = getClass().getResource("/com/banyan_dormitory/images/LOGO.png");
        if (resource != null) {
            primaryStage.getIcons().add(new Image(resource.toExternalForm()));
        }
        ViewManager.setStage(primaryStage);
        ViewManager.changeView("/com/banyan_dormitory/fxml/Login.fxml");
        // 固定窗口大小
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
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