package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // 确保使用的是 JavaFX 的 ActionEvent
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Visitor_DialogController {
    @FXML
    public Button Cancel;
    @FXML
    public Button Confirm;
    @FXML
    private javafx.scene.control.TextField name,visitor_id,phone_number,reason,date;
    private VisitorCheckController visitorCheckController;
    public void initialize(){
        Confirm.setCursor(Cursor.HAND);
        Cancel.setCursor(Cursor.HAND);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        date.setText(LocalDateTime.now().format(formatter));
        date.setEditable(false);
        date.setCursor(Cursor.DEFAULT);
    }
    public void setVisitorCheckController(VisitorCheckController controller) {
        this.visitorCheckController = controller;
    }
    @FXML
    public void Addcomfirm(ActionEvent actionEvent) {
        if (name.getText().isEmpty() || visitor_id.getText().isEmpty() || phone_number.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("输入存在空");
            alert.setHeaderText("输入不合法");
            alert.setContentText("请确保填写必要信息");
            alert.showAndWait();
            return;
        }
        if(reason.getText().isEmpty()){
            reason.setText("其他");
        }
        if(StringUtil.containsDigit(name.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("输入不合法");
            alert.setHeaderText("名字不能数字");
            alert.setContentText("请确保名字不包含数字");
            alert.showAndWait();
            return;
        }
        String insertSQL = "INSERT INTO visitor (name, visitor_id, phone_number, date, time, reason) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, name.getText());
            pstmt.setString(2, visitor_id.getText());
            pstmt.setString(3, phone_number.getText());
            pstmt.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // Insert date part
            pstmt.setTime(5, java.sql.Time.valueOf(LocalTime.now())); // Insert time part with seconds set to 00
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
