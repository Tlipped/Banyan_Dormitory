module com.banyan_dormitory {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.banyan_dormitory to javafx.fxml;
    opens com.banyan_dormitory.controller to javafx.fxml;
    exports com.banyan_dormitory;
}