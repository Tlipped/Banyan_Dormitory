package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerRespondMessage {
    public TextArea textArea;
    public Button rejectButton;
    public Button passButton;

    private int id;
    private String student_id;
    private String type;
    private Button returnButton;

    public ManagerRespondMessage()
    {

    }
    public ManagerRespondMessage(int id,String student_id,String type,Button returnButton)
    {
        this.id=id;
        this.student_id=student_id;
        this.type=type;
        this.returnButton=returnButton;
    }
    public void initialize()
    {
        int number=this.id+10000;
        String searchSql = "select * from message where id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSql))
        {
            pstmt.setInt(1,number);
            ResultSet Set= pstmt.executeQuery();
            if(Set.next())
            {
                textArea.setText(Set.getString("content"));
            }
        } catch (SQLException a) {
            a.printStackTrace();
        }

        passButton.setOnAction(event -> {
            if (textArea.getText().trim().isEmpty()) {
                showAlert("请输入回复内容");
            } else {
                try {
                    String sql ="UPDATE message SET status=1 WHERE id=?";
                    Connection connection= DatabaseUtil.getConnection();
                    PreparedStatement pstm= connection.prepareStatement(sql);
                    pstm.setInt(1,id);
                    pstm.execute();
                    handlePass(textArea.getText(), student_id,type);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                showSuccess("回复成功");
                Stage stage = (Stage) passButton.getScene().getWindow();
                stage.close();
//                ViewManager.changeView("/com/banyan_dormitory/fxml/Manager/manager_handleMessages.fxml");
            }
        });

        rejectButton.setOnAction(event -> {
            if (textArea.getText().trim().isEmpty()) {
                showAlert("请输入回复内容");
            } else {
                try {
                    String sql="UPDATE message SET status=1 WHERE id=?";
                    Connection connection= DatabaseUtil.getConnection();
                    PreparedStatement pstm= connection.prepareStatement(sql);
                    pstm.setInt(1,id);
                    pstm.execute();
                    handleReject(textArea.getText(), student_id,type);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                showSuccess("回复成功");
                Stage stage = (Stage) passButton.getScene().getWindow();
                stage.close();
//                ViewManager.changeView("/com/banyan_dormitory/fxml/Manager/manager_handleMessages.fxml");
            }
        });
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        Stage stage=(Stage) returnButton.getScene().getWindow();
        stage.close();
    }
    private void handlePass(String inputText,String id,String type) throws SQLException {
        int number=this.id+10000;
        String deleteSql="delete from message where id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSql))
        {
            pstmt.setInt(1,number);
            pstmt.execute();
        } catch (SQLException a) {
            a.printStackTrace();
        }

        String sql = "INSERT INTO message VALUES (?,'123456',?,?,2,?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,number);
            pstmt.setString(2,id);
            pstmt.setString(3,inputText);
            pstmt.setString(4,type);
            pstmt.execute();
        } catch (SQLException a) {
            a.printStackTrace();
        }
    }

    private void handleReject(String inputText,String id,String type) throws SQLException {
        int number=this.id+10000;
        String deleteSql="delete from message where id=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSql))
        {
            pstmt.setInt(1,number);
            pstmt.execute();
        } catch (SQLException a) {
            a.printStackTrace();
        }

        String sql = "INSERT INTO message VALUES (?,'123456',?,?,3,?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,number);
            pstmt.setString(2,id);
            pstmt.setString(3,inputText);
            pstmt.setString(4,type);
            pstmt.execute();
        } catch (SQLException a) {
            a.printStackTrace();
        }
    }
}
