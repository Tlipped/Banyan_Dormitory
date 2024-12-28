package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.util.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ManagerRespond {
    public TextArea specifictext;
    private int id=1;
    private String student_id,type;
    @FXML
    private Button returnButton;
    @FXML
    private Label typeArea;
    public void setId(int id)
    {
        this.id=id;
    }
    public ManagerRespond(int id)
    {
        this.id=id;
    }
    public ManagerRespond()
    {

    }
    public void initialize() throws SQLException {
        String sql="SELECT * FROM message WHERE id=?";
        Connection connection= DatabaseUtil.getConnection();
        PreparedStatement pstmt=connection.prepareStatement(sql);
        pstmt.setInt(1,id);
        ResultSet Set=pstmt.executeQuery();
        Set.next();

        this.type=Set.getString("type");
        specifictext.setText(Set.getString("content"));
        this.student_id=Set.getString("from");
        typeArea.setText("类型："+Set.getString("type"));

    }
    public void returnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) returnButton.getScene().getWindow();
        stage.close();
    }

    public void responseAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Manager/manager_respondMessage.fxml"));
        loader.setControllerFactory(param -> {
            return new ManagerRespondMessage(id,student_id,type,returnButton);
        });
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(600);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
