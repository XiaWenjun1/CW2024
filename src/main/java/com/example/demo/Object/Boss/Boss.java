package com.example.demo.Object.Boss;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Object.FighterPlane;

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
	private static final int IMAGE_WIDTH = 300; // Width of the boss image
	private static final int IMAGE_HEIGHT = 300; // Height of the boss image
	private static final int HEALTH = 30; // Initial health of the boss
	private static final int Y_UPPER_BOUND = 55; // Upper bound for Y position
	private static final int Y_LOWER_BOUND = 700; // Lower bound for Y position

	private final LevelParent levelParent; // The level parent object managing the game scene
	private final BossMovePattern movePattern; // The boss's movement pattern manager
	private final BossShield bossShield; // The boss's shield
	private final BossHealthBar bossHealthBar; // The boss's health bar
	private final BossFirePattern firePattern; // The boss's fire pattern manager

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

		this.movePattern = new BossMovePattern(); // Initialize movement pattern
		this.bossHealthBar = BossHealthBar.initialize(this, levelParent); // Initialize health bar
		this.bossShield = BossShield.initialize(this, levelParent); // Initialize shield
	}

	/**
	 * Updates the boss's state, including position, shield, health bar, and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition(); // Update position based on movement pattern
		bossShield.update(); // Update shield status
		bossHealthBar.update(); // Update health bar
		updateHitbox(); // Update hitbox for collision detection
	}

	/**
	 * Updates the boss's position based on its movement pattern.
	 * Ensures the boss stays within vertical bounds.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(movePattern.getNextMove()); // Move the boss vertically
		double currentPositionY = getLayoutY() + getTranslateY();
		if (currentPositionY < Y_UPPER_BOUND || currentPositionY > Y_LOWER_BOUND) {
			setTranslateY(initialTranslateY); // Ensure the boss stays within the vertical bounds
		}
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
			case 1 -> firePattern.createStraightProjectile(); // Fire straight projectiles
			case 2 -> firePattern.createScatterProjectiles(); // Fire scatter projectiles
			case 3 -> firePattern.createDirectionalProjectiles(); // Fire directional projectiles
			default -> new ArrayList<>();
		};
	}

	/**
	 * Reduces the boss's health by 1 if not shielded and updates the health bar.
	 */
	@Override
	public void takeDamage() {
		if (!bossShield.isShielded()) {
			super.takeDamage(); // Call parent class takeDamage if the shield is not active
		}

		if (getHealth() <= 0) {
			die(); // Trigger boss death if health reaches 0 or below
		}
	}

	/**
	 * Determines if the boss fires a projectile in the current frame based on the fire rate.
	 *
	 * @return True if the boss fires a projectile, false otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE; // Randomly decide whether to fire based on the fire rate
	}

	/**
	 * Checks if the boss collides with another actor.
	 *
	 * @param otherActor The other actor to check for collision.
	 * @return True if there is a collision, false otherwise.
	 */
	public boolean checkCollision(ActiveActor otherActor) {
		return getHitbox().getBoundsInParent().intersects(otherActor.getHitbox().getBoundsInParent()); // Check if hitboxes overlap
	}

	/**
	 * Gets the X position of the boss, considering any translations applied.
	 *
	 * @return The X position of the boss.
	 */
	public double getBossXPosition() {
		return getLayoutX() + getTranslateX(); // Get the X position with translation applied
	}

	/**
	 * Gets the Y position of the boss, considering any translations applied.
	 *
	 * @return The Y position of the boss.
	 */
	public double getBossYPosition() {
		return getLayoutY() + getTranslateY(); // Get the Y position with translation applied
	}

	/**
	 * Gets the width of the boss.
	 *
	 * @return The width of the boss image.
	 */
	public double getBossWidth() {
		return IMAGE_WIDTH;
	}

	/**
	 * Gets the maximum health of the boss.
	 *
	 * @return The maximum health of the boss.
	 */
	public int getMAXHealth() {
		return HEALTH;
	}

	/**
	 * Executes the death behavior of the boss, hiding the health bar and printing a message.
	 */
	private void die() {
		System.out.println("Boss is dead!");
		bossHealthBar.hide(); // Hide the health bar when the boss dies
	}
}