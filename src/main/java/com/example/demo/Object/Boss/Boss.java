package com.example.demo.Object.Boss;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Display.ShieldImage;
import com.example.demo.Object.FighterPlane;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.*;

/**
 * The Boss class represents a boss enemy in the game, extending the FighterPlane class.
 * It manages the boss's health, movement patterns, projectile firing, and shield mechanics.
 * The boss can move vertically, fire projectiles in different patterns, and activate a shield randomly.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png"; // Image name for the boss
	private static final double INITIAL_X_POSITION = 1000.0; // Initial X position of the boss
	private static final double INITIAL_Y_POSITION = 400.0; // Initial Y position of the boss
	private static final double BOSS_FIRE_RATE = 0.015; // Probability of firing a projectile in each frame
	private static final double BOSS_SHIELD_PROBABILITY = 0.001; // Probability of activating the shield
	private static final int IMAGE_WIDTH = 300; // Width of the boss image
	private static final int IMAGE_HEIGHT = 300; // Height of the boss image
	private static final int VERTICAL_VELOCITY = 5; // Vertical velocity for the boss movement
	private static final int HEALTH = 50; // Initial health of the boss
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10; // Maximum frames moving in the same direction
	private static final int Y_UPPER_BOUND = 55; // Upper bound for Y position
	private static final int Y_LOWER_BOUND = 700; // Lower bound for Y position
	private static final int MAX_FRAMES_WITH_SHIELD = 500; // Maximum frames with the shield active
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5; // Frequency of movement changes in a cycle
	private static final int ZERO = 0; // Constant for zero movement

	private final LevelParent levelParent; // The level parent object managing the game scene
	private final List<Integer> movePattern = new ArrayList<>(); // List defining the movement pattern
	private final ShieldImage shieldImage; // Shield image displayed when the boss is shielded
	private final ProgressBar healthBar; // Progress bar displaying the boss's health
	private final BossFirePattern firePattern; // The boss's fire pattern manager

	private boolean isShielded; // Whether the boss is currently shielded
	private int consecutiveMovesInSameDirection; // Counter for consecutive moves in the same direction
	private int indexOfCurrentMove; // Index for the current move in the move pattern
	private int framesWithShieldActivated; // Counter for frames the shield has been activated

	/**
	 * Constructs a Boss object with the specified levelParent.
	 *
	 * @param levelParent The level parent that manages the game scene and root elements.
	 */
	public Boss(LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.levelParent = levelParent;
		this.firePattern = new BossFirePattern(levelParent, this);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.2);

		initializeMovePattern();
		shieldImage = initializeShieldImage();
		healthBar = initializeHealthBar();
	}

	/**
	 * Initializes the movement pattern for the boss.
	 * The pattern contains alternating vertical movements with some stationary moves.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Initializes the shield image for the boss.
	 *
	 * @return The ShieldImage object for the boss.
	 */
	private ShieldImage initializeShieldImage() {
		ShieldImage shield = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		if (levelParent != null && levelParent.getRoot() != null) {
			levelParent.getRoot().getChildren().add(shield);
		}
		return shield;
	}

	/**
	 * Initializes the health bar for the boss.
	 *
	 * @return The ProgressBar object for the boss's health.
	 */
	private ProgressBar initializeHealthBar() {
		ProgressBar healthBar = createHealthBar();
		Platform.runLater(() -> {
			if (levelParent != null && levelParent.getRoot() != null) {
				levelParent.getRoot().getChildren().add(healthBar);
			}
		});
		return healthBar;
	}

	/**
	 * Updates the boss's state, including position, shield, health bar, and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
		updateHealthBar();
		updateHitbox();
	}

	/**
	 * Updates the boss's position based on its movement pattern.
	 * Ensures the boss stays within vertical bounds.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_UPPER_BOUND || currentPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the boss's shield state, showing or hiding the shield as needed.
	 */
	private void updateShield() {
		double currentPosition = getLayoutY() + getTranslateY();
		shieldImage.setLayoutX(getLayoutX());
		shieldImage.setLayoutY(currentPosition);

		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
		} else if (shieldShouldBeActivated()) {
			activateShield();
		}

		if (shieldExhausted()) {
			deactivateShield();
		}

		shieldImage.toFront();
	}

	/**
	 * Updates the health bar to reflect the current health percentage of the boss.
	 */
	private void updateHealthBar() {
		double currentPositionY = getLayoutY() + getTranslateY();
		healthBar.setLayoutX(getLayoutX());
		healthBar.setLayoutY(currentPositionY + 60);
		healthBar.setProgress(Math.max(0, (double) getHealth() / HEALTH));
	}

	/**
	 * Fires projectiles based on the boss's current attack type.
	 *
	 * @return A list of projectiles fired by the boss.
	 */
	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		if (!bossFiresInCurrentFrame()) {
			return new ArrayList<>();
		}

		int attackType = firePattern.selectAttackType();
		return switch (attackType) {
			case 1 -> firePattern.createStraightProjectile();
			case 2 -> firePattern.createScatterProjectiles();
			case 3 -> firePattern.createDirectionalProjectiles();
			default -> new ArrayList<>();
		};
	}

	/**
	 * Reduces the boss's health by 1 if not shielded and updates the health bar.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}

		double healthPercentage = Math.max(0, (double) getHealth() / HEALTH);
		healthBar.setProgress(healthPercentage);

		if (getHealth() <= 0) {
			die();
		}
	}

	/**
	 * Creates the health bar for the boss.
	 *
	 * @return A ProgressBar object for the boss's health.
	 */
	private ProgressBar createHealthBar() {
		ProgressBar bar = new ProgressBar(1.0);
		bar.setPrefWidth(IMAGE_WIDTH);
		bar.setPrefHeight(10);
		bar.setStyle("-fx-accent: red; -fx-background-color: lightgray;");
		return bar;
	}

	/**
	 * Determines if the boss fires a projectile in the current frame.
	 *
	 * @return True if the boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Determines if the shield should be activated.
	 *
	 * @return True if the shield should be activated, false otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Determines if the shield has been activated for too long.
	 *
	 * @return True if the shield is exhausted, false otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss's shield.
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();
	}

	/**
	 * Deactivates the boss's shield.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();
	}

	/**
	 * Hides the health bar for the boss.
	 */
	public void hideHealthBar() {
		if (healthBar != null) {
			healthBar.setVisible(false);
		}
	}

	/**
	 * Gets the next move in the movement pattern for the boss.
	 *
	 * @return The next move for the boss in the pattern.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Checks if the boss collides with another actor.
	 *
	 * @param otherActor The other actor to check for collision.
	 * @return True if there is a collision, false otherwise.
	 */
	public boolean checkCollision(ActiveActor otherActor) {
		return getHitbox().getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent());
	}

	/**
	 * Gets the health bar of the boss.
	 *
	 * @return The health bar of the boss.
	 */
	public ProgressBar getHealthBar() {
		return healthBar;
	}

	/**
	 * Gets the shield image of the boss.
	 *
	 * @return The shield image of the boss.
	 */
	public ImageView getShieldImage() {
		return shieldImage;
	}

	/**
	 * Executes the death behavior of the boss.
	 */
	private void die() {
		System.out.println("Boss is dead!");
	}
}