package com.example.demo.controller;

import com.example.demo.Level.LevelManager.ExplosionEffectManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

/**
 * The Control_Setting class is responsible for managing the settings interface in the game.
 * It allows the user to toggle background music and explosion sound effects,
 * and provides the functionality to close the settings window.
 */
public class Control_Setting {

    /**
     * A checkbox to toggle the background music on or off.
     * This enables the user to enable or disable the background music during gameplay.
     */
    @FXML
    private CheckBox bgToggle; // Checkbox to toggle background music

    /**
     * A checkbox to toggle the explosion sound effect on or off.
     * This enables the user to enable or disable the explosion sound during gameplay.
     */
    @FXML
    private CheckBox gsToggle;  // Checkbox to toggle explosion sound effect

    /**
     * The button used to close the settings window.
     * It exits the game or closes the current settings view.
     */
    @FXML
    private Button closeButton; // Close button

    /**
     * The root container of the main user interface.
     * It holds all the UI components for the main screen.
     */
    private Pane mainRoot; // The root container for the main interface

    /**
     * Default constructor for the Control_Setting class.
     * Initializes default values for the fields.
     */
    public Control_Setting() {
        this.mainRoot = null;
    }

    /**
     * Initializes the settings interface by adding event listeners to the toggle checkboxes
     * and setting their initial values based on the current settings.
     */
    public void initialize() {
        addHoverSoundToButton(closeButton); // Add hover sound effect to the close button
        bgToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleBackgroundMusic(newValue));
        gsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleExplosionSound(newValue));

        // Set initial states for the toggles based on current settings
        gsToggle.setSelected(ExplosionEffectManager.isExplosionSoundEnabled()); // Set explosion sound toggle state
        bgToggle.setSelected(AudioManager.isBackgroundMusicPlaying()); // Set background music toggle state
    }

    /**
     * Adds a hover sound effect to the specified button.
     *
     * @param button The button to which the hover sound effect will be added.
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
     * Sets the root container of the main interface, used to remove the blur effect when closing the settings window.
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
        // Remove the blur effect from all elements in the main interface
        if (mainRoot != null) {
            for (Node child : mainRoot.getChildren()) {
                child.setEffect(null); // Remove the blur effect from all elements
            }
            // Remove the settings pane from the main interface
            mainRoot.getChildren().removeIf(node -> "settingsPane".equals(node.getId()));
        }
    }
}