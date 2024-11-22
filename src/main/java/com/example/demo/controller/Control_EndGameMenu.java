package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.demo.Level.LevelParent;
import javafx.application.Platform;

import java.io.IOException;

public class Control_EndGameMenu {

    @FXML
    private Button returnToMainButton;

    private LevelParent levelParent;

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    @FXML
    private void handleReturnToMainButton(ActionEvent event) {
        levelParent.cleanUp();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot, 1300, 750);

            Stage stage = (Stage) returnToMainButton.getScene().getWindow();

            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }
}