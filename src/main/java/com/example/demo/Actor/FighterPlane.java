package com.example.demo.Actor;

import java.util.List;

/**
 * The FighterPlane class represents a fighter plane in the game.
 * It extends {@link ActiveActorDestructible} and includes properties and behaviors related to health and projectile firing.
 * Subclasses of {@link FighterPlane} are expected to implement the {@link #fireProjectiles()} method to specify how the plane fires projectiles.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The health of the player or entity.
	 * This variable stores the current health value. It is used to track the player's or enemy's health in the game.
	 */
	private int health; // The health of the player or entity

	/**
	 * Constructs a new FighterPlane object with the specified image, position, and health.
	 *
	 * @param imageName The name of the image file used to represent the fighter plane.
	 * @param imageWidth The width of the fighter plane image.
	 * @param imageHeight The height of the fighter plane image.
	 * @param initialXPos The initial X position of the fighter plane.
	 * @param initialYPos The initial Y position of the fighter plane.
	 * @param health The initial health value of the fighter plane.
	 */
	public FighterPlane(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires projectiles from the fighter plane.
	 * Subclasses must implement this method to define the behavior of firing projectiles.
	 *
	 * @return A list of {@link ActiveActorDestructible} objects representing the projectiles fired by the plane.
	 */
	public abstract List<ActiveActorDestructible> fireProjectiles();

	/**
	 * Reduces the health of the fighter plane by 1.
	 * If the health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Increases the health of the fighter plane by 1.
	 */
	public void increaseHealth() {
		health++;
	}

	/**
	 * Calculates the X position of the projectile relative to the fighter plane's current position and a specified offset.
	 *
	 * @param xPositionOffset The offset from the fighter plane's X position to determine the projectile's X position.
	 * @return The calculated X position of the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position of the projectile relative to the fighter plane's current position and a specified offset.
	 *
	 * @param yPositionOffset The offset from the fighter plane's Y position to determine the projectile's Y position.
	 * @return The calculated Y position of the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the fighter plane's health has reached zero.
	 *
	 * @return True if the health is zero, false otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return The current health value of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
}