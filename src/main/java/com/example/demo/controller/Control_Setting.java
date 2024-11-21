package com.example.demo.controller;

import com.example.demo.Level.LevelManager.ExplosionEffectManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class Control_Setting {

    @FXML private CheckBox bgToggle; // Used to switch background music
    @FXML private CheckBox gsToggle;  // Switch to control the explosion sound effect
    @FXML private Button closeButton; // Close button

    private Pane mainRoot; // The root container of the main interface

    public void initialize() {
        addHoverSoundToButton(closeButton);
        bgToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleBackgroundMusic(newValue));
        gsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleExplosionSound(newValue));
        gsToggle.setSelected(ExplosionEffectManager.isExplosionSoundEnabled());
        bgToggle.setSelected(AudioManager.isBackgroundMusicPlaying());
    }

    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> AudioManager.playHoverSound());
    }

    private void toggleBackgroundMusic(boolean play) {
        AudioManager.setBackgroundMusicEnabled(play);
        if (play) {
            AudioManager.playBackgroundMusic();
        } else {
            AudioManager.pauseBackgroundMusic();
        }
    }

    private void toggleExplosionSound(boolean play) {
        ExplosionEffectManager.setExplosionSoundEnabled(play);
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