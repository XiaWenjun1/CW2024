package com.example.demo.Actor.Projectile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBossProjectile {

	private BossProjectile bossProjectile;

	@BeforeEach
	void setUp() {
		bossProjectile = new BossProjectile(100, 200);
	}

	@Test
	void testSetVelocity() {
		bossProjectile.setVelocity(-5, 3);

		bossProjectile.updatePosition();
		assertEquals(95, bossProjectile.getLayoutX() + bossProjectile.getTranslateX());
		assertEquals(203, bossProjectile.getLayoutY() + bossProjectile.getTranslateY());
	}

	@Test
	void testUpdatePosition() {
		bossProjectile.setVelocity(-5, 3);

		double initialX = bossProjectile.getLayoutX();
		double initialY = bossProjectile.getLayoutY();

		bossProjectile.updatePosition();

		assertEquals(initialX - 5, bossProjectile.getLayoutX() + bossProjectile.getTranslateX());
		assertEquals(initialY + 3, bossProjectile.getLayoutY() + bossProjectile.getTranslateY());
	}

	@Test
	void testUpdateActor() {
		bossProjectile.setVelocity(-7, 2);

		double initialX = bossProjectile.getLayoutX();
		double initialY = bossProjectile.getLayoutY();

		bossProjectile.updateActor();

		assertEquals(initialX - 7, bossProjectile.getLayoutX() + bossProjectile.getTranslateX());
		assertEquals(initialY + 2, bossProjectile.getLayoutY() + bossProjectile.getTranslateY());
		assertNotNull(bossProjectile.getHitbox());
		assertEquals(initialX - 7, bossProjectile.getHitbox().getX());
		assertEquals(initialY + 2, bossProjectile.getHitbox().getY());
	}
}