package com.example.demo.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BattlePlane extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BattlePlane.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
        Parent root = fxmlLoader.load();

        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sky Battle");

        primaryStage.show();
    }

    // 获取主舞台
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}