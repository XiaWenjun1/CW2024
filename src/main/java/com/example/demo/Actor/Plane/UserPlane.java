package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectile.UserProjectile;
import com.example.demo.Level.LevelManager.AudioManager;
import javafx.animation.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserPlane} class represents the player's controlled plane.
 * It extends {@link FighterPlane} and provides functionality for moving the plane,
 * firing projectiles, upgrading projectiles, and handling the plane's interactions in the game.
 */
public class UserPlane extends FighterPlane {

	/**
	 * The user's projectile object, which is used to manage and fire projectiles.
	 */
	private final UserProjectile userProjectile;

	/**
	 * Flag to determine if the game is paused. If true, certain actions like firing projectiles are disabled.
	 */
	private boolean isPaused = false;

	/**
	 * The image name for the user plane.
	 */
	private static final String IMAGE_NAME = "userplane.png";

	/**
	 * The upper bound (Y-coordinate) for the plane's movement. The plane cannot move above this Y position.
	 */
	private static final double Y_UPPER_BOUND = 55;

	/**
	 * The lower bound (Y-coordinate) for the plane's movement. The plane cannot move below this Y position.
	 */
	private static final double Y_LOWER_BOUND = 720.0;

	/**
	 * The leftmost bound (X-coordinate) for the plane's movement. The plane cannot move beyond this X position.
	 */
	private static final double X_LEFT_BOUND = 0;

	/**
	 * The rightmost bound (X-coordinate) for the plane's movement. The plane cannot move beyond this X position.
	 */
	private static final double X_RIGHT_BOUND = 700.0;

	/**
	 * The initial X position of the user plane when it is created.
	 */
	private static final double INITIAL_X_POSITION = 5.0;

	/**
	 * The initial Y position of the user plane when it is created.
	 */
	private static final double INITIAL_Y_POSITION = 300.0;

	/**
	 * The width of the user plane's image.
	 */
	private static final int IMAGE_WIDTH = 125;

	/**
	 * The height of the user plane's image.
	 */
	private static final int IMAGE_HEIGHT = 125;

	/**
	 * The vertical speed (velocity) of the user plane.
	 */
	private static final int VERTICAL_VELOCITY = 7;

	/**
	 * The horizontal speed (velocity) of the user plane.
	 */
	private static final int HORIZONTAL_VELOCITY = 7;

	/**
	 * The X offset for the projectile's starting position relative to the plane.
	 */
	private static final int PROJECTILE_X_POSITION_OFFSET = 150;

	/**
	 * The Y offset for the projectile's starting position relative to the plane.
	 */
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	/**
	 * A multiplier for the vertical velocity, used to control the movement speed.
	 */
	private int velocityMultiplierY = 0;

	/**
	 * A multiplier for the horizontal velocity, used to control the movement speed.
	 */
	private int velocityMultiplierX = 0;

	/**
	 * The total number of kills the user has made.
	 */
	private int numberOfKills;

	/**
	 * Invincibility flag
	 */
	private boolean isInvincible = false;

	/**
	 * Indicates if an animation is in progress.
	 */
	private boolean isAnimating = false;

	/**
	 * Timer for invincibility duration
	 */
	private final Timeline invincibilityTimer;

