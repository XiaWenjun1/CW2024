package com.example.demo.Actor.Plane.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectile.BossProjectile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBossFirePattern {

    private Boss boss;
    private BossFirePattern bossFirePattern;

    @BeforeEach
    void setUp() {
        // Create the boss with a health value of 100
        boss = new Boss(100);

        // Set the boss's position explicitly to match the expected positions in tests
        boss.setLayoutX(1000.0);
        boss.setLayoutY(430.0);

        // Initialize the BossFirePattern with the boss object
        bossFirePattern = new BossFirePattern(boss);
    }

    @Test
    void testCreateStraightProjectile() {
        List<ActiveActorDestructible> projectiles = bossFirePattern.createStraightProjectile();

        assertEquals(1, projectiles.size(), "Straight projectile list should contain one projectile.");
        BossProjectile projectile = (BossProjectile) projectiles.get(0);

        assertEquals(1000.0, projectile.getLayoutX(), 0.01, "Projectile X position is incorrect.");
        assertEquals(460.0, projectile.getLayoutY(), 0.01, "Projectile Y position is incorrect.");
    }

    @Test
    void testCreateTwoProjectiles() {
        List<ActiveActorDestructible> projectiles = bossFirePattern.createTwoProjectiles();

        assertEquals(2, projectiles.size(), "Two projectiles should be created.");

        BossProjectile projectile1 = (BossProjectile) projectiles.get(0);
        BossProjectile projectile2 = (BossProjectile) projectiles.get(1);

        assertEquals(1000.0, projectile1.getLayoutX(), 0.01, "Projectile 1 X position is incorrect.");
        assertEquals(380.0, projectile1.getLayoutY(), 0.01, "Projectile 1 Y position is incorrect.");

        assertEquals(1000.0, projectile2.getLayoutX(), 0.01, "Projectile 2 X position is incorrect.");
        assertEquals(480.0, projectile2.getLayoutY(), 0.01, "Projectile 2 Y position is incorrect.");
    }

    @Test
    void testCreateScatterProjectiles() {
        List<ActiveActorDestructible> projectiles = bossFirePattern.createScatterProjectiles();

        assertEquals(3, projectiles.size(), "Scatter projectile list should contain three projectiles.");

        BossProjectile projectile1 = (BossProjectile) projectiles.get(0);
        BossProjectile projectile2 = (BossProjectile) projectiles.get(1);
        BossProjectile projectile3 = (BossProjectile) projectiles.get(2);

        assertEquals(1000.0, projectile1.getLayoutX(), 0.01, "Projectile 1 X position is incorrect.");
        assertEquals(380.0, projectile1.getLayoutY(), 0.01, "Projectile 1 Y position is incorrect.");

        assertEquals(1000.0, projectile2.getLayoutX(), 0.01, "Projectile 2 X position is incorrect.");
        assertEquals(430.0, projectile2.getLayoutY(), 0.01, "Projectile 2 Y position is incorrect.");

        assertEquals(1000.0, projectile3.getLayoutX(), 0.01, "Projectile 3 X position is incorrect.");
        assertEquals(480.0, projectile3.getLayoutY(), 0.01, "Projectile 3 Y position is incorrect.");
    }

    @Test
    void testCreateDirectionalProjectiles() {
        List<ActiveActorDestructible> projectiles = bossFirePattern.createDirectionalProjectiles();

        assertEquals(3, projectiles.size(), "Directional projectile list should contain three projectiles.");

        BossProjectile straightProjectile = (BossProjectile) projectiles.get(0);
        BossProjectile leftUpProjectile = (BossProjectile) projectiles.get(1);
        BossProjectile leftDownProjectile = (BossProjectile) projectiles.get(2);

        assertEquals(1000.0, straightProjectile.getLayoutX(), 0.01, "Straight projectile X position is incorrect.");
        assertEquals(460.0, straightProjectile.getLayoutY(), 0.01, "Straight projectile Y position is incorrect.");

        assertEquals(1000.0, leftUpProjectile.getLayoutX(), 0.01, "Left-up projectile X position is incorrect.");
        assertEquals(410.0, leftUpProjectile.getLayoutY(), 0.01, "Left-up projectile Y position is incorrect.");

        assertEquals(1000.0, leftDownProjectile.getLayoutX(), 0.01, "Left-down projectile X position is incorrect.");
        assertEquals(510.0, leftDownProjectile.getLayoutY(), 0.01, "Left-down projectile Y position is incorrect.");
    }
}