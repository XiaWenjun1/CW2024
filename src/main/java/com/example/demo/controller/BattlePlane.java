package com.example.demo.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BattlePlane extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setScene(new Scene(root, 1300, 750));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sky Battle");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}