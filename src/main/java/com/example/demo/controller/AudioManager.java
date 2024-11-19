package com.example.demo.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static MediaPlayer backgroundMusic;
    private static AudioClip hoverSound;
    private static boolean backgroundMusicEnabled = true;

    public static void initBackgroundMusic() {
        if (backgroundMusic == null) {
            Media media = new Media(AudioManager.class.getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    public static void playBackgroundMusic() {
        if (backgroundMusicEnabled) {
            backgroundMusic.play();
        }
    }

    public static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        }
    }

    public static boolean isBackgroundMusicPlaying() {
        return backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public static void initHoverSound() {
        if (hoverSound == null) {
            hoverSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        }
    }

    public static void playHoverSound() {
        if (hoverSound == null) {
            initHoverSound();
        }
        hoverSound.play();
    }

    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    public static void setBackgroundMusicEnabled(boolean enabled) {
        backgroundMusicEnabled = enabled;
    }

    public static boolean isBackgroundMusicEnabled() {
        return backgroundMusicEnabled;
    }
}