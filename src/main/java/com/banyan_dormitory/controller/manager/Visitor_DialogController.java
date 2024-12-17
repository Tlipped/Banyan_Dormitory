package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // 确保使用的是 JavaFX 的 ActionEvent
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Visitor_DialogController {
    @FXML
    private javafx.scene.control.TextField name,visitor_id,phone_number,reason,date;
    private VisitorCheckController visitorCheckController;

    public void setVisitorCheckController(VisitorCheckController controller) {
        this.visitorCheckController = controller;
    }
    @FXML
    public void Addcomfirm(ActionEvent actionEvent) {
        if (name.getText().isEmpty() || visitor_id.getText().isEmpty() || phone_number.getText().isEmpty() ||
                reason.getText().isEmpty() || date.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("输入存在空");
            alert.setHeaderText("输入不合法");
            alert.setContentText("请确保填写必要信息");
            alert.showAndWait();
            return;
        }

        String[] dateTimeParts = StringUtil.splitAndConvertDateTime(date.getText());
        if (dateTimeParts == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Date/Time Format Error");
            alert.setContentText("请输入日期格式 (yyyy-MM-dd HH:mm).");
            alert.showAndWait();
            return;
        }

        String insertSQL = "INSERT INTO visitor (name, visitor_id, phone_number, date, time, reason) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, name.getText());
            pstmt.setString(2, visitor_id.getText());
            pstmt.setString(3, phone_number.getText());
            pstmt.setDate(4, java.sql.Date.valueOf(dateTimeParts[0])); // Insert date part
            pstmt.setTime(5, java.sql.Time.valueOf(dateTimeParts[1] + ":00")); // Insert time part with seconds set to 00
            pstmt.setString(6, reason.getText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
                //执行一次查询更新
                if (visitorCheckController != null) {
                    visitorCheckController.onSearchButtonClick(new ActionEvent(visitorCheckController, null));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CloseDialog(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
