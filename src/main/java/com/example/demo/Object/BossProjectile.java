package com.example.demo.Object;

import com.example.demo.Level.LevelParent;

public class BossProjectile extends Projectile {
	private LevelParent levelParent;
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;

	private double horizontalVelocity = -4;
	private double verticalVelocity = 0;

	public BossProjectile(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);

		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.3);

		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	public void setVelocity(double horizontal, double vertical) {
		this.horizontalVelocity = horizontal;
		this.verticalVelocity = vertical;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
		moveVertically(verticalVelocity);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}
}
