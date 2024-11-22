package com.example.demo.Object.UserPlane;

import com.example.demo.Level.LevelParent;
import com.example.demo.Object.Projectile;
import javafx.scene.image.ImageView;

/**
 * The {@code UserProjectile} class represents a projectile fired by the user's plane.
 * It handles the appearance, movement, and properties of the projectile in the game.
 */
public class UserProjectile extends Projectile {

	private int powerLevel = 1;
	private ImageView imageView;
	private int horizontalVelocity;

	private static final ProjectileProperties[] PROPERTIES = {
			new ProjectileProperties(50, 50, 6, 50, 10),       // Power Level 1
			new ProjectileProperties(75, 75, 10, 55, 12),      // Power Level 2
			new ProjectileProperties(100, 100, 14, 60, 14)     // Power Level 3
	};

	/**
	 * Constructs a new {@code UserProjectile} with the specified initial position and properties.
	 *
	 * @param initialXPos  the initial x-coordinate of the projectile
	 * @param initialYPos  the initial y-coordinate of the projectile
	 * @param levelParent  the parent level in which the projectile is created
	 * @param imageWidth   the width of the projectile image
	 * @param imageHeight  the height of the projectile image
	 */
	public UserProjectile(double initialXPos, double initialYPos, LevelParent levelParent, int imageWidth, int imageHeight) {
		super("userfire.png", imageWidth, imageHeight, initialXPos, initialYPos);
		this.imageView = new ImageView();
		this.imageView.setPreserveRatio(true);
		updateProjectileState();
		setHitboxSize(PROPERTIES[powerLevel - 1].hitboxWidth, PROPERTIES[powerLevel - 1].hitboxHeight);
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	/**
	 * Updates the position of the projectile.
	 * Moves the projectile horizontally based on its current velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(horizontalVelocity);
	}

	/**
	 * Updates the projectile's state, including its position and hitbox.
	 * This method is called every frame.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}

	/**
	 * Returns the current power level of the projectile.
	 *
	 * @return the current power level of the projectile
	 */
	public int getPowerLevel() {
		return powerLevel;
	}

	/**
	 * Sets a new power level for the projectile. The power level must be between 1 and 3.
	 *
	 * @param newPowerLevel the new power level to set
	 */
	public void setPowerLevel(int newPowerLevel) {
		if (newPowerLevel >= 1 && newPowerLevel <= 3) {
			this.powerLevel = newPowerLevel;
			updateProjectileState();
		} else {
			System.out.println("Invalid power level: " + newPowerLevel);
		}
	}

	/**
	 * Updates the projectile's state based on its current power level.
	 * This includes updating its velocity, hitbox size, and image size.
	 */
	public void updateProjectileState() {
		ProjectileProperties properties = PROPERTIES[powerLevel - 1];
		setVelocity(properties.velocity);
		setHitboxSize(properties.hitboxWidth, properties.hitboxHeight);
		setImageSize(properties.imageWidth, properties.imageHeight);
	}

	/**
	 * Sets the horizontal velocity of the projectile.
	 *
	 * @param velocity the new horizontal velocity
	 */
	private void setVelocity(int velocity) {
		this.horizontalVelocity = velocity;
	}

	/**
	 * Sets the size of the projectile's image.
	 *
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	private void setImageSize(int width, int height) {
		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
	}

	/**
	 * Returns the width of the projectile's image.
	 *
	 * @return the width of the image
	 */
	public double getWidth() {
		return imageView.getFitWidth();
	}

	/**
	 * Returns the height of the projectile's image.
	 *
	 * @return the height of the image
	 */
	public double getHeight() {
		return imageView.getFitHeight();
	}

	/**
	 * Inner class that represents the properties of a projectile at different power levels.
	 */
	private static class ProjectileProperties {
		final int imageWidth;
		final int imageHeight;
		final int velocity;
		final double hitboxWidth;
		final double hitboxHeight;

		/**
		 * Constructor to initialize the properties for a specific power level.
		 *
		 * @param imageWidth    the width of the projectile image
		 * @param imageHeight   the height of the projectile image
		 * @param velocity      the velocity of the projectile
		 * @param hitboxWidth   the width of the projectile's hitbox
		 * @param hitboxHeight  the height of the projectile's hitbox
		 */
		ProjectileProperties(int imageWidth, int imageHeight, int velocity, double hitboxWidth, double hitboxHeight) {
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			this.velocity = velocity;
			this.hitboxWidth = hitboxWidth;
			this.hitboxHeight = hitboxHeight;
		}
	}

	/**
	 * Moves the projectile horizontally based on its velocity.
	 *
	 * @param velocity the horizontal velocity to move the projectile by
	 */
	private void moveHorizontally(int velocity) {
		setTranslateX(getTranslateX() + velocity);
	}
}