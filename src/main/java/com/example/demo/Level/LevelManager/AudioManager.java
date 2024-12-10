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
     * AudioClip for get ammo box sound effects.
     * This clip is used to play sound effects when get ammo box in the game.
     */
    private static AudioClip getAmmoBoxSound;

    /**
     * AudioClip for get heart sound effects.
     * This clip is used to play sound effects when get heart in the game.
     */
    private static AudioClip getHeartSound;

    /**
     * AudioClip for user got damage sound effects.
     * This clip is used to play sound effects when user got damage in the game.
     */
    private static AudioClip userDamageSound;

    /**
     * AudioClip for shield sound effects.
     * This clip is used to play sound effects when boss active shield in the game.
     */
    private static AudioClip shieldSound;

    /**
     * AudioClip for the winning sound effect.
     * This clip is played when the player wins the game.
     */
    private static AudioClip winSound;

    /**
     * AudioClip for the losing sound effect.
     * This clip is played when the player loses the game.
     */
    private static AudioClip loseSound;

    /**
     * AudioClip for the teleport-in sound effect.
     * This clip is played when an entity teleports into the game.
     */
    private static AudioClip teleportInSound;

    /**
     * AudioClip for the teleport-out sound effect.
     * This clip is played when an entity teleports out of the game.
     */
    private static AudioClip teleportOutSound;

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
     * Flag to track if shield sound effects are enabled.
     * If true, shield sounds will play when the boss active shield; otherwise, they will be muted.
     */
    private static boolean shieldSoundEnabled = true;

    /**
     * Flag to track if user win, lose and level transfer sound effects are enabled.
     * If true, sounds will play, otherwise, they will be muted.
     */
    private static boolean interactionSoundEnabled = true;

    /**
     * Volume control for the shoot sound effect.
     * This value controls the volume of the shooting sound effect.
     */
    private final static double shootSoundVolume = 0.3;

    /**
     * Volume control for the background sound effect.
     * This value controls the volume of the background music.
     */
    private final static double backgroundVolume = 0.5;

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
     * This method will play the background music at the currently set volume level
     */
    public static void playBackgroundMusic() {
        if (backgroundMusicEnabled) {
            backgroundMusic.setVolume(backgroundVolume);
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
     * Initializes the ammo box pickup sound effect if it is not already initialized.
     * Loads the ammo box pickup sound resource for use in the game.
     */
    public static void initGetAmmoBoxSound() {
        if (getAmmoBoxSound == null) {
            getAmmoBoxSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/levelup.mp3").toExternalForm());
        }
    }

    /**
     * Initializes the second heart pickup sound effect if it is not already initialized.
     * Loads the second heart pickup sound resource for use in the game.
     */
    public static void initGetHeartSound() {
        if (getHeartSound == null) {
            getHeartSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/heal.mp3").toExternalForm());
        }
    }

    /**
     * Plays the ammo box pickup sound effect if the sound is enabled.
     * Ensures that the sound effect is initialized and plays it only if enabled.
     */
    public static void triggerGetAmmoBoxAudio() {
        if (getAmmoBoxSound == null) {
            initGetAmmoBoxSound();
        }

        if (getObjectSoundEnabled) {
            getAmmoBoxSound.play();
        }
    }

    /**
     * Plays the heart pickup sound effect if the sound is enabled.
     * Ensures that the sound effect is initialized and plays it only if enabled.
     */
    public static void triggerGetHeartAudio() {
        if (getHeartSound == null) {
            initGetHeartSound();
        }

        if (getObjectSoundEnabled) {
            getHeartSound.play();
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
            userDamageSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/userDamage.mp3").toExternalForm());
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

    // ================== Shield Sound Methods ====================

    /**
     * Initializes the shield sound effect if it is not already initialized.
     * This method loads the shield sound resource for use in the game, ensuring
     * that the sound effect is ready to be played when triggered.
     */
    public static void initShieldSound() {
        if (shieldSound == null) {
            shieldSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/shield.mp3").toExternalForm());
        }
    }

    /**
     * Plays the shield sound effect if the sound is enabled.
     * This method ensures that the sound effect is initialized and plays it only
     * if the sound is enabled. If the sound effect is not yet initialized, it will
     * initialize it before playing.
     */
    public static void triggerShieldAudio() {
        if (shieldSound == null) {
            initShieldSound();
        }

        if (shieldSoundEnabled) {
            shieldSound.play();
        }
    }

    /**
     * Enables or disables the shield sound effect.
     * This method allows the user to enable or disable the sound effect that plays
     * when the shield is activated. It modifies the sound effect's enabled state.
     *
     * @param enabled true to enable the shield sound, false to disable it.
     */
    public static void setShieldSoundEnabled(boolean enabled) {
        shieldSoundEnabled = enabled;
    }

    /**
     * Checks whether the shield sound effect is enabled.
     * This method checks the current state of the shield sound effect,
     * indicating whether the sound is enabled or not.
     *
     * @return true if the shield sound is enabled, false otherwise.
     */
    public static boolean isShieldSoundEnabled() {
        return shieldSoundEnabled;
    }

    // ================== Win, lose and level transfer Sound Methods ====================

    /**
     * Initializes the wining sound effect.
     */
    public static void initWinSound() {
        if (winSound == null) {
            winSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/win.mp3").toExternalForm());
        }
    }

    /**
     * Initializes the losing sound effect.
     */
    public static void initLoseSound() {
        if (loseSound == null) {
            loseSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/lose.mp3").toExternalForm());
        }
    }

    /**
     * Initializes the teleport-in sound effect.
     */
    public static void initTeleportInSound() {
        if (teleportInSound == null) {
            teleportInSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/teleportIn.mp3").toExternalForm());
        }
    }

    /**
     * Initializes the teleport-out sound effect.
     */
    public static void initTeleportOutSound() {
        if (teleportOutSound == null) {
            teleportOutSound = new AudioClip(AudioManager.class.getResource("/com/example/demo/sounds/teleportOut.mp3").toExternalForm());
        }
    }

    /**
     * Triggers the winning sound effect.
     */
    public static void triggerWinAudio() {
        if (winSound == null) {
            initWinSound();
        }

        if (interactionSoundEnabled) {
            winSound.play();
        }
    }

    /**
     * Triggers the losing sound effect.
     */
    public static void triggerLoseAudio() {
        if (loseSound == null) {
            initLoseSound();
        }

        if (interactionSoundEnabled) {
            loseSound.play();
        }
    }

    /**
     * Triggers the teleport-in sound effect.
     */
    public static void triggerTeleportInAudio() {
        if (teleportInSound == null) {
            initTeleportInSound();
        }

        if (interactionSoundEnabled) {
            teleportInSound.play();
        }
    }

    /**
     * Triggers the teleport-out sound effect.
     */
    public static void triggerTeleportOutAudio() {
        if (teleportOutSound == null) {
            initTeleportOutSound();
        }

        if (interactionSoundEnabled) {
            teleportOutSound.play();
        }
    }

    /**
     * Enables or disables interaction sound effects.
     *
     * @param enabled true to enable, false to disable interaction sounds
     */
    public static void setInteractionSoundEnabled(boolean enabled) {
        interactionSoundEnabled = enabled;
    }

    /**
     * Checks whether interaction sound effects are enabled.
     *
     * @return true if interaction sounds are enabled, false otherwise
     */
    public static boolean isInteractionSoundEnabled() {
        return interactionSoundEnabled;
    }
}