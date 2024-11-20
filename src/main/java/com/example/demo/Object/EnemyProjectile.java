package com.example.demo.Object;

import com.example.demo.Level.LevelParent;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_WIDTH = 50;
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;

	public EnemyProjectile(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);

		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);

		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}


}
