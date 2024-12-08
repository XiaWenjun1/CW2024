package com.example.demo.Level.LevelManager;

import com.example.demo.Level.LevelParent;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Manages the end game process by handling win/lose scenarios
 * and transitioning to the end game menu.
 * This class ensures the end game logic is executed only once
 * to avoid redundant processing or UI conflicts.
 */
public class EndGameMenuManager {

    /** Reference to the parent level that contains game elements and views. */
    private LevelParent levelParent;

    /** Flag to ensure the end game logic is executed only once. */
    private boolean isGameOver = false;

    /**
     * Constructs an EndGameMenuManager tied to a specific LevelParent.
     *
     * @param levelParent the parent level that contains the game logic and visuals.
     */
    public EndGameMenuManager(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Handles the actions when the player wins the game.
     * Displays a "win" image and transitions to the end game menu after a delay.
     */
    public void winGame() {
        if (isGameOver) return; // Prevent multiple calls
        isGameOver = true;

        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            // Display the win image
            levelParent.getLevelView().showWinImage();

            // Transition to the end game menu after a 2-second delay
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> showEndGameMenu(currentStage));
            delay.play();
        });
    }

    /**
     * Handles the actions when the player loses the game.
     * Displays a "game over" image and transitions to the end game menu after a delay.
     */
    public void loseGame() {
        if (isGameOver) return; // Prevent multiple calls
        isGameOver = true;

        Stage currentStage = getCurrentStage();

        Platform.runLater(() -> {
            // Display the game over image
            levelParent.getLevelView().showGameOverImage();

            // Transition to the end game menu after a 2-second delay
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> showEndGameMenu(currentStage));
            delay.play();
        });
    }

    /**
     * Retrieves the current stage from the LevelParent.
     * The stage is required to update the scene for the end game menu.
     *
     * @return the current stage of the application, or {@code null} if not found
     */
    private Stage getCurrentStage() {
        Scene currentScene = levelParent.getRoot().getScene();
        if (currentScene != null) {
            return (Stage) currentScene.getWindow();
        }
        return null;
    }

    /**
     * Loads and displays the end game menu.
     * The menu is configured differently depending on whether the player won or lost.
     *
     * @param currentStage the stage to be closed after showing the end game menu
     */
    private void showEndGameMenu(Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/EndGameMenu/EndGameMenu.fxml"));
            Parent endGameRoot = loader.load();

            Stage endGameStage = new Stage();
            Scene endGameScene = new Scene(endGameRoot);

            endGameStage.setScene(endGameScene);
            endGameStage.show();

            if (currentStage != null) {
                currentStage.close();
            }

            levelParent.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}