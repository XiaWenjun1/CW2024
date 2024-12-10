package com.example.demo.Actor.Plane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserPlane {

	private UserPlane userPlane;

	@BeforeEach
	void setUp() {
		// Initialize UserPlane with initial health value
		userPlane = new UserPlane(3);  // Example: 3 health points
	}

	@Test
	void testInitialHealth() {
		// Test if the initial health is correctly set
		assertEquals(3, userPlane.getHealth());
	}

	@Test
	void testStopVerticalMovement() {
		// Test stopping the vertical movement
		userPlane.moveDown();
		userPlane.stopVerticalMovement();
		double initialY = userPlane.getLayoutY();
		userPlane.updatePosition();
		assertEquals(initialY, userPlane.getLayoutY());
	}

	@Test
	void testStopHorizontalMovement() {
		// Test stopping the horizontal movement
		userPlane.moveRight();
		userPlane.stopHorizontalMovement();
		double initialX = userPlane.getLayoutX();
		userPlane.updatePosition();
		assertEquals(initialX, userPlane.getLayoutX());
	}

	@Test
	void testFireProjectiles() {
		// Test firing projectiles when the game is not paused
		userPlane.stopVerticalMovement();  // Ensure no movement during test
		assertFalse(userPlane.fireProjectiles().isEmpty());
	}

	@Test
	void testUpgradeProjectile() {
		// Test upgrading the projectile power level
		userPlane.upgradeProjectile();
		assertEquals(2, userPlane.getUserProjectile().getPowerLevel());  // Assuming the initial level is 1
	}

	@Test
	void testKillCount() {
		// Test the kill count functionality
		userPlane.incrementKillCount();
		assertEquals(1, userPlane.getNumberOfKills());
	}
}