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
        loadAnimation(); // 加载动画控制器

        // 初始化背景音乐，仅在第一次进入主界面时初始化
        if (AudioManager.getBackgroundMusic() == null) {
            AudioManager.initBackgroundMusic(); // 初始化背景音乐
        }

        // 仅在背景音乐启用状态为 true 且背景音乐未播放时才播放
        if (AudioManager.isBackgroundMusicEnabled() && !AudioManager.isBackgroundMusicPlaying()) {
            AudioManager.playBackgroundMusic();
        }

        // 设置按钮的鼠标悬停音效
        setupButtonHoverSounds();

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

    private void setupButtonHoverSounds() {
        // 为每个按钮添加鼠标悬停时的音效
        addHoverSoundToButton(startButton);
        addHoverSoundToButton(controlButton);
        addHoverSoundToButton(settingsButton);
    }

    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> {
            // 播放按钮悬停音效
            AudioManager.playHoverSound();
        });
    }

    private void startGame() {
        releaseAnimationResources(); // Release animation resources
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

                // 获取Control_Setting控制器实例，并设置主界面
                settingsController = loader.getController();
                settingsController.setMainRoot(rootPane);
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
            controlAnimation.stopAnimations();
        }
    }
}