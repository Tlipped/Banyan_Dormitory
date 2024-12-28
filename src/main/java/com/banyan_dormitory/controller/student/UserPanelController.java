package com.banyan_dormitory.controller.student;
import com.banyan_dormitory.util.DatabaseUtil;
import com.banyan_dormitory.model.User;
import com.banyan_dormitory.util.StringUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;

public class UserPanelController {
    @FXML
    public Label schoolInputWarningText;

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
    private TextField phone_numberInput;

    @FXML
    private Label phone_numberWarningText;

    @FXML
    private StackPane schoolContainer;

    @FXML
    private TextField schoolInput;

    @FXML
    private StackPane scoreContainer;

    @FXML
    private StackPane scoreContainer1;

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

    @FXML
    private Label userScoreText1;

    @FXML
    private Label userScoreText11;

    @FXML
    private StackPane user_;

    @FXML
    private TextField user_idInput;

    @FXML
    private Label user_idWarningText;

    boolean isEditable = false;

    public static User user;

    static Timeline timeline;

    public static void stopUserPanelTimeline() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void initialize() {
        loadUserPanel(user.getId());
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            loadUserPanel(user.getId());
//            System.out.println("refreshed");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        changePasswordButton.setCursor(Cursor.HAND);
        changeUserInfoButton.setCursor(Cursor.HAND);
    }

    public static void receiveUserID(String userid){
        user=new User();
        user.setId(userid);
        user=DatabaseUtil.getUser(userid);
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
        user_idInput.setText(user.getUser_id());
        user_idInput.setEditable(false);
        phone_numberInput.setText(user.getPhone_number());
        phone_numberInput.setEditable(false);
        user_idWarningText.setVisible(false);
        phone_numberWarningText.setVisible(false);
        schoolInputWarningText.setVisible(false);

        //changePasswordButton.setStyle("-fx-background-color: rgba(173,240,140,1);");
        changePasswordButton.setOnAction(event -> {
            Stage changePasswordStage = new Stage();
            changePasswordStage.setTitle("修改密码");

            //冻结其他窗口
            changePasswordStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

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
                timeline.stop();
                changeUserInfoButton.setText("保存信息");
                isEditable = true;
                //nameInput.setEditable(true);
                //nameInput.setStyle("-fx-border-color: rgba(255,255,255,1);-fx-border-radius: 5;");
                schoolInput.setEditable(true);
                schoolInput.setStyle("-fx-border-color: rgba(255,255,255,1);-fx-border-radius: 5;-fx-background-color: transparent;");
                user_idInput.setEditable(true);
                user_idInput.setStyle("-fx-border-color: rgba(255,255,255,1);-fx-border-radius: 5;-fx-background-color: transparent;");
                phone_numberInput.setEditable(true);
                phone_numberInput.setStyle("-fx-border-color: rgba(255,255,255,1);-fx-border-radius: 5;-fx-background-color: transparent;");
            }else{
                String user_id = user_idInput.getText();
                String phone_number = phone_numberInput.getText();
                String school = schoolInput.getText();

                user_idWarningText.setVisible(false);
                phone_numberWarningText.setVisible(false);
                boolean isValid=true;
                if(StringUtil.isEmpty(user_id)){
                    user_idWarningText.setText("请输入身份证号");
                    user_idWarningText.setVisible(true);
                    isValid=false;
                }
                if(StringUtil.isEmpty(phone_number)){
                    phone_numberWarningText.setText("请输入手机号");
                    phone_numberWarningText.setVisible(true);
                    isValid=false;
                }
                if(user_id.length() != 18){
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "身份证号长度不正确");
//                    alert.showAndWait();
                    user_idWarningText.setText("身份证号需要18位");
                    user_idWarningText.setVisible(true);
                    isValid=false;
                }

                if(phone_number.length() != 11){
//                    Alert alert = new Alert(Alert.AlertType.ERROR, "手机号长度不正确");
//                    alert.showAndWait();
                    phone_numberWarningText.setText("手机号码需要11位");
                    phone_numberWarningText.setVisible(true);
                    isValid=false;
                }

                if(!StringUtil.isNumeric(phone_number)){
                    phone_numberWarningText.setText("手机号码只能包含数字");
                    phone_numberWarningText.setVisible(true);
                    isValid=false;
                }

                if(!StringUtil.isNumeric(user_id)){
                    user_idWarningText.setText("身份证号只能包含数字");
                    user_idWarningText.setVisible(true);
                    isValid=false;
                }

                if(school.equals("")){
                    schoolInputWarningText.setText("学院信息不能为空");
                    schoolInputWarningText.setVisible(true);
                    isValid=false;
                }

                if(!isValid){
                    return;
                }

                user_idWarningText.setVisible(false);
                phone_numberWarningText.setVisible(false);
                changeUserInfoButton.setText("修改信息");
                isEditable = false;
                //nameInput.setEditable(false);
                schoolInput.setEditable(false);
                user_idInput.setEditable(false);
                phone_numberInput.setEditable(false);
                //user.setName(nameInput.getText());
                user.setSchool(schoolInput.getText());
                user.setUser_id(user_idInput.getText());
                user.setPhone_number(phone_numberInput.getText());
                DatabaseUtil.updateUser(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "修改成功");
                alert.showAndWait();
                schoolInput.setStyle("-fx-border-color: none;-fx-border-radius: 5;-fx-background-color: transparent;");
                user_idInput.setStyle("-fx-border-color: none;-fx-border-radius: 5;-fx-background-color: transparent;");
                phone_numberInput.setStyle("-fx-border-color: none;-fx-border-radius: 5;-fx-background-color: transparent;");

                timeline.play();
            }
        });

        changePasswordButton.setCursor(Cursor.HAND);
        changeUserInfoButton.setCursor(Cursor.HAND);

        //userPanelMainContainer.setStyle("-fx-background-color: rgba(10,159,65,0.2);-fx-background-radius: 10;");
    }
}

