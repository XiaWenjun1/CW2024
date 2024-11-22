package com.example.demo.Object;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The Projectile class represents a projectile in the game.
 * It extends {@link ActiveActorDestructible} and includes the behavior of taking damage and updating the position of the projectile.
 * Subclasses must implement the {@link #updatePosition()} method to define how the projectile moves.
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a new Projectile object with the specified image, position, and size.
	 *
	 * @param imageName The name of the image file used to represent the projectile.
	 * @param imageWidth The width of the projectile image.
	 * @param imageHeight The height of the projectile image.
	 * @param initialXPos The initial X position of the projectile.
	 * @param initialYPos The initial Y position of the projectile.
	 */
	public Projectile(String imageName, double imageWidth, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageWidth, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the behavior of the projectile when it takes damage.
	 * When a projectile takes damage, it is destroyed.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * Subclasses must implement this method to define how the projectile's position is updated.
	 */
	@Override
	public abstract void updatePosition();
}