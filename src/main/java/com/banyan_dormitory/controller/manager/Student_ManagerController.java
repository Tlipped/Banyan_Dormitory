package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.sql.*;

public class Student_ManagerController {

    @FXML private TextField room;
    @FXML private Button search;
    @FXML private Button add;

    @FXML private Label name1, id1, score1;
    @FXML private Button changeScore1, delete1;

    @FXML private Label name2, id2, score2;
    @FXML private Button changeScore2, delete2;

    @FXML private Label name3, id3, score3;
    @FXML private Button changeScore3, delete3;

    @FXML private Label name4, id4, score4;
    @FXML private Button changeScore4, delete4;

    @FXML
    public void initialize() {
        search.setOnAction(event -> loadRoomData());

        // 绑定删除按钮事件
        delete1.setOnAction(event -> showDeleteConfirmation("1"));
        delete2.setOnAction(event -> showDeleteConfirmation("2"));
        delete3.setOnAction(event -> showDeleteConfirmation("3"));
        delete4.setOnAction(event -> showDeleteConfirmation("4"));

        // 绑定修改分数按钮事件
        changeScore1.setOnAction(event -> showChangeScoreDialog("1"));
        changeScore2.setOnAction(event -> showChangeScoreDialog("2"));
        changeScore3.setOnAction(event -> showChangeScoreDialog("3"));
        changeScore4.setOnAction(event -> showChangeScoreDialog("4"));

        // 添加学生
        add.setOnAction(event -> showAddStudentDialog());

        changeScore1.setCursor(Cursor.HAND);
        delete1.setCursor(Cursor.HAND);
        changeScore2.setCursor(Cursor.HAND);
        delete2.setCursor(Cursor.HAND);
        changeScore3.setCursor(Cursor.HAND);
        delete3.setCursor(Cursor.HAND);
        changeScore4.setCursor(Cursor.HAND);
        delete4.setCursor(Cursor.HAND);
        search.setCursor(Cursor.HAND);
        add.setCursor(Cursor.HAND);
    }

    private void loadRoomData() {
        // 获取用户输入的房间号
        String roomNumber = room.getText();
        if (room.getText() == null || room.getText().trim().isEmpty()) {
            showAlerttOnce("输入错误", "请输入有效宿舍号");
            return;
        }
        String query = "SELECT * FROM user WHERE room = ?";

        // 清空所有显示
        resetFields();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                String bed = rs.getString("bed");
                String studentName = rs.getString("name");
                String studentId = rs.getString("id");
                String studentScore = rs.getString("score");
                count++;

                switch (bed) {
                    case "1":
                        updateField(name1, id1, score1, changeScore1, delete1, studentName, studentId, studentScore);
                        break;
                    case "2":
                        updateField(name2, id2, score2, changeScore2, delete2, studentName, studentId, studentScore);
                        break;
                    case "3":
                        updateField(name3, id3, score3, changeScore3, delete3, studentName, studentId, studentScore);
                        break;
                    case "4":
                        updateField(name4, id4, score4, changeScore4, delete4, studentName, studentId, studentScore);
                        break;
                }
            }
            if (count < 4) {
                add.setDisable(false);
            } else {
                add.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void resetFields() {
        resetField(name1, id1, score1, changeScore1, delete1);
        resetField(name2, id2, score2, changeScore2, delete2);
        resetField(name3, id3, score3, changeScore3, delete3);
        resetField(name4, id4, score4, changeScore4, delete4);
    }

    private void resetField(Label name, Label id, Label score, Button changeScore, Button delete) {
        name.setText("床位为空");
        id.setText("");
        score.setText("");
        changeScore.setDisable(true);
        delete.setDisable(true);
    }

    private void updateField(Label name, Label id, Label score, Button changeScore, Button delete,
                             String studentName, String studentId, String studentScore) {
        name.setText(studentName);
        id.setText(studentId);
        score.setText(studentScore);
        changeScore.setDisable(false);
        delete.setDisable(false);
    }

    private void showDeleteConfirmation(String bedNumber) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("删除确认");

        // 确认消息样式
        Label message = new Label("确定删除该学生的信息？");
        message.setStyle("-fx-font-size: 20px; -fx-text-fill: green; -fx-font-weight: bold;");

        // 确定按钮样式
        Button confirmButton = new Button("确定");
        confirmButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");
        confirmButton.setOnAction(event -> {
            deleteStudentFromDatabase(bedNumber);
            dialog.close();
            search.fire(); // 刷新数据
        });

        // 取消按钮样式
        Button cancelButton = new Button("取消");
        cancelButton.setStyle(
                "-fx-background-color: #A5D6A7; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");
        cancelButton.setOnAction(event -> dialog.close());

        // 横向布局按钮
        HBox buttons = new HBox(30, confirmButton, cancelButton);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);

