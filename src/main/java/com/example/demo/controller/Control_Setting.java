package com.example.demo.controller;

import com.example.demo.LevelParent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

public class Control_Setting {

    @FXML private CheckBox bgToggle; // Used to switch background music
    @FXML private CheckBox gsToggle;  // Switch to control the explosion sound effect
    @FXML private Button closeButton; // Close button

    private Pane mainRoot; // The root container of the main interface
    private MediaPlayer backgroundMusic; // Background music player
    private AudioClip hoverSound; // Hover sound effect

    public void initialize() {
        // Load hover sound effects
        hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        // Add mouse hover sound effect to closeButton
        addHoverSoundToButton(closeButton);

        // Monitor the changes of the music switch CheckBox
        bgToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleBackgroundMusic(newValue));

        // Monitor the changes of the explosion sound effect toggle
        gsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleExplosionSound(newValue));

        // Initialize the explosion sound toggle with the current state from LevelParent (only once)
        gsToggle.setSelected(LevelParent.isExplosionSoundEnabled());
    }

    // Method to add hover sound effect
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> hoverSound.play());
    }

    // Set up the background music player and set the initial music state according to the CheckBox state
    public void setBackgroundMusic(MediaPlayer backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        bgToggle.setSelected(backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING); // 设置开关初始状态
    }

    // Turn background music on or off according to the state of the CheckBox
    private void toggleBackgroundMusic(boolean play) {
        if (backgroundMusic != null) {
            if (play) {
                backgroundMusic.play(); // Play music
            } else {
                backgroundMusic.pause(); // Pause the music
            }
        }
    }

    private void toggleExplosionSound(boolean play) {
        // Save the game sound status for use elsewhere
        LevelParent.setExplosionSoundEnabled(play);
    }

    // Set the root container of the main interface to remove the blur effect when closing
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    // Close the settings window
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