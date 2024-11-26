package com.example.demo.Actor.UserPlane;

import com.example.demo.Actor.Projectile;
import javafx.scene.image.ImageView;

/**
 * The {@code UserProjectile} class represents a projectile fired by the user's plane.
 * It handles the appearance, movement, and properties of the projectile in the game.
 */
public class UserProjectile extends Projectile {

	private int powerLevel = 1;
	private ImageView imageView;
	private int horizontalVelocity;

	// Array of properties for projectiles at different power levels
	private static final ProjectileProperties[] PROPERTIES = {
			new ProjectileProperties(50, 50, 6, 50, 10),       // Power Level 1
			new ProjectileProperties(75, 75, 10, 55, 14),      // Power Level 2
			new ProjectileProperties(100, 100, 14, 60, 18),    // Power Level 3
			new ProjectileProperties(125, 125, 18, 65, 22),    // Power Level 4
			new ProjectileProperties(150, 155, 22, 70, 26)     // Power Level 5
	};

	/**
	 * Constructs a new {@code UserProjectile} with the specified initial position and properties.
	 *
	 * @param initialXPos  the initial x-coordinate of the projectile
	 * @param initialYPos  the initial y-coordinate of the projectile
	 * @param imageWidth   the width of the projectile image
	 * @param imageHeight  the height of the projectile image
	 */
	public UserProjectile(double initialXPos, double initialYPos, int imageWidth, int imageHeight) {
		super("userfire_level1.png", imageWidth, imageHeight, initialXPos, initialYPos);
		this.imageView = new ImageView();
		this.imageView.setPreserveRatio(true);
		updateProjectileState();
		setHitboxSize(PROPERTIES[powerLevel - 1].hitboxWidth, PROPERTIES[powerLevel - 1].hitboxHeight);
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
	 * Sets a new power level for the projectile. The power level must be between 1 and 5.
	 *
	 * @param newPowerLevel the new power level to set
	 */
	public void setPowerLevel(int newPowerLevel) {
		if (newPowerLevel >= 1 && newPowerLevel <= 5) {
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

		// Set the image based on the power level
		switch (powerLevel) {
			case 1:
				setImage("userfire_level1.png");
				break;
			case 2:
				setImage("userfire_level2.png");
				break;
			case 3:
				setImage("userfire_level3.png");
				break;
			case 4:
				setImage("userfire_level4.png");
				break;
			case 5:
				setImage("userfire_level5.png");
				break;
			default:
				System.out.println("Invalid power level: " + powerLevel);
		}
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