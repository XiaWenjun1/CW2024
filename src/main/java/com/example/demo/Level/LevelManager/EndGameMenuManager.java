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

/**
 * Manages the end game menu and transitions when the player wins or loses the game.
 * It handles showing win/lose images and transitions to the end game menu.
 */
public class EndGameMenuManager {

    private LevelParent levelParent;

    /**
     * Constructor that initializes the EndGameMenuManager with the given LevelParent.
     *
     * @param levelParent the parent level that contains the game and view
     */
    public EndGameMenuManager(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Handles the actions that occur when the player wins the game.
     * This includes showing a win image and transitioning to the end game menu after a short delay.
     */
    public void winGame() {
        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            levelParent.cleanUp();
            levelParent.getLevelView().showWinImage();

            // Pause for 1 second before showing the end game menu
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> showEndGameMenu(currentStage, true));
            delay.play();
        });
    }

    /**
     * Handles the actions that occur when the player loses the game.
     * This includes showing a game over image and transitioning to the end game menu after a short delay.
     */
    public void loseGame() {
        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            levelParent.cleanUp();
            levelParent.getLevelView().showGameOverImage();

            // Pause for 1 second before showing the end game menu
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> showEndGameMenu(currentStage, false));
            delay.play();
        });
    }

    /**
     * Retrieves the current stage from the LevelParent.
     *
     * @return the current stage of the application, or null if not found
     */
    private Stage getCurrentStage() {
        Scene currentScene = levelParent.getRoot().getScene();
        if (currentScene != null) {
            return (Stage) currentScene.getWindow();
        }
        return null;
    }

    /**
     * Loads and shows the end game menu scene based on the result (win or lose).
     *
     * @param stage the current stage where the end game menu should be displayed
     * @param isWin boolean indicating whether the player won or lost
     */
    private void showEndGameMenu(Stage stage, boolean isWin) {
        try {
            // Load the FXML layout for the end game menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/EndGameMenu/EndGameMenu.fxml"));
            Parent endGameRoot = loader.load();

            // Initialize the controller for the end game menu
            Control_EndGameMenu controller = loader.getController();
            controller.initialize(levelParent);

            // Set the new scene and show it
            Scene endGameScene = new Scene(endGameRoot);
            stage.setScene(endGameScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}