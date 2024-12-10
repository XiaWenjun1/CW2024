package com.example.demo.Actor.Projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnemyProjectile {

	private EnemyProjectile enemyProjectile;

	@BeforeEach
	void setUp() {
		// Initialize the EnemyProjectile with a starting position
		double initialX = 500;
		double initialY = 300;
		enemyProjectile = new EnemyProjectile(initialX, initialY);
	}

	@Test
	void testUpdatePosition() {
		// Update the projectile's position and verify it has moved correctly
		double initialX = enemyProjectile.getLayoutX();
		enemyProjectile.updatePosition();
		assertEquals(initialX - 5, enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX());
	}

	@Test
	void testHitboxInitialization() {
		// Verify that the hitbox is initialized correctly
		assertNotNull(enemyProjectile.getHitbox());
		assertEquals(50, enemyProjectile.getHitbox().getWidth());
		assertEquals(15, enemyProjectile.getHitbox().getHeight(), 0.1); // 50 * 0.3
	}

	@Test
	void testUpdateActor() {
		// Ensure updateActor correctly updates position
		double initialX = enemyProjectile.getLayoutX();
		enemyProjectile.updateActor();
		assertEquals(initialX - 5, enemyProjectile.getLayoutX() + enemyProjectile.getTranslateX());
	}
}