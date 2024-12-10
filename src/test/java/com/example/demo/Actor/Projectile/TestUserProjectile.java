package com.example.demo.Actor.Projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestUserProjectile {

	private UserProjectile userProjectile;

	@BeforeEach
	void setUp() {
		// Initialize UserProjectile, assuming the initial position is x=0, y=0, and the image size is 50x50
		userProjectile = new UserProjectile(0, 0, 50, 50);
	}

	@Test
	void testInitialPowerLevel() {
		// Test if the initial powerLevel is 1
		assertEquals(1, userProjectile.getPowerLevel());
	}

	@Test
	void testSetPowerLevel() {
		// Test setting powerLevel
		userProjectile.setPowerLevel(3);
		assertEquals(3, userProjectile.getPowerLevel());

		// Set invalid powerLevel
		userProjectile.setPowerLevel(6);
		assertEquals(3, userProjectile.getPowerLevel()); // Should still remain at 3

		userProjectile.setPowerLevel(2);
		assertEquals(2, userProjectile.getPowerLevel());
	}

	@Test
	void testUpdateProjectileState() {
		// Test update projectile status
		userProjectile.setPowerLevel(4);
		userProjectile.updateProjectileState();

		// Verify updated properties
		assertEquals(165, userProjectile.getWidth()); //According to the properties of power level 4
		assertEquals(165, userProjectile.getHeight()); //According to the properties of power level 4
		assertEquals(18, userProjectile.getVelocity()); //According to the properties of power level 4
	}

	@Test
	void testUpdatePosition() {
		// Test update position
		userProjectile.setPowerLevel(2);
		userProjectile.updateProjectileState();
		double initialPosition = userProjectile.getTranslateX();
		userProjectile.updatePosition();
		assertNotEquals(initialPosition, userProjectile.getTranslateX());
	}
}