package com.example.demo.Level.LevelManager;

import javafx.scene.media.AudioClip;

/**
 * Manages the shooting sound in the game.
 * <p>
 * This class is responsible for controlling the playback of the shooting sound effect.
 * It allows enabling or disabling the sound, as well as adjusting the volume of the sound.
 * </p>
 */
public class ShootAudioManager {

    /**
     * The path to the shooting sound file.
     */
    private static final String SHOOT_SOUND_PATH = "/com/example/demo/sounds/shoot.mp3";

    /**
     * The AudioClip object that plays the shooting sound.
     */
    private static AudioClip shootSound = new AudioClip(
            ExplosionEffectManager.class.getResource(SHOOT_SOUND_PATH).toExternalForm());

    /**
     * A flag indicating whether the shooting sound is enabled.
     * If set to true, the sound will play when triggered. If false, no sound will play.
     */
    private static boolean shootSoundEnabled = true;

    /**
     * The volume of the shooting sound.
     * The value ranges from 0.0 (mute) to 1.0 (full volume).
     */
    private static double shootSoundVolume = 0.4;

    /**
     * Constructs a ShootAudioManager instance.
     * This constructor does not initialize any fields as they are static.
     */
    public ShootAudioManager() {
        // Default constructor, no initialization required for static fields
    }

    /**
     * Triggers the shooting sound effect if the sound is enabled.
     * <p>
     * This method will play the shooting sound at the currently set volume level
     * only if the shooting sound is enabled.
     * </p>
     */
    public static void triggerShootAudio() {
        if (shootSoundEnabled) {
            shootSound.setVolume(shootSoundVolume);
            shootSound.play();
        }
    }

    /**
     * Enables or disables the shooting sound.
     *
     * @param enabled a boolean flag indicating whether the shooting sound should be played
     */
    public static void setExplosionSoundEnabled(boolean enabled) {
        shootSoundEnabled = enabled;
    }

    /**
     * Returns whether the shooting sound is enabled or not.
     *
     * @return true if the shooting sound is enabled, false otherwise
     */
    public static boolean isShootSoundEnabled() {
        return shootSoundEnabled;
    }
}