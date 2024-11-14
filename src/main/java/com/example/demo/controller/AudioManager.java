package com.example.demo.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static MediaPlayer backgroundMusic;
    private static AudioClip hoverSound; // 音效文件路径
    private static boolean backgroundMusicEnabled = true;

    // 只初始化一次背景音乐播放器
    public static void initBackgroundMusic() {
        if (backgroundMusic == null) {
            Media media = new Media(AudioManager.class.getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);  // 循环播放背景音乐
        }
    }

    // 播放背景音乐的方法（根据背景音乐的开关来决定是否播放）
    public static void playBackgroundMusic() {
        if (backgroundMusicEnabled) {
            backgroundMusic.play(); // 只有开启背景音乐时才播放
        }
    }

    // 暂停背景音乐
    public static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        }
    }

    // 停止背景音乐
    public static void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    // 获取当前背景音乐的播放状态
    public static boolean isBackgroundMusicPlaying() {
        return backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING;
    }

    // 播放按钮悬停音效
    public static void playHoverSound() {
        if (hoverSound == null) {
            hoverSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        }
        hoverSound.play();
    }

    // 获取背景音乐
    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    // 设置背景音乐是否启用
    public static void setBackgroundMusicEnabled(boolean enabled) {
        backgroundMusicEnabled = enabled; // 更新背景音乐的状态
    }

    // 检查背景音乐是否启用
    public static boolean isBackgroundMusicEnabled() {
        return backgroundMusicEnabled;
    }
}