package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;

public class Control_Main {
    private AudioClip hoverSound; // Audio file path
    private MediaPlayer backgroundMusic; // Background music
    private Control_Animation controlAnimation; // Animation controller

    @FXML private StackPane rootPane; // Main interface root container
    private BoxBlur blurEffect = new BoxBlur(10, 10, 3); // Blur effect

    @FXML private Button startButton;
    @FXML private Button controlButton;
    @FXML private Button settingsButton;

    @FXML private AnchorPane animationController; // AnchorPane for the animation controller

    public void initialize() {
        loadAnimation(); // Load the animation controller
        loadAudio(); // Load the audio
        setupButtonHoverSounds(); // Set the button hover sound effect
        playBackgroundMusic(); // Play background music

        settingsButton.setOnAction(event -> openSettings());
        startButton.setOnAction(event -> startGame());
    }

    private void loadAnimation() {
        try {
            // Check if the controller instance has been created
            if (controlAnimation == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Animation/Animation.fxml"));
                AnchorPane animationPane = loader.load();
                animationController.getChildren().add(animationPane);
                controlAnimation = loader.getController(); // Get the Control_Animation instance

                controlAnimation.initialize(); // Make sure the animation is initialized
                controlAnimation.startAnimations(); // Start the animation
            } else {
                controlAnimation.startAnimations(); // If it already exists, start the animation directly
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAudio() {
        // Load the audio file only once
        hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        Media media = new Media(getClass().getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void setupButtonHoverSounds() {
        // Add mouse hover sound effects to each button
        addHoverSoundToButton(startButton);
        addHoverSoundToButton(controlButton);
        addHoverSoundToButton(settingsButton);
    }

    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> {
            if (hoverSound != null) {
                hoverSound.play();
            }
        });
    }

    private void playBackgroundMusic() {
        backgroundMusic.play(); // Play background music
    }

    private void startGame() {
        releaseAnimationResources(); // Release animation resources
        Control_Start controlStart = new Control_Start((Stage) rootPane.getScene().getWindow()); // Get the current window Stage
        try {
            controlStart.launchGame(); // Start the game
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        try {
            // Load settings.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Setting/Setting.fxml"));
            AnchorPane settingsPane = loader.load();

            // Set the settingsPane ID so that it can be found and removed when closeSettings
            settingsPane.setId("settingsPane");

            // Get the Control_Setting controller instance and pass the main interface root container
            Control_Setting settingsController = loader.getController();
            settingsController.setMainRoot(rootPane);

            // Pass the background music player to the settings controller
            settingsController.setBackgroundMusic(backgroundMusic);

            // Apply the blur effect only to the main interface content inside the rootPane, without affecting the newly added settings interface
            for (Node child : rootPane.getChildren()) {
                if (!"settingsPane".equals(child.getId())) { // 排除设置界面
                    child.setEffect(blurEffect);
                }
            }

            // Overlay the settings panel on the top layer of the main interface
            rootPane.getChildren().add(settingsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseAnimationResources() {
        if (controlAnimation != null) {
            controlAnimation.stopAnimations(); // Stop the animation
            controlAnimation = null; // Release the controller reference to allow garbage collection
        }
    }
}