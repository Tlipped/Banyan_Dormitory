package com.banyan_dormitory.controller.manager;
import com.banyan_dormitory.model.Visitor;
import com.banyan_dormitory.util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.w3c.dom.events.EventTarget;



public class Visitor_layoutController {
    @FXML
    private javafx.scene.control.Label name;
    @FXML
    private javafx.scene.control.Label visitor_id;
    @FXML
    private javafx.scene.control.Label phone_number;
    @FXML
    private javafx.scene.control.Label reason;

    public void setData(Visitor visitor) {
        name.setText(visitor.getName());
        visitor_id.setText(visitor.getVisitor_id());
        phone_number.setText(visitor.getPhone_number());
        reason.setText(visitor.getReason());
    }
}

