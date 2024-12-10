package com.example.demo.Actor.Projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeavyEnemyProjectile {

    private HeavyEnemyProjectile heavyEnemyProjectile;

    @BeforeEach
    void setUp() {
        // Initialize HeavyEnemyProjectile with a starting position
        double initialX = 400;
        double initialY = 300;
        heavyEnemyProjectile = new HeavyEnemyProjectile(initialX, initialY);
    }

    @Test
    void testInitialHitbox() {
        // Verify the hitbox dimensions match the projectile's size
        assertNotNull(heavyEnemyProjectile.getHitbox());
        assertEquals(50, heavyEnemyProjectile.getHitbox().getWidth());
        assertEquals(50, heavyEnemyProjectile.getHitbox().getHeight());
    }

    @Test
    void testUpdatePosition() {
        // Verify the projectile moves left when updatePosition is called
        double initialX = heavyEnemyProjectile.getLayoutX();
        heavyEnemyProjectile.updatePosition();
        assertEquals(initialX - 7, heavyEnemyProjectile.getLayoutX() + heavyEnemyProjectile.getTranslateX());
    }

    @Test
    void testUpdateActor() {
        // Ensure updateActor calls updatePosition correctly
        double initialX = heavyEnemyProjectile.getLayoutX();
        heavyEnemyProjectile.updateActor();
        assertEquals(initialX - 7, heavyEnemyProjectile.getLayoutX() + heavyEnemyProjectile.getTranslateX());
    }
}