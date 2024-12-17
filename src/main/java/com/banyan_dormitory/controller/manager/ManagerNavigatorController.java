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

import com.banyan_dormitory.Main;

public class ManagerNavigatorController {
    @FXML
    private StackPane public_container,checkin_container,student_management_container,examine_container;
    @FXML
    private Button public_announce,checkin,student_management,examine;
    private StackPane currentSelectedContainer;
    @FXML
    private TextArea accouncement_edit;
    @FXML
    private Label error;
    @FXML
    private DatePicker date_edit;
    @FXML
    private Label success;
    @FXML
    private ListView<String> accouncement_show;
    public void initialize() throws SQLException {
        setupButton(public_announce, public_container);
        setupButton(checkin, checkin_container);
        setupButton(student_management, student_management_container);
        setupButton(examine, examine_container);

        // 默认选中第一个按钮
        selectButton(public_container);

        String sql="SELECT * FROM information order by id ";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            String str=Set.getString("content");
            int size=50-3*str.length();
            StringBuffer Sb=new StringBuffer(" ");
            System.out.println(size);
            while (size>=0)
            {
                Sb.append(' ');
                size--;
            }
            accouncement_show.getItems().add(Set.getString("content")+Sb+Set.getString("date"));
        }
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

    public void release_announcement_action(ActionEvent actionEvent) throws SQLException, InterruptedException {
        String accouncement=accouncement_edit.getText();
        String date=date_edit.getEditor().getText();
        if(accouncement.isEmpty())
        {
            error.setText("公告不能为空");
            error.setVisible(true);
            success.setVisible(false);
            return;
        }
        else if (date.isEmpty())
        {
            error.setText("日期不能为空");
            error.setVisible(true);
            success.setVisible(false);
            return;
        }
        else error.setVisible(false);
        int id=0;
        String sql="SELECT * FROM information order by id";
        Connection connection= DatabaseUtil.getConnection();
        Statement sq= connection.createStatement();
        ResultSet Set=sq.executeQuery(sql);
        while (Set.next())
        {
            id=Set.getInt("id")+1;
        }

        sql="INSERT INTO information VALUES (?,?,?)";
        try (PreparedStatement presq=connection.prepareStatement(sql);){
            presq.setInt(1,id);
            presq.setString(2,accouncement);
            presq.setString(3,date);
            presq.execute();
        }

        accouncement_edit.clear();
        date_edit.getEditor().clear();
        success.setVisible(true);
        initialize();
        return;
    }
}
