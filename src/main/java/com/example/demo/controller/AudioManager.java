package com.example.demo.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages the audio functionalities of the game, including background music and sound effects.
 * Provides methods for initializing, playing, pausing, and managing background music and hover sounds.
 */
public class AudioManager {

    // The MediaPlayer for background music
    /**
     * A MediaPlayer instance for playing the background music of the game.
     * This player is used to control the background music playback.
     */
    private static MediaPlayer backgroundMusic;

    // The AudioClip for hover sound effects
    /**
     * An AudioClip instance for playing sound effects when buttons are hovered.
     * This clip is used to manage hover sound effects during user interactions.
     */
    private static AudioClip hoverSound;

    // Flag to track if background music is enabled
    /**
     * A flag that indicates whether the background music is enabled.
     * This value controls whether the background music will be played or paused.
     */
    private static boolean backgroundMusicEnabled = true;

    /**
     * Initializes the background music player if not already initialized.
     * Loads the background music from the resources.
     */
    public static void initBackgroundMusic() {
        if (backgroundMusic == null) {
            // Load the background music from resources
            Media media = new Media(AudioManager.class.getResource("/com/example/demo/sounds/bg.mp3").toExternalForm());
            backgroundMusic = new MediaPlayer(media);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        }
    }

    /**
     * Plays the background music if it's enabled.
     */
    public static void playBackgroundMusic() {
        if (backgroundMusicEnabled) {
            backgroundMusic.play();
        }
    }

    /**
     * Pauses the background music if it's currently playing.
     */
    public static void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusic.pause();
        }
    }

    /**
     * Checks if the background music is currently playing.
     *
     * @return true if background music is playing, false otherwise.
     */
    public static boolean isBackgroundMusicPlaying() {
        return backgroundMusic != null && backgroundMusic.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * Initializes the hover sound effect if not already initialized.
     * Loads the hover sound from the resources.
     */
    public static void initHoverSound() {
        if (hoverSound == null) {
            // Load the hover sound from resources
            hoverSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/btnhover.wav").toExternalForm());
        }
    }

    /**
     * Plays the hover sound effect.
     * This sound is played when the user hovers over buttons or interactive elements.
     */
    public static void playHoverSound() {
        if (hoverSound == null) {
            initHoverSound(); // Initialize the hover sound if not already initialized
        }
        hoverSound.play();
    }

    /**
     * Gets the background music MediaPlayer.
     *
     * @return the background music MediaPlayer instance.
     */
    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    /**
     * Enables or disables the background music.
     *
     * @param enabled true to enable background music, false to disable it.
     */
    public static void setBackgroundMusicEnabled(boolean enabled) {
        backgroundMusicEnabled = enabled;
    }

    /**
     * Checks if background music is enabled.
     *
     * @return true if background music is enabled, false otherwise.
     */
    public static boolean isBackgroundMusicEnabled() {
        return backgroundMusicEnabled;
    }
}