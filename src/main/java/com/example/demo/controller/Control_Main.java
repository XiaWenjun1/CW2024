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
    private Control_Setting settingsController; // 保存设置控制器的实例

    @FXML private StackPane rootPane; // Main interface root container
    private BoxBlur blurEffect = new BoxBlur(10, 10, 3); // Blur effect

    @FXML private Button startButton;
    @FXML private Button controlButton;
    @FXML private Button settingsButton;

    @FXML private AnchorPane animationController; // AnchorPane for the animation controller
    private AnchorPane settingsPane; // 保存设置面板的实例

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
        try {
            // 检查音频是否已经加载
            if (backgroundMusic == null && hoverSound == null) {
                // 初始化 hoverSound
                hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());

                // 初始化 backgroundMusic
                Media media = new Media(getClass().getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
                backgroundMusic = new MediaPlayer(media);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupButtonHoverSounds() {
        // 检查 hoverSound 是否已加载，若未加载则初始化
        if (hoverSound == null) {
            loadButtonHoverSound();  // 加载音效
        }

        // 为每个按钮添加鼠标悬停时的音效
        addHoverSoundToButton(startButton);
        addHoverSoundToButton(controlButton);
        addHoverSoundToButton(settingsButton);
    }

    private void loadButtonHoverSound() {
        try {
            // 初始化 hoverSound
            hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> {
            // 确保 hoverSound 已初始化
            if (hoverSound != null) {
                hoverSound.play(); // 播放音效
            }
        });
    }

    private void playBackgroundMusic() {
        backgroundMusic.play(); // Play background music
    }

    private void startGame() {
        releaseAnimationResources(); // Release animation resources
        releaseBtnHoverResources();
        Control_Start controlStart = new Control_Start((Stage) rootPane.getScene().getWindow()); // Get the current window Stage
        try {
            controlStart.launchGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        try {
            if (settingsPane == null) { // 仅在settingsPane为null时加载FXML文件
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Setting/Setting.fxml"));
                settingsPane = loader.load();
                settingsPane.setId("settingsPane");

                // 获取Control_Setting控制器实例，并设置主界面和背景音乐
                settingsController = loader.getController();
                settingsController.setMainRoot(rootPane);
                settingsController.setBackgroundMusic(backgroundMusic);
            }

            // 为rootPane应用模糊效果（排除settingsPane）
            for (Node child : rootPane.getChildren()) {
                if (!"settingsPane".equals(child.getId())) {
                    child.setEffect(blurEffect);
                }
            }

            // 若settingsPane尚未添加到rootPane，则添加
            if (!rootPane.getChildren().contains(settingsPane)) {
                rootPane.getChildren().add(settingsPane);
            }
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

    private void releaseBtnHoverResources() {
        if (hoverSound != null) {
            hoverSound.stop();
            hoverSound = null;
        }
    }
}