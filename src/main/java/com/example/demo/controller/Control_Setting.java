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

    public void initialize() {
        // 添加按钮悬停音效
        addHoverSoundToButton(closeButton);

        // 监听背景音乐开关的变化
        bgToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleBackgroundMusic(newValue));

        // 监听爆炸音效开关的变化
        gsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> toggleExplosionSound(newValue));

        // 初始化爆炸音效开关的状态
        gsToggle.setSelected(LevelParent.isExplosionSoundEnabled());

        // 初始化背景音乐开关的状态
        bgToggle.setSelected(AudioManager.isBackgroundMusicPlaying());
    }

    // 为按钮添加悬停音效
    private void addHoverSoundToButton(Button button) {
        button.setOnMouseEntered(event -> AudioManager.playHoverSound());
    }

    // 设置背景音乐播放器，并根据复选框的状态初始化音乐
    private void toggleBackgroundMusic(boolean play) {
        AudioManager.setBackgroundMusicEnabled(play);
        if (play) {
            AudioManager.playBackgroundMusic(); // 播放背景音乐
        } else {
            AudioManager.pauseBackgroundMusic(); // 暂停背景音乐
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