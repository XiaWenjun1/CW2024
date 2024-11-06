package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

public class Control_Setting {

    @FXML private Slider musicSlider;
    @FXML private Slider effectSlider;
    @FXML private Button closeButton; // Close button

    private Pane mainRoot; // The root container of the main interface
    private MediaPlayer backgroundMusic; // Background music player
    private AudioClip hoverSound; // Hover sound effect

    public void initialize() {
        // Load hover sound effects
        hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        // Add mouse hover sound effect to closeButton
        addHoverSoundToButton(closeButton);
    }

    // Method to add hover sound effect
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> hoverSound.play());
    }

    // Set up the background music player
    public void setBackgroundMusic(MediaPlayer backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        // Bind the music volume to the slider value
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (this.backgroundMusic != null) {
                this.backgroundMusic.setVolume(newValue.doubleValue()); // Set the volume
            }
        });
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

    public Slider getMusicSlider() {
        return musicSlider;
    }

    public Slider getEffectSlider() {
        return effectSlider;
    }
}