package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
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
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class ManagerHandleMessages {

    @FXML
    private ListView<HBox> totalMessages;

    private Timeline timeline;
    public ManagerHandleMessages()
    {

    }
    public void initialize() throws SQLException
    {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Platform.runLater(() -> {
                try {
                    update();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void update() throws SQLException
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
                    vbox.setSpacing(100);
                    setGraphic(vbox);
                } else {
                    setGraphic(null);
                }
            }
        });

        String sql="SELECT * FROM message WHERE `to`='123456' order by status,id";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            int number=Set.getInt("id");
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


            Button button = new Button("查看详情");
            button.setCursor(Cursor.HAND);
            if(Set.getInt("status")!=0)
            {
                button.setText("已处理");
                button.setDisable(true);
            }
            button.setStyle("-fx-font-size: 20;-fx-background-color: #ADD8E6");

            button.setOnAction(e-> {
                try {
                    timeline.stop();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Manager/managerRespond.fxml"));
                    loader.setControllerFactory(param -> {
                        return new ManagerRespond(number);
                    });
                    Parent root = loader.load();
                    Stage currentStage = new Stage();
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            hbox.getChildren().addAll(dormLabel,roomLabel,idLabel,nameLabel,typeLabel);
            Region spacer=new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            hbox.getChildren().add(spacer);
            hbox.getChildren().add(button);

            hbox.setStyle("-fx-background-radius: 30");
            totalMessages.getItems().add(hbox);
        }

        Set.close();
        sq.close();
        connection.close();
    }
}
