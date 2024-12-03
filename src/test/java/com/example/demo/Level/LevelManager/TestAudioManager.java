package com.example.demo.Level.LevelManager;

import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAudioManager {

    @BeforeEach
    void setUp() {
        // Ensure that each test case initializes the state before
        AudioManager.getInstance().setBackgroundMusicEnabled(true);
        AudioManager.getInstance().setShootSoundEnabled(true);
        AudioManager.getInstance().setExplosionSoundEnabled(true);
        AudioManager.getInstance().setGetObjectSoundEnabled(true);
        AudioManager.getInstance().setUserDamageSoundEnabled(true);
    }

    @Test
    void testBackgroundMusicEnableDisable() {
        AudioManager.getInstance().setBackgroundMusicEnabled(false);
        assertFalse(AudioManager.getInstance().isBackgroundMusicEnabled(), "Background music should be disabled.");

        AudioManager.getInstance().playBackgroundMusic();
        MediaPlayer player = AudioManager.getInstance().getBackgroundMusic();
        if (player != null) {
            assertNotEquals(MediaPlayer.Status.PLAYING, player.getStatus(), "Background music should not play when disabled.");
        }
    }

    @Test
    void testHoverSoundInitializationAndPlay() {
        AudioManager.getInstance().initHoverSound();
        assertDoesNotThrow(AudioManager::playHoverSound, "Hover sound should play without throwing exceptions.");
    }

    @Test
    void testShootSoundInitializationAndPlay() {
        AudioManager.getInstance().initShootSound();
        assertDoesNotThrow(AudioManager::triggerShootAudio, "Shoot sound should play without throwing exceptions.");

        AudioManager.getInstance().setShootSoundEnabled(false);
        assertFalse(AudioManager.isShootSoundEnabled(), "Shoot sound should be disabled.");
    }

    @Test
    void testExplosionSoundInitializationAndPlay() {
        AudioManager.getInstance().initExplosionSound();
        assertDoesNotThrow(AudioManager::triggerExplosionAudio, "Explosion sound should play without throwing exceptions.");

        AudioManager.getInstance().setExplosionSoundEnabled(false);
        assertFalse(AudioManager.isExplosionSoundEnabled(), "Explosion sound should be disabled.");
    }

    @Test
    void testGetObjectSoundInitializationAndPlay() {
        AudioManager.getInstance().initGetObjectSound();
        assertDoesNotThrow(AudioManager::triggerGetObjectAudio, "Get Object sound should play without throwing exceptions.");

        AudioManager.getInstance().setGetObjectSoundEnabled(false);
        assertFalse(AudioManager.isGetObjectSoundEnabled(), "Get Object sound should be disabled.");
    }

    @Test
    void testUserDamageSoundInitializationAndPlay() {
        AudioManager.getInstance().initUserDamageSound();
        assertDoesNotThrow(AudioManager::triggerUserDamageAudio, "User Damage sound should play without throwing exceptions.");

        AudioManager.getInstance().setUserDamageSoundEnabled(false);
        assertFalse(AudioManager.isUserDamageSoundEnabled(), "User Damage sound should be disabled.");
    }
}