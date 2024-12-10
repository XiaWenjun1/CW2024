package com.example.demo.Actor.Projectile;

/**
 * The BossProjectile class represents a projectile fired by a boss in the game.
 * It extends {@link Projectile} and moves horizontally and vertically at adjustable velocities.
 */
public class BossProjectile extends Projectile {
	/**
	 * The image file name for the boss projectile.
	 */
	private static final String IMAGE_NAME = "fireball.png";

	/**
	 * The width of the boss projectile image.
	 */
	private static final int IMAGE_WIDTH = 100;

	/**
	 * The height of the boss projectile image.
	 */
	private static final int IMAGE_HEIGHT = 100;

	/**
	 * The horizontal velocity of the boss projectile.
	 * The default value is -7, which moves the projectile to the left.
	 */
	private double horizontalVelocity = -7;

	/**
	 * The vertical velocity of the boss projectile.
	 * The default value is 0, which means no vertical movement.
	 */
	private double verticalVelocity = 0;

	/**
	 * Constructs a BossProjectile object with the specified initial position and level.
	 *
	 * @param initialXPos The initial X position of the boss projectile.
	 * @param initialYPos The initial Y position of the boss projectile.
	 */
	public BossProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
		setHitboxSize(IMAGE_WIDTH * 0.5, IMAGE_HEIGHT * 0.3);
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