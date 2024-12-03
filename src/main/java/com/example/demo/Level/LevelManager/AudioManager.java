package com.example.demo.Level.LevelManager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Manages the audio functionalities of the game, including background music and sound effects.
 * Provides methods for initializing, playing, pausing, and managing background music, sound effects,
 * and explosion sounds.
 */
public class AudioManager {

    /**
     * Singleton instance of the AudioManager.
     */
    private static AudioManager instance;

    /**
     * MediaPlayer for background music.
     * This player is used to control the background music playback in the game.
     */
    private static MediaPlayer backgroundMusic;

    /**
     * AudioClip for hover sound effects (button hover sound).
     * This clip is used to play sound effects when buttons or interactive elements are hovered.
     */
    private static AudioClip hoverSound;

    /**
     * AudioClip for shooting sound effects.
     * This clip is used to play sound effects when the player shoots.
     */
    private static AudioClip shootSound;

    /**
     * AudioClip for explosion sound effects.
     * This clip is used to play sound effects when an explosion occurs in the game.
     */
    private static AudioClip explosionSound;

    /**
     * AudioClip for get object sound effects.
     * This clip is used to play sound effects when get object in the game.
     */
    private static AudioClip getObjectSound;

    /**
     * AudioClip for user got damage sound effects.
     * This clip is used to play sound effects when user got damage in the game.
     */
    private static AudioClip userDamageSound;

    /**
     * Flag to track if explosion sound is enabled.
     * If true, explosion sounds are played when triggered; otherwise, they are muted.
     */
    private static boolean explosionSoundEnabled = true;

    /**
     * Flag to track if background music is enabled.
     * If true, the background music will play; otherwise, it will be muted.
     */
    private static boolean backgroundMusicEnabled = true;

    /**
     * Flag to track if shooting sound is enabled.
     * If true, shooting sounds will play when the player shoots; otherwise, they are muted.
     */
    private static boolean shootSoundEnabled = true;

    /**
     * Flag to track if object pickup sound effects are enabled.
     * If true, object pickup sounds will play when the player collects items; otherwise, they will be muted.
     */
    private static boolean getObjectSoundEnabled = true;

    /**
     * Flag to track if user damage sound effects are enabled.
     * If true, user damage sounds will play when the player takes damage; otherwise, they will be muted.
     */
    private static boolean userDamageSoundEnabled = true;

    /**
     * Volume control for the shoot sound effect.
     * This value controls the volume of the shooting sound effect.
     */
    private final static double shootSoundVolume = 0.3;

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private AudioManager() {
        // Initialize resources if needed
    }

