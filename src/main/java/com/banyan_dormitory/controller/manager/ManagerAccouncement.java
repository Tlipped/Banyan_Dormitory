package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

import static java.lang.Thread.sleep;

public class ManagerAccouncement {
    @FXML
    private ListView<HBox> accouncement_show;
    @FXML
    private Button releaseButton;
    private Timeline timeline;
    public void initialize() throws SQLException {
        accouncement_show.setCellFactory(param -> new ListCell<HBox>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null); // 清除任何先前的内容
                } else {
                    setGraphic(item);
                    item.setStyle("-fx-background-color: rgba(0,176,80,0.5);");
                }
            }
        });
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
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
        releaseButton.setCursor(Cursor.HAND);
        accouncement_show.getItems().clear();
        accouncement_show.setFixedCellSize(50);

        String sql="SELECT * FROM information order by id ";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            String id=Set.getString("id");
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

            Button button=new Button("删除公告");
            button.setCursor(Cursor.HAND);
            button.setStyle("-fx-font-size: 20px;-fx-text-fill: black;-fx-background-color: #F394A1");
            button.setOnAction(e->{
                String drop="delete from information where id=?";
                try {
                    Connection connection1= DatabaseUtil.getConnection();
                    PreparedStatement pstm= connection1.prepareStatement(drop);
                    pstm.setString(1,id);
                    pstm.execute();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            HBox hbox=new HBox();
            hbox.getChildren().add(label);

            Region spacer=new Region();
            HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
            hbox.getChildren().add(spacer);
            hbox.getChildren().add(button);
            accouncement_show.getItems().add(hbox);
        }

        Set.close();
        sq.close();
        connection.close();
    }
    public ManagerAccouncement() {
        // 默认构造函数
    }

    public void handleReleaseButtonAction(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Manager/manager_releaseMessage.fxml"));
        loader.setControllerFactory(param -> {
            return new ManagerReleaseMessage();
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
