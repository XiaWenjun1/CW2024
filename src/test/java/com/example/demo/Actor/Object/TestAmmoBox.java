package com.example.demo.Actor.Object;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAmmoBox {

    private AmmoBox ammoBox;

    @BeforeAll
    static void initJavaFX() {
        Platform.startup(() -> {}); // Initialize the JavaFX runtime
    }

    @BeforeEach
    void setUp() {
        // Initialize an AmmoBox with a specific starting position
        ammoBox = new AmmoBox(1000, 300);
    }

    @Test
    void testMoveHorizontally() {
        // Move the ammo box by a specific amount
        ammoBox.moveHorizontally(-20);

        // Test the new X position
        assertEquals(980, ammoBox.getTranslateX(), "X position should decrease by 20.");
    }

    @Test
    void testUpdatePosition() {
        // Update position, should move by the horizontal velocity
        ammoBox.updatePosition();

        // Test the new X position
        assertEquals(994, ammoBox.getTranslateX(), "X position should decrease by HORIZONTAL_VELOCITY (-6).");
    }

    @Test
    void testSpawnProbability() {
        // Test spawn probability
        assertEquals(0.01, AmmoBox.getSpawnProbability(), "Spawn probability should be 0.01.");
    }

    @Test
    void testMaximumPositions() {
        // Test maximum X and Y positions
        assertEquals(1350, AmmoBox.getMaximumXPosition(), "Maximum X position should be 1350.");
        assertEquals(600, AmmoBox.getMaximumYPosition(), "Maximum Y position should be 600.");
    }

    @Test
    void testTakeDamage() {
        // Test takeDamage behavior (should do nothing)
        assertDoesNotThrow(() -> ammoBox.takeDamage(), "takeDamage should not throw an exception.");
    }
}