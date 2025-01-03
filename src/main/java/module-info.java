module com.banyan_dormitory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;
    requires jdk.jdi;

    // 确保开放控制器所在的包
    opens com.banyan_dormitory to javafx.fxml;
    opens com.banyan_dormitory.controller to javafx.fxml;
    opens com.banyan_dormitory.controller.student to javafx.fxml;
    opens com.banyan_dormitory.images to javafx.fxml;
    opens com.banyan_dormitory.controller.manager to javafx.fxml;
    opens com.banyan_dormitory.model to javafx.fxml;

    exports com.banyan_dormitory;
}