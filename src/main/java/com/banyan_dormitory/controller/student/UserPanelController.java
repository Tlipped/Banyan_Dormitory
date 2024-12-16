package com.banyan_dormitory.controller.student;
import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;

public class UserPanelController {

    @FXML
    private TextField bedInput;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button changeUserInfoButton;

    @FXML
    private StackPane dormInfoContainer;

    @FXML
    private TextField dormInput;

    @FXML
    private StackPane idContainer;

    @FXML
    private TextField idInput;

    @FXML
    private StackPane nameContainer;

    @FXML
    private TextField nameInput;

    @FXML
    private StackPane schoolContainer;

    @FXML
    private TextField schoolInput;

    @FXML
    private StackPane scoreContainer;

    @FXML
    private TextField scoreInput;

    @FXML
    private Label userDormInfoText;

    @FXML
    private Label userIdText;

    @FXML
    private Label userNameText;

    @FXML
    private AnchorPane userPanelMainContainer;

    @FXML
    private Label userSchoolText;

    @FXML
    private Label userScoreText;

    boolean isEditable = false;

    public static User user;

    public void initialize() {
        loadUserPanel(user.getId());
    }

    public static void receiveUserID(String userid){
        user=new User();
        user.setId(userid);
    }

//    public void initLineBackground(StackPane container){
//        container.setStyle("-fx-background-color: rgba(10,159,65,1);-fx-background-radius: 5;");
//    }

    public void loadUserPanel(String userid) {
        user= DatabaseUtil.getUser(String.valueOf(userid));
//        initLineBackground(nameContainer);
//        initLineBackground(idContainer);
//        initLineBackground(dormInfoContainer);
//        initLineBackground(schoolContainer);
//        initLineBackground(scoreContainer);
        nameInput.setText(user.getName());
        nameInput.setEditable(false);
        idInput.setText(user.getId());
        idInput.setEditable(false);
        dormInput.setText(user.getRoom());
        dormInput.setEditable(false);
        bedInput.setText(user.getBed());
        bedInput.setEditable(false);
        schoolInput.setText(user.getSchool());
        schoolInput.setEditable(false);
        scoreInput.setText(user.getScore());
        scoreInput.setEditable(false);

        //changePasswordButton.setStyle("-fx-background-color: rgba(173,240,140,1);");
        changePasswordButton.setOnAction(event -> {
            Stage changePasswordStage = new Stage();
            changePasswordStage.setTitle("修改密码");
            try {
                Parent root= FXMLLoader.load(UserPanelController.class.getResource("/com/banyan_dormitory/fxml/Student/ChangePassword.fxml"));
                Scene scene = new Scene(root);
                changePasswordStage.setScene(scene);
                changePasswordStage.centerOnScreen();
                changePasswordStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("无法加载 FXML 文件: " + "/com/banyan_dormitory/fxml/ChangePassword.fxml");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load view.");
                alert.setContentText("The system could not load the requested view. Please try again or contact support.");
                alert.showAndWait();
            }
        });

        changeUserInfoButton.setOnAction(event -> {
            if(!isEditable) {
                changeUserInfoButton.setText("保存用户信息");
                isEditable = true;
                nameInput.setEditable(true);
                schoolInput.setEditable(true);
            }else{
                changeUserInfoButton.setText("修改用户信息");
                isEditable = false;
                nameInput.setEditable(false);
                schoolInput.setEditable(false);
                user.setName(nameInput.getText());
                user.setSchool(schoolInput.getText());
                DatabaseUtil.updateUser(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "修改成功");
                alert.showAndWait();
            }
        });

        //userPanelMainContainer.setStyle("-fx-background-color: rgba(10,159,65,0.2);-fx-background-radius: 10;");
    }
}

