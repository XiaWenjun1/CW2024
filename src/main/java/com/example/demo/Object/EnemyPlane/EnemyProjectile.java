package com.example.demo.Object.EnemyPlane;

import com.example.demo.Object.Projectile;

public class EnemyProjectile extends Projectile {

	// Constants for the enemy projectile's properties
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_WIDTH = 50;
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	@Override
	public void updateActor() {
		updatePosition();
	}
}