	/**
	 * Constructor to initialize the {@code UserPlane} with its starting position, health, and related parameters.
	 *
	 * @param initialHealth The initial health of the plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.3);
		userProjectile = new UserProjectile(INITIAL_X_POSITION, INITIAL_Y_POSITION, 50, 50);
		updateProjectileSize();
		invincibilityTimer = initializeInvincibilityTimer();
	}

	/**
	 * Initializes the invincibility timer which triggers the end of invincibility after a duration.
	 *
	 * @return The initialized Timeline object for invincibility timer.
	 */
	private Timeline initializeInvincibilityTimer() {
		Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> endInvincibility()));
		timer.setCycleCount(1); // Only run once
		return timer;
	}

	/**
	 * Activates invincibility, starting the flashing effect and timer.
	 */
	public void triggerInvincibility() {
		if (isInvincible) return; // Already invincible
		isInvincible = true;
		startFlashing();
		invincibilityTimer.playFromStart();
	}

	/**
	 * Ends invincibility and stops the flashing effect.
	 */
	private void endInvincibility() {
		isInvincible = false;
		stopFlashing();
	}

	/**
	 * Starts the flashing effect of the plane (blinking).
	 */
	private void startFlashing() {
		Timeline flashingTimer = new Timeline(
				new KeyFrame(Duration.seconds(0.1), e -> setOpacity(getOpacity() == 1 ? 0.5 : 1))
		);
		flashingTimer.setCycleCount(Timeline.INDEFINITE);
		flashingTimer.play();

		// Stop flashing when invincibility ends
		invincibilityTimer.setOnFinished(e -> {
			flashingTimer.stop();
			setOpacity(1); // Restore normal opacity
		});
	}

	/**
	 * Stops the flashing effect.
	 */
	private void stopFlashing() {
		setOpacity(1); // Restore normal opacity
	}

	/**
	 * Handles damage to the user plane, ignoring damage if invincible.
	 */
	@Override
	public void takeDamage() {
		if (isInvincible) return; // Ignore damage when invincible
		super.takeDamage();
		AudioManager.getInstance().triggerUserDamageAudio();
		triggerInvincibility(); // Activate invincibility after damage
	}

	/**
	 * Upgrades the projectile's power level if it's below the maximum level.
	 */
	public void upgradeProjectile() {
		if (userProjectile.getPowerLevel() < 5) {
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
				(int) userProjectile.getWidth(),
				(int) userProjectile.getHeight()
		);
		projectile.setPowerLevel(userProjectile.getPowerLevel());

		projectiles.add(projectile);
		return projectiles;
	}

	/**
	 * Animates the player's entry into the scene using a spiral portal effect.
	 * The animation includes rotation, scaling, and fading in.
	 */
	public void spiralPortalEnter() {
		setOpacity(0);
		setScaleX(0.1);
		setScaleY(0.1);
		setLayoutX(INITIAL_X_POSITION);
		setLayoutY(INITIAL_Y_POSITION);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), this);
		rotateTransition.setByAngle(720);

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), this);
		scaleTransition.setToX(1);
		scaleTransition.setToY(1);

		FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(2), this);
		fadeInTransition.setToValue(1);

		ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, scaleTransition, fadeInTransition);

		parallelTransition.setOnFinished(event -> {
			setOpacity(1);
		});

		parallelTransition.play();
	}

	/**
	 * Animates the player's exit from the scene by moving it to the right and fading out.
	 * The animation includes rotation, scaling, and fading out.
	 */
	public void spiralPortalExit() {
		if (isAnimating) {
			return;
		}
		isAnimating = true;

		setOpacity(1);
		setScaleX(1);
		setScaleY(1);

		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), this);
		rotateTransition.setByAngle(1440);

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), this);
		scaleTransition.setToX(0.1);
		scaleTransition.setToY(0.1);

		FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(2), this);
		fadeOutTransition.setToValue(0);

		ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, scaleTransition, fadeOutTransition);

		parallelTransition.setOnFinished(event -> {
			isAnimating = false;
		});

		parallelTransition.play();
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

	/**
	 * Retrieves the {@code UserProjectile} instance associated with this user plane.
	 * This method provides access to the user's projectile object, which manages the state and behavior of the projectiles fired by the plane.
	 *
	 * @return The {@code UserProjectile} instance representing the projectiles fired by this plane.
	 */
	public UserProjectile getUserProjectile() {
		return userProjectile;
	}

	/**
	 * Retrieves the current power level of the user's projectiles.
	 *
	 * @return The power level of the user's projectiles.
	 */
	public int getCurrentProjectilePowerLevel() { return userProjectile.getPowerLevel(); }
}