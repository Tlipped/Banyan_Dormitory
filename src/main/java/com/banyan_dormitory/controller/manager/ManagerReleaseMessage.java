package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ManagerReleaseMessage {

    public DatePicker datePicker;
    public Button confirmButton;
    public TextArea textField;

    public ManagerReleaseMessage()
    {

    }

    public void initialize()
    {
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);
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
                int number=0;
                String sql="SELECT id FROM information order by id desc ";
                Connection connection = null;
                try {
                    connection = DatabaseUtil.getConnection();
                    Statement st=connection.createStatement();
                    ResultSet Set=st.executeQuery(sql);
                    if(Set.next()) number=Set.getInt("id")+1;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                sql = "INSERT INTO information VALUES (?,?,?)";
                try (Connection conn = DatabaseUtil.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql))
                {
                    pstmt.setInt(1,number);
                    pstmt.setString(2,textField.getText());
                    pstmt.setString(3,datePicker.getEditor().getText());
                    pstmt.execute();
                } catch (SQLException a) {
                    a.printStackTrace();
                }
                Stage stage = (Stage) confirmButton.getScene().getWindow();
                stage.close();
            }

        });
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
