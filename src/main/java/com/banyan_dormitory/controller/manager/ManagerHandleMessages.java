package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.sql.*;
import java.time.LocalDateTime;

public class ManagerHandleMessages {

    @FXML
    private ListView<HBox> totalMessages;

    public ManagerHandleMessages()
    {

    }
    public void initialize() throws SQLException
    {
        totalMessages.getItems().clear();
        totalMessages.setFixedCellSize(60);
        totalMessages.setCellFactory(param -> new ListCell<HBox>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (item!= null) {
                    setGraphic(item);
                    setStyle("-fx-padding: 20px;-fx-background-color: green");
                    VBox vbox = new VBox(item);
                    vbox.setSpacing(100); // 设置垂直间距
                    setGraphic(vbox);
                } else {
                    setGraphic(null);
                }
            }
        });

        String sql="SELECT * FROM message WHERE `to`='123456'AND status=0";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            String id=Set.getString("from");

            String newSql="SELECT * FROM user WHERE id=?";
            PreparedStatement pstmt=connection.prepareStatement(newSql);
            pstmt.setString(1,id);
            ResultSet se=pstmt.executeQuery();
            se.next();

            String name=se.getString("name");
            String room=se.getString("room");
            String type=Set.getString("type");
            String content=Set.getString("content");

            Label nameLabel=new Label(name);
            nameLabel.setStyle("-fx-background-radius: 30;-fx-font-size: 20");

            Label roomLabel=new Label(room);
            roomLabel.setStyle("-fx-background-radius: 30;-fx-font-size: 20");

            Label idLabel=new Label(id);
            idLabel.setStyle("-fx-background-radius: 30;-fx-font-size: 20");

            Label dormLabel=new Label("榕园九号");
            dormLabel.setStyle("-fx-background-radius: 30;-fx-font-size: 20");

            Label typeLabel=new Label(type);
            typeLabel.setStyle("-fx-background-radius: 30;-fx-font-size: 20");

            HBox hbox = new HBox();
            hbox.setSpacing(28);
            hbox.setPadding(new Insets(5));

            // 创建按钮
            Button button = new Button("查看详情");
            button.setStyle("-fx-font-size: 20;-fx-background-color: #ADD8E6");
            button.setOnAction(e-> {
                try {
                    showPopup(content,id,type);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            hbox.getChildren().addAll(dormLabel,roomLabel,idLabel,nameLabel,typeLabel,new Region(),button);
            HBox.setHgrow(new Region(), javafx.scene.layout.Priority.ALWAYS);
            hbox.setStyle("-fx-background-radius: 30");
            totalMessages.getItems().add(hbox);
        }
    }

    private void showPopup(String inputString,String id,String type) throws SQLException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("具体内容");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Label showLabel = new Label(inputString);
        showLabel.setWrapText(true);
        showLabel.setPrefWidth(280);
        showLabel.setPrefHeight(100);
        showLabel.setTextFill(Color.BLACK);
        showLabel.setFont(Font.font("System", 14));
        showLabel.setStyle("-fx-background-color: white");

        TextField textField = new TextField();
        textField.setPrefColumnCount(30);
        textField.setPrefHeight(100);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button passButton = new Button("通过");
        passButton.setFont(Font.font("Arial", 16));
        passButton.setStyle("-fx-background-color: green");

        Button rejectButton = new Button("驳回");
        rejectButton.setFont(Font.font("Arial", 16));
        rejectButton.setStyle("-fx-background-color: red");

        // "通过"按钮点击事件处理逻辑
        passButton.setOnAction(e -> {
            String inputText = textField.getText();
            if (inputText.isEmpty()) {
                // 如果输入文本为空，弹出提示框提醒
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("请输入回复内容");
                alert.showAndWait();
            } else {
                try {
                    String sql="UPDATE message SET status=1 WHERE `from`=? AND content=?";
                    Connection connection= DatabaseUtil.getConnection();
                    PreparedStatement pstm= connection.prepareStatement(sql);
                    pstm.setString(1,id);
                    pstm.setString(2,inputString);
                    pstm.execute();
                    handlePass(inputText,id,type);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                popupStage.close();
            }
        });

        // "驳回"按钮点击事件处理逻辑
        rejectButton.setOnAction(e -> {
            String inputText = textField.getText();
            if (inputText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("提示");
                alert.setHeaderText(null);
                alert.setContentText("请输入回复内容");
                alert.showAndWait();
            } else {
                try {
                    String sql="UPDATE message SET status=1 WHERE `from`=? AND content=?";
                    Connection connection= DatabaseUtil.getConnection();
                    PreparedStatement pstm= connection.prepareStatement(sql);
                    pstm.setString(1,id);
                    pstm.setString(2,inputString);
                    pstm.execute();
                    handleReject(inputText,id,type);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                popupStage.close();
            }
        });

        buttonBox.getChildren().addAll(passButton, rejectButton);

        vbox.getChildren().addAll(showLabel, textField, buttonBox);
        vbox.setStyle("-fx-background-color: #F5F5DC");

        Rectangle rectangle = new Rectangle(350, 300);
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);

        StackPane stackPane = new StackPane(rectangle, vbox);

        Scene popupScene = new Scene(stackPane, 350, 300);

        stackPane.setStyle("-fx-background-color: blue;");

        popupStage.setScene(popupScene);
        popupStage.show();
        initialize();
    }

    private void handlePass(String inputText,String id,String type) throws SQLException {
        int number=0;
        Connection connection = DatabaseUtil.getConnection();
        Statement st=connection.createStatement();
        String sql="SELECT id FROM message order by id desc ";
        ResultSet Set=st.executeQuery(sql);
        if(Set.next()) number=Set.getInt("id")+1;

        sql = "INSERT INTO message VALUES (?,'123456',?,?,2,?)";
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
        initialize();
    }

    private void handleReject(String inputText,String id,String type) throws SQLException {
        int number=0;
        Connection connection = DatabaseUtil.getConnection();
        Statement st=connection.createStatement();
        String sql="SELECT id FROM message order by id desc ";
        ResultSet Set=st.executeQuery(sql);
        if(Set.next()) number=Set.getInt("id")+1;

        sql = "INSERT INTO message VALUES (?,'123456',?,?,3,?)";
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
        initialize();
    }
}
