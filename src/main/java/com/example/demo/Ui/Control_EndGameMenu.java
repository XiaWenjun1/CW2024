package com.example.demo.Ui;

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

/**
 * The Control_EndGameMenu class handles the actions when the game ends.
 * It provides functionality to return to the main menu or exit the game.
 */
public class Control_EndGameMenu {
    /**
     * The button to return to the main menu.
     * This button is used to navigate back to the main menu from the current screen.
     */
    @FXML
    private Button returnToMainButton; // Button to return to the main menu

    /**
     * The current level parent to clean up when returning to the main menu.
     * This object manages the state and resources for the current level.
     */
    private LevelParent levelParent; // The current level parent to clean up when returning to the main menu

    /**
     * No-argument constructor for the Control_EndGameMenu class.
     * <p>
     * Initializes the levelParent field to null. This constructor is used for creating an instance of
     * the Control_EndGameMenu class without any specific initialization, leaving the levelParent to
     * be set later via the initialize method.
     * </p>
     */
    public Control_EndGameMenu() {
        this.levelParent = null;
    }

    /**
     * Initializes the controller with the provided LevelParent.
     *
     * @param levelParent The parent of the current level, used for cleanup.
     */
    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    /**
     * Handles the action when the "Return to Main" button is clicked.
     * Cleans up the current level and loads the main menu scene.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleReturnToMainButton(ActionEvent event) {
        levelParent.cleanUp(); // Clean up the current level before returning to the main menu

        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot, 1300, 750);

            // Get the current stage and set the new scene
            Stage stage = (Stage) returnToMainButton.getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the "Exit" button is clicked.
     * This will exit the application.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit(); // Exit the application
    }
}