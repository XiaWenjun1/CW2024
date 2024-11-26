package com.example.demo.controller;

import com.example.demo.Level.LevelManager.ExplosionEffectManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

/**
 * The Control_Setting class manages the settings interface in the game,
 * including toggling background music and explosion sound effects,
 * and handling the close functionality for the settings window.
 */
public class Control_Setting {

    @FXML private CheckBox bgToggle; // Used to switch background music
    @FXML private CheckBox gsToggle;  // Switch to control the explosion sound effect
    @FXML private Button closeButton; // Close button

    private Pane mainRoot; // The root container of the main interface

    /**
     * Initializes the settings interface by adding event listeners
     * and setting the initial values of the toggle switches based on
     * the current settings.
     */
    public void initialize() {
        addHoverSoundToButton(closeButton); // Add hover sound to close button
        bgToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleBackgroundMusic(newValue));
        gsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleExplosionSound(newValue));
        gsToggle.setSelected(ExplosionEffectManager.isExplosionSoundEnabled()); // Set initial state of explosion sound toggle
        bgToggle.setSelected(AudioManager.isBackgroundMusicPlaying()); // Set initial state of background music toggle
    }

    /**
     * Adds a hover sound effect to the specified button.
     *
     * @param button The button to add the hover sound effect to.
     */
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> AudioManager.playHoverSound()); // Play hover sound when mouse enters the button
    }

    /**
     * Toggles the background music on or off based on the provided boolean value.
     *
     * @param play If true, background music is enabled; if false, background music is paused.
     */
    private void toggleBackgroundMusic(boolean play) {
        AudioManager.setBackgroundMusicEnabled(play);
        if (play) {
            AudioManager.playBackgroundMusic(); // Play background music if enabled
        } else {
            AudioManager.pauseBackgroundMusic(); // Pause background music if disabled
        }
    }

    /**
     * Toggles the explosion sound effect on or off based on the provided boolean value.
     *
     * @param play If true, explosion sound is enabled; if false, explosion sound is disabled.
     */
    private void toggleExplosionSound(boolean play) {
        ExplosionEffectManager.setExplosionSoundEnabled(play); // Enable or disable explosion sound
    }

    /**
     * Sets the root container of the main interface to remove the blur effect when closing
     * the settings window.
     *
     * @param mainRoot The root container of the main interface.
     */
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    /**
     * Closes the settings window and removes the blur effect from the main interface.
     */
    @FXML
    private void closeSettings() {
        // Remove the blur effect
        if (mainRoot != null) {
            for (Node child : mainRoot.getChildren()) {
                child.setEffect(null); // Remove the blur effect from all elements
            }
            // Remove settingsPane from the main interface
            mainRoot.getChildren().removeIf(node -> "settingsPane".equals(node.getId()));
        }
    }
}