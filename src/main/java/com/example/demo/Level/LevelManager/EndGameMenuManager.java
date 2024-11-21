package com.example.demo.Level.LevelManager;

import com.example.demo.Level.LevelParent;
import com.example.demo.controller.Control_EndGameMenu;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class EndGameMenuManager {

    private LevelParent levelParent;

    public EndGameMenuManager(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    public void winGame() {
        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            levelParent.cleanUp();
            levelParent.getLevelView().showWinImage();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> showEndGameMenu(currentStage, true));
            delay.play();
        });
    }

    public void loseGame() {
        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            levelParent.cleanUp();
            levelParent.getLevelView().showGameOverImage();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> showEndGameMenu(currentStage, false));
            delay.play();
        });
    }

    private Stage getCurrentStage() {
        Scene currentScene = levelParent.getRoot().getScene();
        if (currentScene != null) {
            return (Stage) currentScene.getWindow();
        }
        return null;
    }

    private void showEndGameMenu(Stage stage, boolean isWin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/EndGameMenu/EndGameMenu.fxml"));
            Parent endGameRoot = loader.load();

            Control_EndGameMenu controller = loader.getController();
            controller.initialize(levelParent);

            Scene endGameScene = new Scene(endGameRoot);
            stage.setScene(endGameScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
