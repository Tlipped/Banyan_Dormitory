package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

public class ManagerAccouncement {
    @FXML
    private ListView<Label> accouncement_show;
    @FXML
    private Button releaseButton;
    public void initialize() throws SQLException {
        accouncement_show.getItems().clear();
        accouncement_show.setStyle("-fx-background-radius: 50;-fx-font-family: Arial");
        accouncement_show.setFixedCellSize(50);
        releaseButton.setCursor(Cursor.HAND);
        String sql="SELECT * FROM information order by id ";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            String str=Set.getString("content");
            int size=50-3*str.length();
            StringBuffer Sb=new StringBuffer(" ");
            while (size>=0)
            {
                Sb.append(' ');
                size--;
            }
            Label label=new Label(str+Sb+Set.getString("date"));
            label.setStyle("-fx-font-size: 24px;-fx-background-radius: 30;-fx-text-fill: black");

            accouncement_show.getItems().add(label);
        }
    }
    public ManagerAccouncement() {
        // 默认构造函数
    }

    public void handleReleaseButtonAction(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("发布公告");

        TextField textField = new TextField();
        textField.setPrefWidth(300);
        textField.setPrefHeight(200);
        textField.setStyle("-fx-font-family: Arial;-fx-background-radius: 30");
        DatePicker datePicker = new DatePicker();
        Button confirmButton = new Button("确认发布");

        confirmButton.setOnAction(e -> {
            if(datePicker.getValue()==null&&textField.getText().isEmpty())
            {
                showAlert("请输入公告和日期");
            }
            else if (datePicker.getEditor().getText().isEmpty()) {
                showAlert("请输入日期");
            } else if (textField.getText().isEmpty()) {
                showAlert("请输入公告");
            } else {
                showAlert("公告发布成功");
                // 获取当前本地日期时间
                LocalDateTime now = LocalDateTime.now();

                // 提取月、日、时、分、秒
                int month = now.getMonthValue();
                int day = now.getDayOfMonth();
                int hour = now.getHour();
                int minute = now.getMinute();
                int second = now.getSecond();
                // 按照自定义规则合并为一个整数
                System.out.println(hour);
                System.out.println(minute);
                System.out.println(second);
                int combinedValue = month * 1000000 + day * 10000 + hour * 100 + minute * 10 + second;

                String sql = "INSERT INTO information VALUES (?,?,?)";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql))
                {
                    pstmt.setInt(1,combinedValue);
                    pstmt.setString(2,textField.getText());
                    pstmt.setString(3,datePicker.getEditor().getText());
                    pstmt.execute();
                } catch (SQLException a) {
                    a.printStackTrace();
                }
                try {
                    initialize();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                popupStage.close();
            }
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(textField, datePicker, confirmButton);
        BackgroundFill background_fill = new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, null);
        layout.setBackground(new Background(background_fill));

        Scene scene = new Scene(layout, 300, 200);
        popupStage.setScene(scene);
        popupStage.show();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
