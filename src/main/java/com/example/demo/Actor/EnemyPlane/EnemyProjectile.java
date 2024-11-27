package com.example.demo.Actor.EnemyPlane;

import com.example.demo.Actor.Projectile;

/**
 * The {@code EnemyProjectile} class represents a projectile fired by an enemy plane.
 * It extends {@link Projectile} and provides functionality for updating the position and properties of the enemy projectile.
 */
public class EnemyProjectile extends Projectile {

	/**
	 * The image name of the enemy projectile, used to load its sprite.
	 */
	private static final String IMAGE_NAME = "enemyFire.png";

	/**
	 * The width of the enemy projectile's image.
	 */
	private static final int IMAGE_WIDTH = 50;

	/**
	 * The height of the enemy projectile's image.
	 */
	private static final int IMAGE_HEIGHT = 50;

	/**
	 * The horizontal velocity at which the enemy projectile moves, representing its speed.
	 */
	private static final int HORIZONTAL_VELOCITY = -5;

	/**
	 * Constructor to initialize the {@code EnemyProjectile} with its initial position.
	 *
	 * @param initialXPos The initial X position of the enemy projectile.
	 * @param initialYPos The initial Y position of the enemy projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
	}

	/**
	 * Updates the enemy projectile's position by moving it horizontally at a constant speed.
	 * This method is called every frame to update the projectile's position.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	/**
	 * Updates the enemy projectile's actor state.
	 * This method is called every game tick to update the projectile's position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}