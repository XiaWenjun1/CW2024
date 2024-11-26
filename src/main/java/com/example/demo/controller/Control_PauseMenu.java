package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import com.example.demo.Level.LevelManager.PauseMenuManager;

/**
 * The Control_PauseMenu class handles the functionality of the pause menu in the game.
 * It allows for showing and hiding the pause menu, as well as resuming the game.
 */
public class Control_PauseMenu {

    private PauseMenuManager pauseMenuManager; // Manages the pause menu functionality

    @FXML
    private AnchorPane pauseMenuPane; // The pause menu pane

    @FXML
    private Button continueButton; // The button to continue the game

    private Pane mainRoot; // The root container of the main interface

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
     * Hides the pause menu and the continue button without resuming the game.
     */
    public void hidePauseMenu() {
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }
}