package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.model.Message;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;
import com.banyan_dormitory.util.DatabaseUtil;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RequestFeedbackPanelController {
    @FXML
    private VBox MessageContainer;

    static Timeline timeline;

    @FXML
    private ScrollPane myscrollPane;

    public void initialize() {
        showAllRequests();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            showAllRequests();
//            System.out.println("refreshed");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void stopRequestFeedbackPanelTimeline(){
        if(timeline!=null){
            timeline.stop();
        }
    }

    private void showAllRequests(){
        List<Message> messages = DatabaseUtil.readMessageFromDatabase(UserPanelController.user.getId());
        MessageContainer.getChildren().clear();
        for (Message message : messages) {
            GridPane gridPane = new GridPane();
            MessageContainer.getChildren().add(gridPane);

            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setStyle("-fx-background-color: rgba(10,159,65,0.4);-fx-background-radius: 5");

            for (int i = 0; i < 7; i++) {
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(100.0 / 7);
                gridPane.getColumnConstraints().add(column);
            }

            Label id_label = new Label(String.valueOf(message.getId()));
            id_label.setStyle("-fx-font-size: 20px;");
            id_label.setMaxWidth(Double.MAX_VALUE);
            id_label.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(id_label, 0, 0);

            Label label1 = new Label("榕园九号");
            label1.setStyle("-fx-font-size: 20px;");
            label1.setMaxWidth(Double.MAX_VALUE);
            label1.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label1, 1, 0);

            Label label2 = new Label(UserPanelController.user.getRoom());
            label2.setStyle("-fx-font-size: 20px;");
            label2.setMaxWidth(Double.MAX_VALUE);
            label2.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label2, 2, 0);

            Label label3 = new Label(UserPanelController.user.getName());
            label3.setStyle("-fx-font-size: 20px;");
            label3.setMaxWidth(Double.MAX_VALUE);
            label3.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label3, 3, 0);

            Label label4 = new Label(UserPanelController.user.getId());
            label4.setStyle("-fx-font-size: 20px;");
            label4.setMaxWidth(Double.MAX_VALUE);
            label4.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label4, 4, 0);

            Label label5 = new Label(message.getType());
            label5.setStyle("-fx-font-size: 20px;");
            label5.setMaxWidth(Double.MAX_VALUE);
            label5.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label5, 5, 0);

            int messageStatus = message.getStatus();
            StackPane statusContainer = new StackPane();
            statusContainer.setPadding(new Insets(10, 10, 10, 20));
            statusContainer.setStyle("-fx-background-color: rgba(173,240,140,1);-fx-background-radius: 5");
            statusContainer.setAlignment(Pos.CENTER_LEFT);

            String statusText="";
            if(message.getFrom().equals(UserPanelController.user.getId())){
                switch (messageStatus) {
                    case 0:
                        statusText = "未处理";
                        break;
                    case 1:
                        statusText = "已处理";
                        break;
                    case 2:
                        statusText = "已通过";
                        break;
                    case 3:
                        statusText = "被驳回";
                        statusContainer.setStyle("-fx-background-color: #F394A1;-fx-background-radius: 5");
                        break;
                    default:
                        statusText = "发送失败";
                        break;
                }
            } else if (message.getTo().equals(UserPanelController.user.getId())) {
                statusText="宿管回复";
            }

            Label statusLabel = new Label(statusText);
            statusLabel.setStyle("-fx-font-size: 20px;");
            statusContainer.getChildren().add(statusLabel);
            statusContainer.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(statusContainer, 6, 0);

            gridPane.setOnMouseClicked(event -> {
                System.out.println("clicked");
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("消息详情");
//                alert.setHeaderText("消息内容");
//                alert.setContentText(message.getContent());
//                alert.showAndWait();
//                if(message.getFrom().equals("123456"/*adminId*/)){
//                    DatabaseUtil.updateMessageStatus(message.getId(), 2);
//                }
                Stage RequestDetailStage = new Stage();
                RequestDetailStage.setTitle("信息详情");
                try {
                    RequestDetailWindowController.setContentDetail(message.getContent());
                    RequestDetailWindowController.setReplyDetail(message.getReply());

                    Parent root= FXMLLoader.load(RequestFeedbackPanelController.class.getResource("/com/banyan_dormitory/fxml/Student/RequestDetailWindow.fxml"));
                    if(message.getFrom().equals("123456"/*adminId*/)){
                        DatabaseUtil.updateMessageStatus(message.getId(), 2);
                    }
                    Scene scene = new Scene(root);
                    RequestDetailStage.setScene(scene);
                    RequestDetailStage.centerOnScreen();
                    RequestDetailStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("无法加载 FXML 文件: " + "/com/banyan_dormitory/fxml/RequestDetailWindow.fxml");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to load view.");
                    alert.setContentText("The system could not load the requested view. Please try again or contact support.");
                    alert.showAndWait();
                }
            });

            gridPane.setCursor(Cursor.HAND);
        }
        Platform.runLater(this::setVerticalScrollBarStyle);
    }

    private void setVerticalScrollBarStyle() {
        // 垂直滚动条并设置其样式
        Node verticalScrollBar = myscrollPane.lookup(".scroll-bar:vertical");
        if (verticalScrollBar != null) {
            verticalScrollBar.setStyle(
                    "-fx-background-color: rgba(142,216,99,0.8); " + // 设置外部颜色
                            "-fx-background-insets: 0; " +
                            "-fx-padding: 0;"
            );

            Node thumb = verticalScrollBar.lookup(".thumb");
            if (thumb != null) {
                thumb.setStyle("-fx-background-color: rgb(75,147,53);");
            }
        }
    }
}
