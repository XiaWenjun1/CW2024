package com.example.demo.Actor.Plane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSpeedEnemy {

    private SpeedEnemy speedEnemy;

    @BeforeEach
    void setUp() {
        // Initialize the SpeedEnemy object and set the initial position
        double initialX = 500;
        double initialY = 200;
        speedEnemy = new SpeedEnemy(initialX, initialY);
    }

    @Test
    void testInitialHealth() {
        // Verify the initial health of SpeedEnemy
        assertEquals(1, speedEnemy.getHealth());
    }

    @Test
    void testInitialHitbox() {
        // Verify that the collision box width and height match the dimensions of the SpeedEnemy
        assertNotNull(speedEnemy.getHitbox());
        assertEquals(70, speedEnemy.getHitbox().getWidth());
        assertEquals(70, speedEnemy.getHitbox().getHeight());
    }

    @Test
    void testUpdatePosition() {
        // Test that SpeedEnemy is moving left as expected
        double initialX = speedEnemy.getLayoutX();
        speedEnemy.updatePosition();
        assertEquals(initialX - 5, speedEnemy.getLayoutX() + speedEnemy.getTranslateX());
    }

    @Test
    void testFireProjectiles() {
        // Verify that SpeedEnemy doesn't fire any projectiles
        assertTrue(speedEnemy.fireProjectiles().isEmpty());
    }
}