        // 对话框整体样式
        VBox layout = new VBox(30, message, buttons);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setStyle("-fx-background-color: #E8F5E9; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10;");

        // 设置场景
        Scene scene = new Scene(layout, 350, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void deleteStudentFromDatabase(String bedNumber) {
        String query = "UPDATE user SET room = NULL, score = NULL, bed = NULL WHERE room = ? AND bed = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, room.getText());
            pstmt.setString(2, bedNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showChangeScoreDialog(String bedNumber) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("加/扣分");

        // 提示信息和输入框横向放置
        Label prompt = new Label("分数增减量：");
        prompt.setStyle("-fx-font-size: 18px; -fx-text-fill: green; -fx-font-weight: bold;");

        TextField scoreInput = new TextField();
        scoreInput.setPromptText("例如: +10 或 -5");
        scoreInput.setStyle("-fx-font-size: 16px; -fx-background-radius: 10;");

        HBox inputLayout = new HBox(10, prompt, scoreInput);
        inputLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // 确定按钮
        Button confirmButton = new Button("确定");
        confirmButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");
        confirmButton.setOnAction(event -> {
            try {
                int delta = Integer.parseInt(scoreInput.getText().trim());
                updateStudentScore(bedNumber, delta);
                dialog.close();
                search.fire(); // 刷新显示
            } catch (NumberFormatException e) {
                showAlerttOnce("输入错误", "请输入有效的分数增减量，例如 +10 或 -5");
            }
        });

        // 取消按钮
        Button cancelButton = new Button("取消");
        cancelButton.setStyle(
                "-fx-background-color: #A5D6A7; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10 20;");
        cancelButton.setOnAction(event -> dialog.close());

        // 按钮布局
        HBox buttons = new HBox(30, confirmButton, cancelButton);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);

        // 窗口整体布局
        VBox layout = new VBox(30, inputLayout, buttons);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setStyle("-fx-background-color: #E8F5E9; -fx-padding: 30; -fx-border-radius: 10; -fx-background-radius: 10;");

        // 设置场景
        Scene scene = new Scene(layout, 400, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void updateStudentScore(String bedNumber, int delta) {
        String querySelect = "SELECT score FROM user WHERE room = ? AND bed = ?";
        String queryUpdate = "UPDATE user SET score = ? WHERE room = ? AND bed = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(querySelect);
             PreparedStatement pstmtUpdate = conn.prepareStatement(queryUpdate)) {

            // 获取当前分数
            pstmtSelect.setString(1, room.getText());
            pstmtSelect.setString(2, bedNumber);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                int currentScore = rs.getInt("score");
                int newScore = currentScore + delta;

                // 分数边界检查
                if (newScore > 100) newScore = 100;
                if (newScore < 0) newScore = 0;

                // 更新分数
                pstmtUpdate.setInt(1, newScore);
                pstmtUpdate.setString(2, room.getText());
                pstmtUpdate.setString(3, bedNumber);
                pstmtUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 显示添加学生信息的弹窗
    private void showAddStudentDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("添加学生");

        // 创建输入组件
        Label roomLabel = new Label("房间号：");
        roomLabel.setStyle("-fx-font-size: 16px;"); // 设置字体大小
        // 设置默认房间号为201
        TextField roomInput = new TextField(room.getText());

        roomInput.setDisable(true); // 禁止编辑房间号
        roomInput.setStyle("-fx-font-size: 16px; -fx-pref-height: 30px;"); // 设置字体大小和高度

        Label bedLabel = new Label("床位号：");
        bedLabel.setStyle("-fx-font-size: 16px;");
        TextField bedInput = new TextField();
        bedInput.setPromptText("请输入床位 (1~4)");
        bedInput.setStyle("-fx-font-size: 16px; -fx-pref-height: 30px;");

        Label idLabel = new Label("学号：    ");
        idLabel.setStyle("-fx-font-size: 16px;");
        TextField idInput = new TextField();
        idInput.setPromptText("请输入学号");
        idInput.setStyle("-fx-font-size: 16px; -fx-pref-height: 30px;");

        Label scoreLabel = new Label("表现分：");
        scoreLabel.setStyle("-fx-font-size: 16px;");
        TextField scoreInput = new TextField("100"); // 表现分默认100
        scoreInput.setStyle("-fx-font-size: 16px; -fx-pref-height: 30px;");

        // 确定与取消按钮
        Button confirmButton = new Button("确定");
        confirmButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px;");
        confirmButton.setOnAction(event -> {
            handleAddStudent(roomInput.getText(), bedInput.getText(), idInput.getText(), scoreInput.getText(), dialog);
        });

        Button cancelButton = new Button("取消");
        cancelButton.setStyle("-fx-background-color: #A5D6A7; -fx-text-fill: white; -fx-font-size: 18px;");
        cancelButton.setOnAction(event -> dialog.close());

        HBox buttons = new HBox(20, confirmButton, cancelButton);
        buttons.setAlignment(javafx.geometry.Pos.CENTER);

        // 布局
        VBox layout = new VBox(15,
                new HBox(10, roomLabel, roomInput) {{
                    setAlignment(javafx.geometry.Pos.CENTER);
                }},
                new HBox(10, bedLabel, bedInput) {{
                    setAlignment(javafx.geometry.Pos.CENTER);
                }},
                new HBox(10, idLabel, idInput) {{
                    setAlignment(javafx.geometry.Pos.CENTER);
                }},
                new HBox(10, scoreLabel, scoreInput) {{
                    setAlignment(javafx.geometry.Pos.CENTER);
                }},
                buttons
        );
        layout.setStyle("-fx-padding: 20; -fx-background-color: #e8f5e9;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(layout, 400, 300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // 处理添加学生的逻辑
    private void handleAddStudent(String roomNumber, String bedNumber, String studentId, String score, Stage dialog) {
        if (bedNumber.isEmpty() || studentId.isEmpty()) {
            showAlerttOnce("错误", "床位号和学号不能为空！");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // 检查此房间号和床位是否已分配
            String checkRoomBedQuery = "SELECT id FROM user WHERE room = ? AND bed = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkRoomBedQuery)) {
                pstmt.setString(1, roomNumber);
                pstmt.setString(2, bedNumber);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    showAlerttOnce("已分配", "房间 " + roomNumber + " 床位 " + bedNumber + " 已分配给学号：" + rs.getString("id"));
                    return;
                }
            }

            // 检查此学号是否存在
            String checkIdQuery = "SELECT room, bed FROM user WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkIdQuery)) {
                pstmt.setString(1, studentId);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next() || studentId.length()==6) {
                    showAlerttOnce("错误", "学号 " + studentId + " 不存在！");
                    return;
                } else {
                    String existingRoom = rs.getString("room");
                    String existingBed = rs.getString("bed");
                    if (existingRoom != null && !existingRoom.isEmpty() && existingBed != null && !existingBed.isEmpty()) {
                        showAlerttOnce("已分配", "学号 " + studentId + " 已分配宿舍：房间 " + existingRoom + " 床位 " + existingBed);
                        return;
                    }
                }
            }
            if(Integer.parseInt(score)>100 || Integer.parseInt(score)<0){
                showAlerttOnce("错误", "分数设置请在0到100之间！");
                return;
            }
            // 更新数据库，将room和bed分配给学生
            String updateQuery = "UPDATE user SET room = ?, bed = ?, score = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, roomNumber);
                pstmt.setString(2, bedNumber);
                pstmt.setString(3, score);
                pstmt.setString(4, studentId);
                pstmt.executeUpdate();
                showAlerttOnce("成功", "学生信息已成功添加！");
                search.fire(); // 自动触发搜索按钮，刷新数据
                dialog.close(); // 添加成功后关闭主窗口
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlerttOnce("错误", "数据库操作失败：" + e.getMessage());
        }
    }

    // 显示仅一次的警告/消息弹窗
    private boolean alertDisplayed = false; // 标记是否已显示过弹窗
    private void showAlerttOnce(String title, String content) {
        if (!alertDisplayed) { // 只允许第一个警告弹窗显示
            alertDisplayed = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // 信息类型警告框
            alert.setTitle(title);        // 设置标题
            alert.setHeaderText(null);    // 不显示头部文本
            alert.setContentText(content); // 设置内容文本

            alert.showAndWait(); // 等待用户关闭弹窗

            alertDisplayed = false; // 重置标志位，以便下次可以再次弹出
        }
    }
}