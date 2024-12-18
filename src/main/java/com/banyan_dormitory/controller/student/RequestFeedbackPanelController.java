package com.banyan_dormitory.controller.student;

import com.banyan_dormitory.model.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.List;
import com.banyan_dormitory.util.DatabaseUtil;

public class RequestFeedbackPanelController {
    @FXML
    private VBox MessageContainer;

    @FXML
    private ScrollPane myscrollPane;
    public void initialize() {
        List<Message> messages = DatabaseUtil.readMessageFromDatabase(UserPanelController.user.getId());
        for (Message message : messages) {
            GridPane gridPane = new GridPane();
            MessageContainer.getChildren().add(gridPane);

            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setStyle("-fx-background-color: rgba(10,159,65,1);-fx-background-radius: 5");

            for (int i = 0; i < 6; i++) {
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(100.0 / 6);
                gridPane.getColumnConstraints().add(column);
            }

            Label label1 = new Label("榕园九号");
            label1.setStyle("-fx-font-size: 24px;");
            label1.setMaxWidth(Double.MAX_VALUE);
            label1.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label1, 0, 0);

            Label label2 = new Label(UserPanelController.user.getRoom());
            label2.setStyle("-fx-font-size: 24px;");
            label2.setMaxWidth(Double.MAX_VALUE);
            label2.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label2, 1, 0);

            Label label3 = new Label(UserPanelController.user.getName());
            label3.setStyle("-fx-font-size: 24px;");
            label3.setMaxWidth(Double.MAX_VALUE);
            label3.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label3, 2, 0);

            Label label4 = new Label(UserPanelController.user.getId());
            label4.setStyle("-fx-font-size: 24px;");
            label4.setMaxWidth(Double.MAX_VALUE);
            label4.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label4, 3, 0);

            Label label5 = new Label(message.getType());
            label5.setStyle("-fx-font-size: 24px;");
            label5.setMaxWidth(Double.MAX_VALUE);
            label5.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(label5, 4, 0);

            int messageStatus = message.getStatus();
            StackPane statusContainer = new StackPane();
            statusContainer.setPadding(new Insets(10, 10, 10, 10));
            statusContainer.setStyle("-fx-background-color: rgba(173,240,140,1);-fx-background-radius: 5");
            statusContainer.setAlignment(Pos.CENTER_LEFT);

            String statusText="";
            if(message.getFrom().equals(UserPanelController.user.getId())){
                switch (messageStatus) {
                    case 1:
                        statusText = "未处理";
                        break;
                    case 2:
                        statusText = "已通过";
                        break;
                    case 3:
                        statusText = "被驳回";
                        break;
                    default:
                        statusText = "发送失败";
                        break;
                }
            } else if (message.getTo().equals(UserPanelController.user.getId())) {
                statusText="宿管回复";
            }

            Label statusLabel = new Label(statusText);
            statusLabel.setStyle("-fx-font-size: 24px;");
            statusContainer.getChildren().add(statusLabel);
            statusContainer.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(statusContainer, 5, 0);

            gridPane.setOnMouseClicked(event -> {
                System.out.println("clicked");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("消息详情");
                alert.setHeaderText("消息内容");
                alert.setContentText(message.getContent());
                alert.showAndWait();
                if(message.getFrom().equals("123456"/*adminId*/)){
                    DatabaseUtil.updateMessageStatus(message.getId(), 2);
                }
            });
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