    /**
     * Returns the singleton instance of AudioManager.
     * If the instance is not already created, it will create it.
     *
     * @return the AudioManager instance.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // ================== Background Music Methods ====================

    /**
     * Initializes the background music player if not already initialized.
     * Loads the background music from the resources.
     */
    public static void initBackgroundMusic() {
        if (backgroundMusic == null) {
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

    /**
     * Returns the background music MediaPlayer instance.
     *
     * @return the background music MediaPlayer instance, or null if not initialized.
     */
    public static MediaPlayer getBackgroundMusic() {
        return backgroundMusic;
    }

    // ================== Hover Sound Methods ====================

    /**
     * Initializes the hover sound effect if not already initialized.
     * Loads the hover sound from the resources.
     */
    public static void initHoverSound() {
        if (hoverSound == null) {
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

    // ================== Shoot Sound Methods ====================

    /**
     * Initializes the shoot sound effect if not already initialized.
     * Loads the shooting sound from the resources.
     */
    public static void initShootSound() {
        if (shootSound == null) {
            shootSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/shoot.mp3").toExternalForm());
        }
    }

    /**
     * Plays the shooting sound effect if the sound is enabled.
     * This method will play the shoot sound at the currently set volume level
     * only if the shooting sound is enabled.
     */
    public static void triggerShootAudio() {
        if (shootSound == null) {
            initShootSound(); // Initialize the shoot sound if not already initialized
        }

        if (shootSoundEnabled) {
            shootSound.setVolume(shootSoundVolume);
            shootSound.play();
        }
    }

    /**
     * Enables or disables the shooting sound.
     *
     * @param enabled true to enable the shooting sound, false to disable it.
     */
    public static void setShootSoundEnabled(boolean enabled) {
        shootSoundEnabled = enabled;
    }

    /**
     * Returns whether the shooting sound is enabled or not.
     *
     * @return true if the shooting sound is enabled, false otherwise.
     */
    public static boolean isShootSoundEnabled() {
        return shootSoundEnabled;
    }

    // ================== Explosion Sound Methods ====================

    /**
     * Initializes the explosion sound effect if not already initialized.
     * Loads the explosion sound from the resources.
     */
    public static void initExplosionSound() {
        if (explosionSound == null) {
            explosionSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/explosion.mp3").toExternalForm());
        }
    }

    /**
     * Plays the explosion sound effect if the sound is enabled.
     * This method will play the explosion sound at the currently set volume level
     * only if the explosion sound is enabled.
     */
    public static void triggerExplosionAudio() {
        if (explosionSound == null) {
            initExplosionSound(); // Initialize the explosion sound if not already initialized
        }

        if (explosionSoundEnabled) {
            explosionSound.play();
        }
    }

    /**
     * Enables or disables the explosion sound.
     *
     * @param enabled true to enable the explosion sound, false to disable it.
     */
    public static void setExplosionSoundEnabled(boolean enabled) {
        explosionSoundEnabled = enabled;
    }

    /**
     * Returns whether the explosion sound is enabled or not.
     *
     * @return true if the explosion sound is enabled, false otherwise.
     */
    public static boolean isExplosionSoundEnabled() {
        return explosionSoundEnabled;
    }

    // ================== Object Pickup Sound Methods ====================

    /**
     * Initializes the object pickup sound effect if it is not already initialized.
     * Loads the object pickup sound resource for use in the game.
     */
    public static void initGetObjectSound() {
        if (getObjectSound == null) {
            getObjectSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/getObject.mp3").toExternalForm());
        }
    }

    /**
     * Plays the object pickup sound effect if the sound is enabled.
     * Ensures that the sound effect is initialized and plays it only if enabled.
     */
    public static void triggerGetObjectAudio() {
        if (getObjectSound == null) {
            initGetObjectSound();
        }

        if (getObjectSoundEnabled) {
            getObjectSound.play();
        }
    }

    /**
     * Enables or disables the object pickup sound effect.
     *
     * @param enabled true to enable the object pickup sound, false to disable it.
     */
    public static void setGetObjectSoundEnabled(boolean enabled) {
        getObjectSoundEnabled = enabled;
    }

    /**
     * Checks whether the object pickup sound effect is enabled.
     *
     * @return true if the object pickup sound is enabled, false otherwise.
     */
    public static boolean isGetObjectSoundEnabled() {
        return getObjectSoundEnabled;
    }

    // ================== User Damage Sound Methods ====================

    /**
     * Initializes the user damage sound effect if it is not already initialized.
     * Loads the user damage sound resource for use in the game.
     */
    public static void initUserDamageSound() {
        if (userDamageSound == null) {
            userDamageSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/userDamage.wav").toExternalForm());
        }
    }

    /**
     * Plays the user damage sound effect if the sound is enabled.
     * Ensures that the sound effect is initialized and plays it only if enabled.
     */
    public static void triggerUserDamageAudio() {
        if (userDamageSound == null) {
            initUserDamageSound();
        }

        if (userDamageSoundEnabled) {
            userDamageSound.play();
        }
    }

    /**
     * Enables or disables the user damage sound effect.
     *
     * @param enabled true to enable the user damage sound, false to disable it.
     */
    public static void setUserDamageSoundEnabled(boolean enabled) {
        userDamageSoundEnabled = enabled;
    }

    /**
     * Checks whether the user damage sound effect is enabled.
     *
     * @return true if the user damage sound is enabled, false otherwise.
     */
    public static boolean isUserDamageSoundEnabled() {
        return userDamageSoundEnabled;
    }
}