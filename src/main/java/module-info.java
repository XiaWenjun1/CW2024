module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;

    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo to javafx.fxml;

    exports com.example.demo.controller;
}