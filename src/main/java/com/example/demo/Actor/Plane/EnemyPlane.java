package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectile.EnemyProjectile;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EnemyPlane} class represents an enemy plane in the game.
 * It extends {@link FighterPlane} and provides functionality for updating its position,
 * firing projectiles, and handling the plane's interactions with the game world.
 */
public class EnemyPlane extends FighterPlane {

	/**
	 * The image name of the enemy plane, used to load its sprite.
	 */
	private static final String IMAGE_NAME = "enemyplane.png";

	/**
	 * The width of the enemy plane's image.
	 */
	private static final int IMAGE_WIDTH = 150;

	/**
	 * The height of the enemy plane's image.
	 */
	private static final int IMAGE_HEIGHT = 150;

	/**
	 * The horizontal velocity at which the enemy plane moves, representing its speed.
	 */
	private static final int HORIZONTAL_VELOCITY = -2;

	/**
	 * The X-axis offset used to determine the projectile's position when fired by the enemy plane.
	 */
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

	/**
	 * The Y-axis offset used to determine the projectile's position when fired by the enemy plane.
	 */
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

	/**
	 * The initial health of the enemy plane, which starts at 1.
	 */
	private static final int INITIAL_HEALTH = 1;

	/**
	 * The fire rate of the enemy plane, which determines how often it fires projectiles.
	 * A value between 0 and 1, where smaller values mean less frequent firing.
	 */
	private static final double FIRE_RATE = .0025;

	/**
	 * Constructor to initialize the {@code EnemyPlane} with its starting position and health.
	 *
	 * @param initialXPos The initial X position of the enemy plane.
	 * @param initialYPos The initial Y position of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
		setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT * 0.35);
	}

	/**
	 * Updates the enemy plane's position by moving it horizontally at a constant speed.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		updateHitbox();
	}

	/**
	 * Fires projectiles from the enemy plane at a rate determined by the {@code FIRE_RATE}.
	 *
	 * @return A list of projectiles fired by the enemy plane.
	 */
	@Override
	public List<ActiveActorDestructible> fireProjectiles() {
		List<ActiveActorDestructible> projectiles = new ArrayList<>();

		// Fire projectiles at a random rate based on FIRE_RATE
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

			projectiles.add(new EnemyProjectile(projectileXPosition, projectileYPosition));
		}

		return projectiles;
	}

	/**
	 * Updates the enemy plane's actor state.
	 * This method is called every game tick to update the plane's position and hitbox.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
}