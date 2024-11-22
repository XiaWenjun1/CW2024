package com.example.demo.Object.Boss;

import com.example.demo.Level.LevelParent;
import com.example.demo.Object.Projectile;

/**
 * The BossProjectile class represents a projectile fired by a boss in the game.
 * It extends {@link Projectile} and moves horizontally and vertically at adjustable velocities.
 */
public class BossProjectile extends Projectile {

	// Constants for the boss projectile's properties
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;

	private double horizontalVelocity = -7; // Default horizontal velocity
	private double verticalVelocity = 0; // Default vertical velocity

	/**
	 * Constructs a BossProjectile object with the specified initial position and level.
	 *
	 * @param initialXPos The initial X position of the boss projectile.
	 * @param initialYPos The initial Y position of the boss projectile.
	 * @param levelParent The level parent that holds the projectile.
	 */
	public BossProjectile(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);

		setHitboxSize(IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.3);
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	/**
	 * Sets the horizontal and vertical velocities of the boss projectile.
	 *
	 * @param horizontal The horizontal velocity.
	 * @param vertical The vertical velocity.
	 */
	public void setVelocity(double horizontal, double vertical) {
		this.horizontalVelocity = horizontal;
		this.verticalVelocity = vertical;
	}

	/**
	 * Updates the position of the boss projectile by moving it horizontally and vertically.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
		moveVertically(verticalVelocity);
	}

	/**
	 * Updates the state of the boss projectile, including its position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}
}