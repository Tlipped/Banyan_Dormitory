package com.banyan_dormitory.controller.manager;
import com.banyan_dormitory.model.Visitor;
import com.banyan_dormitory.util.DatabaseUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
public class VisitorCheckController {
    @FXML
    public Button searchButton;
    @FXML
    public Button CheckInButton;
    @FXML
    private javafx.scene.control.Button checkIn;
    @FXML
    private javafx.scene.control.ScrollPane myScrollPane;
    @FXML
    private DatePicker date;
    @FXML
    private VBox myVox;
    @FXML
    private void initialize() {
        if (myVox == null) {
            throw new IllegalStateException("myVox is not injected by FXMLLoader");
        }
        date.setValue(LocalDate.now());
        onSearchButtonClick(new ActionEvent());
        Platform.runLater(this::setVerticalScrollBarStyle);
        searchButton.setCursor(Cursor.HAND);
        CheckInButton.setCursor(Cursor.HAND);
        date.setCursor(Cursor.HAND);
    }

    private void setVerticalScrollBarStyle() {
        // 垂直滚动条并设置其样式
        Node verticalScrollBar = myScrollPane.lookup(".scroll-bar:vertical");
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

    @FXML
    void onSearchButtonClick(ActionEvent event) {
        if (myVox == null) {
            System.err.println("myVox is null when trying to clear its children.");
            return;
        }
        myVox.getChildren().clear();

        Optional<Date> selectedDate = Optional.ofNullable(date.getValue()).map(java.sql.Date::valueOf);
        if (selectedDate.isEmpty()) {
            System.out.println("请选择一个有效的日期");
            return;
        }

        ObservableList<Visitor> visitors = DatabaseUtil.fetchDataFromDateBase(selectedDate.get());
        for (Visitor visitor : visitors) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Manager/visitor_layout.fxml"));
                Node recordNode = loader.load();
                Visitor_layoutController recordController = loader.getController();
                recordController.setData(visitor);
                myVox.getChildren().add(recordNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onCheckInButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/banyan_dormitory/fxml/Manager/Visitor_dialog.fxml"));
            Parent root = loader.load();

            Visitor_DialogController dialogController = loader.getController();

            dialogController.setVisitorCheckController(this);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            dialogStage.setTitle("访客登记");

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
