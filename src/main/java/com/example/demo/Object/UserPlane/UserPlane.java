package com.example.demo.Object.UserPlane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Object.FighterPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserPlane class represents the player's controlled plane.
 * It extends {@link FighterPlane} and provides functionality for moving the plane,
 * firing projectiles, upgrading projectiles, and handling the plane's interactions in the game.
 */
public class UserPlane extends FighterPlane {

	private final LevelParent levelParent;
	private final UserProjectile userProjectile;
	private boolean isPaused = false;
	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 55;
	private static final double Y_LOWER_BOUND = 700.0;
	private static final double X_LEFT_BOUND = 0;
	private static final double X_RIGHT_BOUND = 1200.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 150;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplierY = 0;
	private int velocityMultiplierX = 0;
	private int numberOfKills;

	/**
	 * Constructor to initialize the UserPlane with its starting position, health, and related parameters.
	 *
	 * @param initialHealth The initial health of the plane.
	 * @param levelParent The parent level containing the plane.
	 */
	public UserPlane(int initialHealth, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
		userProjectile = new UserProjectile(INITIAL_X_POSITION, INITIAL_Y_POSITION, levelParent, 50, 50);
		updateProjectileSize();
		levelParent.getRoot().getChildren().add(getHitbox());
		getHitbox().toFront();
	}

	/**
	 * Upgrades the projectile's power level if it's below the maximum level.
	 */
	public void upgradeProjectile() {
		if (userProjectile.getPowerLevel() < 3) {
			userProjectile.setPowerLevel(userProjectile.getPowerLevel() + 1);
		}
	}

	/**
	 * Updates the plane's position based on its horizontal and vertical velocity.
	 */
	@Override
	public void updatePosition() {
		this.moveHorizontally(HORIZONTAL_VELOCITY * velocityMultiplierX);
		this.moveVertically(VERTICAL_VELOCITY * velocityMultiplierY);
		applyBoundsCheck();
	}

	/**
	 * Applies the boundary checks to ensure the plane does not move out of bounds (keyboard).
	 */
	private void applyBoundsCheck() {
		double newPositionX = getLayoutX() + getTranslateX();
		double newPositionY = getLayoutY() + getTranslateY();

		if (newPositionX < X_LEFT_BOUND) {
			setTranslateX(X_LEFT_BOUND - getLayoutX());
		} else if (newPositionX > X_RIGHT_BOUND) {
			setTranslateX(X_RIGHT_BOUND - getLayoutX());
		}

		if (newPositionY < Y_UPPER_BOUND) {
			setTranslateY(Y_UPPER_BOUND - getLayoutY());
		} else if (newPositionY > Y_LOWER_BOUND) {
			setTranslateY(Y_LOWER_BOUND - getLayoutY());
		}
	}

	/**
	 * Applies the boundary checks to ensure the plane does not move out of bounds (mouse).
	 * Moves the plane by a given delta in the X and Y directions.
	 *
	 * @param deltaX The change in the X position.
	 * @param deltaY The change in the Y position.
	 */
	public void moveUserPlane(double deltaX, double deltaY) {
		double newX = getLayoutX() + getTranslateX() + deltaX;
		double newY = getLayoutY() + getTranslateY() + deltaY;

		if (newX < X_LEFT_BOUND) {
			deltaX = X_LEFT_BOUND - (getLayoutX() + getTranslateX());
		} else if (newX > X_RIGHT_BOUND) {
			deltaX = X_RIGHT_BOUND - (getLayoutX() + getTranslateX());
		}

		if (newY < Y_UPPER_BOUND) {
			deltaY = Y_UPPER_BOUND - (getLayoutY() + getTranslateY());
		} else if (newY > Y_LOWER_BOUND) {
			deltaY = Y_LOWER_BOUND - (getLayoutY() + getTranslateY());
		}

		setTranslateX(getTranslateX() + deltaX);
		setTranslateY(getTranslateY() + deltaY);
	}

	/**
	 * Updates the plane's actor state.
	 * This method is called every game tick to update the plane's position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
		getHitbox().toFront();
	}

	/**
	 * Fires projectiles from the user's plane.
	 *
	 * @return A list of projectiles fired by the user plane.
	 */
	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		if (isPaused) {
			return new ArrayList<>();
		}

		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

		UserProjectile projectile = new UserProjectile(
				projectileXPosition,
				projectileYPosition,
				levelParent,
				(int) userProjectile.getWidth(),
				(int) userProjectile.getHeight()
		);
		projectile.setPowerLevel(userProjectile.getPowerLevel());

		projectiles.add(projectile);
		return projectiles;
	}

	/**
	 * Updates the size of the projectile based on its power level.
	 */
	private void updateProjectileSize() {
		userProjectile.updateProjectileState();
	}

	/**
	 * Moves the plane upwards by setting the vertical velocity multiplier to -1.
	 */
	public void moveUp() {
		velocityMultiplierY = -1;
	}

	/**
	 * Moves the plane to the right by setting the horizontal velocity multiplier to 1.
	 */
	public void moveRight() {
		velocityMultiplierX = 1;
	}

	/**
	 * Moves the plane to the left by setting the horizontal velocity multiplier to -1.
	 */
	public void moveLeft() {
		velocityMultiplierX = -1;
	}

	/**
	 * Moves the plane downwards by setting the vertical velocity multiplier to 1.
	 */
	public void moveDown() {
		velocityMultiplierY = 1;
	}

	/**
	 * Stops the plane's vertical movement by setting the vertical velocity multiplier to 0.
	 */
	public void stopVerticalMovement() {
		velocityMultiplierY = 0;
	}

	/**
	 * Stops the plane's horizontal movement by setting the horizontal velocity multiplier to 0.
	 */
	public void stopHorizontalMovement() {
		velocityMultiplierX = 0;
	}

	/**
	 * Gets the number of kills the user has made.
	 *
	 * @return The number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count by 1.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}
}