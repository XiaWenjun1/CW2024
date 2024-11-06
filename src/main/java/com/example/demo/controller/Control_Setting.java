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
    @FXML private Button closeButton; // 关闭按钮

    private Pane mainRoot; // 主界面的根容器
    private MediaPlayer backgroundMusic; // 背景音乐播放器
    private AudioClip hoverSound; // 悬停音效

    public void initialize() {
        // 加载悬停音效
        hoverSound = new AudioClip(getClass().getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        // 给 closeButton 添加鼠标悬停音效
        addHoverSoundToButton(closeButton);
    }

    // 添加悬停音效的方法
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> hoverSound.play());
    }

    // 设置背景音乐播放器
    public void setBackgroundMusic(MediaPlayer backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
        // 绑定音乐音量到滑块的值
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (this.backgroundMusic != null) {
                this.backgroundMusic.setVolume(newValue.doubleValue()); // 设置音量
            }
        });
    }

    // 设置主界面根容器，用于关闭时移除模糊效果
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    // 关闭设置窗口
    @FXML
    private void closeSettings() {
        // 移除模糊效果
        if (mainRoot != null) {
            for (Node child : mainRoot.getChildren()) {
                child.setEffect(null); // 移除所有元素的模糊效果
            }
            // 从主界面中移除 settingsPane
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