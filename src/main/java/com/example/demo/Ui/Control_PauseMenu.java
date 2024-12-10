package com.example.demo.Ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import com.example.demo.Level.LevelManager.PauseMenuManager;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Control_PauseMenu class handles the functionality of the pause menu in the game.
 * It allows for showing and hiding the pause menu, as well as resuming the game.
 */
public class Control_PauseMenu {
    /**
     * The button to return to the main menu.
     * This button is used to navigate back to the main menu from the current screen.
     */
    @FXML
    private Button returnToMainButton; // Button to return to the main menu

    /**
     * Manages the functionality of the pause menu in the game.
     * This object handles the logic for pausing, resuming, and closing the game.
     */
    private PauseMenuManager pauseMenuManager; // Manages the pause menu functionality

    /**
     * The AnchorPane that contains the pause menu.
     * This pane holds the UI elements for the pause menu.
     */
    @FXML
    private AnchorPane pauseMenuPane; // The pause menu pane

    /**
     * The button that allows the player to continue the game after it has been paused.
     * This button resumes the game and hides the pause menu.
     */
    @FXML
    private Button continueButton; // The button to continue the game

    /**
     * The root container of the main user interface.
     * This container holds all UI components for the main screen.
     */
    private Pane mainRoot; // The root container of the main interface

    /**
     * No-argument constructor for Control_PauseMenu.
     * Initializes default values for fields and ensures the pause menu is hidden initially.
     */
    public Control_PauseMenu() {
        // Initialize fields with default values
        this.pauseMenuManager = null;
        this.mainRoot = null;
    }

    /**
     * Initializes the pause menu controller with the provided PauseMenuManager.
     * It hides the pause menu and continue button by default.
     *
     * @param pauseMenuManager The PauseMenuManager instance used to manage the game state.
     */
    public void initialize(PauseMenuManager pauseMenuManager) {
        this.pauseMenuManager = pauseMenuManager;

        // Hide the pause menu and continue button initially
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }

    /**
     * Toggles the visibility of the pause menu and continue button.
     * If the pause menu is hidden, it will be shown, and vice versa.
     */
    public void showPauseMenu() {
        boolean isVisible = pauseMenuPane.isVisible();
        pauseMenuPane.setVisible(!isVisible);
        continueButton.setVisible(!isVisible);
    }

    /**
     * Handles the continue button click to resume the game.
     * Hides the pause menu and continue button, and removes the blur effect from the main interface.
     */
    @FXML
    private void handleContinue() {
        pauseMenuManager.continueGame(); // Resumes the game through the PauseMenuManager
        pauseMenuPane.setVisible(false); // Hide the pause menu
        continueButton.setVisible(false); // Hide the continue button

        // Remove the blur effect if the mainRoot is set
        if (mainRoot != null) {
            mainRoot.setEffect(null);
        }
    }

    /**
     * Handles the action when the "Return to Main" button is clicked.
     * Cleans up the current level and loads the main menu scene.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleReturnToMainButton(ActionEvent event) {
        try {
            // Load the main menu scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot, 1300, 750);

            // Get the current stage and set the new scene
            Stage stage = (Stage) returnToMainButton.getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
            pauseMenuManager.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Hides the pause menu and the continue button without resuming the game.
     */
    public void hidePauseMenu() {
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }
}