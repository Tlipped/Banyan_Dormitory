package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

import java.sql.*;

public class HomepageAController {

    @FXML
    private VBox infoContainer; // 容器，用于动态添加信息行

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() {
        // 设置滑动条为绿色主题
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: #C2E4C0;");
        scrollPane.getStyleClass().add("green-scrollbar");

        loadInformationFromDatabase();
    }

    private void loadInformationFromDatabase() {
        String query = "SELECT id, content, date FROM information";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            infoContainer.getChildren().clear(); // 清空旧内容

            while (rs.next()) {
                int id = rs.getInt("id");
                String content = rs.getString("content");
                String date = rs.getString("date");

                // 如果content超出20字符，截断并添加 "..."
                String displayContent = content.length() > 20 ? content.substring(0, 20) + "..." : content;

                // 创建一行信息
                HBox infoRow = createInfoRow(id, displayContent, date, content, content.length() > 20);
                infoContainer.getChildren().add(infoRow);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private HBox createInfoRow(int id, String displayContent, String date, String fullContent, boolean showDetailButton) {
        HBox row = new HBox(25);
        row.setStyle("-fx-background-color: #58C272; -fx-background-radius: 10; -fx-padding: 10;");
        row.setPadding(new Insets(10));
        row.setPrefWidth(780);

        // ID 标签
        Label idLabel = new Label(id + ".");
        idLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;"); // 调小字体
        idLabel.setPrefWidth(40);

        // 内容标签
        Label contentLabel = new Label(displayContent);
        contentLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        contentLabel.setPrefWidth(420);

        // 日期标签
        Label dateLabel = new Label(date);
        dateLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;"); // 调小字体112

        // 查看详情按钮（只有当内容超出20字符时才显示）
        if (showDetailButton) {
            Button viewButton = new Button("查看详情");
            viewButton.setStyle("-fx-background-color: #A5D6A7; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 5 10 5 10;"); // 缩小字体和按钮内部间距
            viewButton.setOnAction(event -> showFullContentDialog(fullContent));
            row.getChildren().addAll(idLabel, contentLabel, dateLabel, viewButton);
        } else {
            row.getChildren().addAll(idLabel, contentLabel, dateLabel);
        }
        return row;
    }

    private void showFullContentDialog(String fullContent) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("详细信息");

        // 主布局
        VBox dialogLayout = new VBox(20);
        dialogLayout.setStyle("-fx-background-color: #E8F5E9; -fx-padding: 20; -fx-background-radius: 10;");

        // 内容标签
        Label contentLabel = new Label(fullContent);
        contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #2E7D32;");
        contentLabel.setWrapText(true);

        // 按钮布局（将关闭按钮单独放到底部）
        HBox buttonContainer = new HBox();
        buttonContainer.setStyle("-fx-alignment: center;");
        buttonContainer.setPadding(new Insets(10, 0, 0, 0)); // 增加顶部间距以靠近底部

        Button closeButton = new Button("关闭");
        closeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        closeButton.setOnAction(event -> dialog.close());

        buttonContainer.getChildren().add(closeButton);

        // 添加组件到布局
        dialogLayout.getChildren().addAll(contentLabel, buttonContainer);

        // 创建场景并显示
        Scene scene = new Scene(dialogLayout, 400, 200, Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
