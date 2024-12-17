package com.banyan_dormitory.controller.manager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.*;

public class Student_ManagerController {

    @FXML private TextField room;
    @FXML private Button search;

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
    }

    private void loadRoomData() {
        String roomNumber = room.getText();
        String query = "SELECT * FROM user WHERE room = ?";

        // 清空显示
        resetFields();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dormitory_db", "username", "password");
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String bed = rs.getString("bed");
                String studentName = rs.getString("name");
                String studentId = rs.getString("id");
                String studentScore = rs.getString("score");

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

    private void updateField(Label name, Label id, Label score, Button changeScore, Button delete, String studentName, String studentId, String studentScore) {
        name.setText(studentName);
        id.setText(studentId);
        score.setText(studentScore);
        changeScore.setDisable(false);
        delete.setDisable(false);
    }
}