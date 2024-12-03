package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeavyEnemy {

    private HeavyEnemy heavyEnemy;

    @BeforeEach
    void setUp() {
        // Initialize HeavyEnemy with a starting position
        double initialX = 500;
        double initialY = 300;
        heavyEnemy = new HeavyEnemy(initialX, initialY);
    }

    @Test
    void testInitialHealth() {
        // Verify the initial health is correct
        assertEquals(5, heavyEnemy.getHealth());
    }

    @Test
    void testUpdatePosition() {
        // Update the position and verify the enemy moves left
        double initialX = heavyEnemy.getLayoutX();
        heavyEnemy.updatePosition();
        assertEquals(initialX - 1, heavyEnemy.getLayoutX() + heavyEnemy.getTranslateX());
    }

    @Test
    void testHitboxInitialization() {
        // Verify the hitbox is initialized with the correct dimensions
        assertNotNull(heavyEnemy.getHitbox());
        assertEquals(100, heavyEnemy.getHitbox().getWidth());
        assertEquals(100, heavyEnemy.getHitbox().getHeight());
    }

    @Test
    void testFireProjectiles() {
        // Test projectile firing logic
        List<ActiveActorDestructible> projectiles = heavyEnemy.fireProjectiles();

        // Verify that projectiles are either fired or not based on FIRE_RATE
        if (!projectiles.isEmpty()) {
            assertEquals(1, projectiles.size());
            assertNotNull(projectiles.get(0));
        }
    }
}