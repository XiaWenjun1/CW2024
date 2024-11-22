package com.example.demo.Object.EnemyPlane;

import com.example.demo.Level.LevelParent;
import com.example.demo.Object.Projectile;

/**
 * The EnemyProjectile class represents a projectile fired by an enemy plane in the game.
 * It extends {@link Projectile} and moves horizontally at a constant speed.
 */
public class EnemyProjectile extends Projectile {

	// Constants for the enemy projectile's properties
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_WIDTH = 50;
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;

	/**
	 * Constructs an EnemyProjectile object with the specified initial position and level.
	 *
	 * @param initialXPos The initial X position of the enemy projectile.
	 * @param initialYPos The initial Y position of the enemy projectile.
	 * @param levelParent The level parent that holds the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);

		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	/**
	 * Updates the position of the enemy projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the enemy projectile, including its position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}
}