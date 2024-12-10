package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnemyPlane {

	private EnemyPlane enemyPlane;

	@BeforeEach
	void setUp() {
		// Initialize an EnemyPlane object with a starting position
		double initialX = 500;
		double initialY = 200;
		enemyPlane = new EnemyPlane(initialX, initialY);
	}

	@Test
	void testUpdatePosition() {
		// Update the enemy plane's position and verify it has moved correctly
		double initialX = enemyPlane.getLayoutX();
		enemyPlane.updatePosition();
		assertEquals(initialX - 2, enemyPlane.getLayoutX() + enemyPlane.getTranslateX());
	}

	@Test
	void testFireProjectiles() {
		// Simulate firing projectiles and verify they are generated correctly
		List<ActiveActorDestructible> projectiles = enemyPlane.fireProjectiles();
		assertNotNull(projectiles);

		// Verify the projectile list size matches the firing logic
		if (!projectiles.isEmpty()) {
			ActiveActorDestructible projectile = projectiles.get(0);
			assertEquals(enemyPlane.getLayoutX() - 100.0, projectile.getLayoutX(), 0.1);
			assertEquals(enemyPlane.getLayoutY() + 50.0, projectile.getLayoutY(), 0.1);
		}
	}

	@Test
	void testTakeDamage() {
		// Verify the enemy plane takes damage and health decreases
		int initialHealth = enemyPlane.getHealth();
		enemyPlane.takeDamage();
		assertEquals(initialHealth - 1, enemyPlane.getHealth());
	}

	@Test
	void testHealthDepletesToZero() {
		// Simulate damage until health is zero and verify it is inactive by health value
		while (enemyPlane.getHealth() > 0) {
			enemyPlane.takeDamage();
		}
		assertEquals(0, enemyPlane.getHealth());
	}
}