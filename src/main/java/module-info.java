module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.Level to javafx.fxml;
    opens com.example.demo.Actor to javafx.fxml;
    opens com.example.demo.Object to javafx.fxml;
    opens com.example.demo.Display to javafx.fxml;

    exports com.example.demo.controller;
    exports com.example.demo.Level;
    exports com.example.demo.Level.LevelManager;

    opens com.example.demo.Level.LevelManager to javafx.fxml;
    opens com.example.demo.Object.Object to javafx.fxml;
    opens com.example.demo.Object.Boss to javafx.fxml;
    opens com.example.demo.Object.EnemyPlane to javafx.fxml;
    opens com.example.demo.Object.UserPlane to javafx.fxml;
}