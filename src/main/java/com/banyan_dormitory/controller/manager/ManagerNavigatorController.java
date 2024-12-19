package com.banyan_dormitory.controller.manager;

import com.banyan_dormitory.util.DatabaseUtil;
import com.mysql.cj.xdevapi.SqlStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.sql.*;
import java.util.Date;

import static java.lang.Thread.sleep;

public class ManagerNavigatorController {
    @FXML
    private StackPane public_container,checkin_container,student_management_container,examine_container;
    @FXML
    private Button public_announce,checkin,student_management,examine;
    private StackPane currentSelectedContainer;
    public void initialize() throws SQLException {
        setupButton(public_announce, public_container);
        setupButton(checkin, checkin_container);
        setupButton(student_management, student_management_container);
        setupButton(examine, examine_container);

        // 默认选中第一个按钮
        selectButton(public_container);
    }
    public ManagerNavigatorController() {
        // 默认构造函数

    }

    private void setupButton(Button button, StackPane container) {
        button.setOnMouseEntered(event -> {
            if (currentSelectedContainer != container) {
                container.setStyle("-fx-border-color:  rgba(234,251,226,0.93);");
            }
        });

        button.setOnMouseExited(event -> {
            if (currentSelectedContainer != container) {
                container.setStyle("-fx-background-color: transparent;");
            }
        });

        button.setOnAction(event -> {
            selectButton(container);
        });
    }

    private void selectButton(StackPane newSelectedContainer) {
        if (currentSelectedContainer != null) {
            currentSelectedContainer.setStyle("-fx-background-color: transparent;");
        }
        currentSelectedContainer = newSelectedContainer;
        currentSelectedContainer.setStyle("-fx-border-color: rgba(234,251,226,0.93);");
    }
}
