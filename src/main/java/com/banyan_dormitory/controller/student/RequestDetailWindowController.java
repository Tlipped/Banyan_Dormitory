package com.banyan_dormitory.controller.student;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RequestDetailWindowController {
    @FXML
    private TextArea contentDetailText;

    @FXML
    private TextArea replyDetailText;

    private static String contentDetail;

    private static String replyDetail;

    public void initialize() {
        contentDetailText.setEditable(false);
        replyDetailText.setEditable(false);
        contentDetailText.setText(contentDetail);
        replyDetailText.setText(replyDetail);
    }

    public static void setContentDetail(String ctdt) {
        contentDetail = ctdt;
    }

    public static void setReplyDetail(String rpdt) {
        replyDetail = rpdt;
    }
}
