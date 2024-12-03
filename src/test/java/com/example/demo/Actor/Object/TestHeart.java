package com.example.demo.Actor.Object;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeart {

    private Heart heart;
    private static boolean initialized = false;

    public static synchronized void initialize() {
        if (!initialized) {
            Platform.startup(() -> {}); // Initialize JavaFX runtime
            initialized = true;
        }
    }

    @BeforeEach
    void setUp() {
        // Initialize a Heart object with a specific starting position
        heart = new Heart(1000, 300);
    }

    @Test
    void testMoveHorizontally() {
        // Move the heart by a specific amount
        heart.moveHorizontally(-20);

        // Test the new X position
        assertEquals(980, heart.getTranslateX(), "X position should decrease by 20.");
    }

    @Test
    void testMoveVertically() {
        // Test vertical oscillation effect
        double initialY = heart.getTranslateY();
        heart.updatePosition(); // Update to apply the vertical movement

        double updatedY = heart.getTranslateY();
        assertNotEquals(initialY, updatedY, "Y position should change due to vertical oscillation.");
    }

    @Test
    void testUpdatePosition() {
        // Update position to simulate frame update
        double initialX = heart.getTranslateX();
        double initialY = heart.getTranslateY();

        heart.updatePosition();

        // Test the updated X and Y positions
        assertTrue(heart.getTranslateX() < initialX, "X position should decrease due to HORIZONTAL_VELOCITY.");
        assertNotEquals(initialY, heart.getTranslateY(), "Y position should oscillate vertically.");
    }

    @Test
    void testSpawnProbability() {
        // Test spawn probability
        assertEquals(0.005, Heart.getSpawnProbability(), "Spawn probability should be 0.005.");
    }

    @Test
    void testMaximumPositions() {
        // Test maximum X and Y positions
        assertEquals(1350, Heart.getMaximumXPosition(), "Maximum X position should be 1350.");
        assertEquals(600, Heart.getMaximumYPosition(), "Maximum Y position should be 600.");
    }

    @Test
    void testTakeDamage() {
        // Test takeDamage behavior (should do nothing)
        assertDoesNotThrow(() -> heart.takeDamage(), "takeDamage should not throw an exception.");
    }
}