package com.example.demo.Actor.Boss.ParentBoss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestBossProjectile {

	private BossProjectile bossProjectile;

	@BeforeEach
	void setUp() {
		bossProjectile = new BossProjectile(100, 200);
	}

	@Test
	void testConstructor() {
		assertNotNull(bossProjectile);
		assertEquals(100, bossProjectile.getLayoutX());
		assertEquals(200, bossProjectile.getLayoutY());
	}

	@Test
	void testSetVelocity() {
		bossProjectile.setVelocity(-5, 3);

		bossProjectile.updatePosition();
		assertEquals(95, bossProjectile.getLayoutX());
		assertEquals(203, bossProjectile.getLayoutY());
	}

	@Test
	void testUpdatePosition() {
		bossProjectile.setVelocity(-5, 3);

		double initialX = bossProjectile.getLayoutX();
		double initialY = bossProjectile.getLayoutY();

		bossProjectile.updatePosition();

		assertEquals(initialX - 5, bossProjectile.getLayoutX());
		assertEquals(initialY + 3, bossProjectile.getLayoutY());
	}

	@Test
	void testUpdateActor() {
		bossProjectile.setVelocity(-7, 2);

		double initialX = bossProjectile.getLayoutX();
		double initialY = bossProjectile.getLayoutY();

		bossProjectile.updateActor();

		assertEquals(initialX - 7, bossProjectile.getLayoutX());
		assertEquals(initialY + 2, bossProjectile.getLayoutY());
		assertNotNull(bossProjectile.getHitbox());
		assertEquals(initialX - 7, bossProjectile.getHitbox().getX());
		assertEquals(initialY + 2, bossProjectile.getHitbox().getY());
	}
}