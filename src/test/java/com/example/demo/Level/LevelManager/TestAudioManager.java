package com.example.demo.Level.LevelManager;

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
        AudioManager.getInstance().setShieldSoundEnabled(true);
        AudioManager.getInstance().setInteractionSoundEnabled(true);
    }

    @Test
    void testBackgroundMusicEnableDisable() {
        AudioManager.getInstance().initBackgroundMusic();
        assertDoesNotThrow(AudioManager::playBackgroundMusic, "Background music should play without throwing exceptions.");

        AudioManager.getInstance().setBackgroundMusicEnabled(false);
        assertFalse(AudioManager.getInstance().isBackgroundMusicEnabled(), "Background music should be disabled.");
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
        AudioManager.getInstance().initGetAmmoBoxSound();
        assertDoesNotThrow(AudioManager::triggerGetAmmoBoxAudio, "Get Ammo Box sound should play without throwing exceptions.");
        AudioManager.getInstance().initGetHeartSound();
        assertDoesNotThrow(AudioManager::triggerGetHeartAudio, "Get Heart sound should play without throwing exceptions.");

        AudioManager.getInstance().setGetObjectSoundEnabled(false);
        assertFalse(AudioManager.isGetObjectSoundEnabled(), "Get Object sounds should be disabled.");
    }

    @Test
    void testUserDamageSoundInitializationAndPlay() {
        AudioManager.getInstance().initUserDamageSound();
        assertDoesNotThrow(AudioManager::triggerUserDamageAudio, "User Damage sound should play without throwing exceptions.");

        AudioManager.getInstance().setUserDamageSoundEnabled(false);
        assertFalse(AudioManager.isUserDamageSoundEnabled(), "User Damage sound should be disabled.");
    }

    @Test
    void testShieldSoundInitializationAndPlay() {
        AudioManager.getInstance().initShieldSound();
        assertDoesNotThrow(AudioManager::triggerShieldAudio, "Active shield sound should play without throwing exceptions.");

        AudioManager.getInstance().setShieldSoundEnabled(false);
        assertFalse(AudioManager.isShieldSoundEnabled(), "Active shield sound should be disabled.");
    }

    @Test
    void testInteractionSoundInitializationAndPlay() {
        AudioManager.getInstance().initWinSound();
        assertDoesNotThrow(AudioManager::triggerWinAudio, "Win sound should play without throwing exceptions.");
        AudioManager.getInstance().initLoseSound();
        assertDoesNotThrow(AudioManager::triggerLoseAudio, "Lose sound should play without throwing exceptions.");
        AudioManager.getInstance().initTeleportInSound();
        assertDoesNotThrow(AudioManager::triggerTeleportInAudio, "Teleport-In sound should play without throwing exceptions.");
        AudioManager.getInstance().initTeleportOutSound();
        assertDoesNotThrow(AudioManager::triggerTeleportOutAudio, "Teleport-Out sound should play without throwing exceptions.");

        AudioManager.getInstance().setInteractionSoundEnabled(false);
        assertFalse(AudioManager.isInteractionSoundEnabled(), "Interaction sounds should be disabled.");
    }
}