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

    @FXML private StackPane rootPane; // 主界面根容器
    private BoxBlur blurEffect = new BoxBlur(10, 10, 3); // 模糊效果

    @FXML private Button startButton;
    @FXML private Button controlButton;
    @FXML private Button settingsButton;

    @FXML private AnchorPane animationController; // AnchorPane for the animation controller

    public void initialize() {
        loadAnimation(); // 加载动画控制器
        loadAudio(); // 加载音频
        setupButtonHoverSounds(); // 设置按钮悬停音效
        playBackgroundMusic(); // 播放背景音乐

        settingsButton.setOnAction(event -> openSettings());
        startButton.setOnAction(event -> startGame());
    }

    private void loadAnimation() {
        try {
            // 检查控制器实例是否已创建
            if (controlAnimation == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Animation/Animation.fxml"));
                AnchorPane animationPane = loader.load();
                animationController.getChildren().add(animationPane);
                controlAnimation = loader.getController(); // 获取Control_Animation实例

                controlAnimation.initialize(); // 确保动画初始化
                controlAnimation.startAnimations(); // 启动动画
            } else {
                controlAnimation.startAnimations(); // 如果已经存在，直接启动动画
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAudio() {
        // 只加载一次音频文件
        hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        Media media = new Media(getClass().getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
        backgroundMusic = new MediaPlayer(media);
        backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
    }

    private void setupButtonHoverSounds() {
        // 为每个按钮添加鼠标悬停音效
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
        backgroundMusic.play(); // 播放背景音乐
    }

    private void startGame() {
        releaseAnimationResources(); // 释放动画资源
        Control_Start controlStart = new Control_Start((Stage) rootPane.getScene().getWindow()); // 获取当前窗口Stage
        try {
            controlStart.launchGame(); // 启动游戏
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSettings() {
        try {
            // 加载 settings.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Setting/Setting.fxml"));
            AnchorPane settingsPane = loader.load();

            // 设置 settingsPane 的 ID，以便 closeSettings 时查找和移除
            settingsPane.setId("settingsPane");

            // 获取 Control_Setting 控制器实例并传递主界面根容器
            Control_Setting settingsController = loader.getController();
            settingsController.setMainRoot(rootPane);

            // 传递背景音乐播放器到设置控制器
            settingsController.setBackgroundMusic(backgroundMusic);

            // 仅对 rootPane 内部的主界面内容应用模糊效果，而不影响新添加的设置界面
            for (Node child : rootPane.getChildren()) {
                if (!"settingsPane".equals(child.getId())) { // 排除设置界面
                    child.setEffect(blurEffect);
                }
            }

            // 在主界面的最上层叠加设置面板
            rootPane.getChildren().add(settingsPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseAnimationResources() {
        if (controlAnimation != null) {
            controlAnimation.stopAnimations(); // 停止动画
            controlAnimation = null; // 释放控制器引用，允许垃圾回收
        }
    }
}