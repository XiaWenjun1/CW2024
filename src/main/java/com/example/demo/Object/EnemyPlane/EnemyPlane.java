package com.example.demo.Object.EnemyPlane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Level.LevelParent;
import com.example.demo.Object.FighterPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * The EnemyPlane class represents an enemy plane in the game.
 * It extends {@link FighterPlane} and moves horizontally while firing projectiles at a certain rate.
 * The enemy plane is destructible and has a specific health value.
 */
public class EnemyPlane extends FighterPlane {

	private final LevelParent levelParent;

	// Constants for the enemy plane's properties
	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_WIDTH = 150;
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .0025;

	/**
	 * Constructs an EnemyPlane object with the specified initial position and level.
	 *
	 * @param initialXPos The initial X position of the enemy plane.
	 * @param initialYPos The initial Y position of the enemy plane.
	 * @param levelParent The level parent that holds the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos, LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		this.levelParent = levelParent;
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.35);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires projectiles from the enemy plane. The projectile firing is based on a random chance
	 * controlled by the {@link #FIRE_RATE}. If the random chance meets the fire rate, a projectile is created.
	 *
	 * @return A list of projectiles fired by the enemy plane.
	 */
	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

			projectiles.add(new EnemyProjectile(projectileXPosition, projectileYPosition, levelParent));
		}

		return projectiles;
	}

	/**
	 * Updates the state of the enemy plane, including its position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateHitbox();
	}
}