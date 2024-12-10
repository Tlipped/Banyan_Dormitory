package com.banyan_dormitory.controller;
import com.banyan_dormitory.Main;

import com.banyan_dormitory.util.StringUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField account;

    @FXML
    private TextField password;
    @FXML
    private Label errorInfo;
    @FXML
    public void initialize(){

    }
    @FXML
    public void doLogin(){
        String accountText=account.getText();
        String passwordText= password.getText();
        if(StringUtil.isEmpty(accountText)){
            errorInfo.setText("请输入账户！！");
            errorInfo.setVisible(true);
            return;
        }
        if(StringUtil.isEmpty(passwordText)){
            errorInfo.setText("请输入密码！！");
            errorInfo.setVisible(true);
            return;
        }
        if(accountText.equals("admin")&&passwordText.equals("admin")){
//            Main.changeView("main.fxml");
        }else{
            errorInfo.setText("账户或密码错误");
            errorInfo.setVisible(true);
        }
    }
}
