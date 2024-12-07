package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Projectile.HeavyEnemyProjectile;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a heavy enemy plane in the game.
 * <p>
 * The {@code HeavyEnemy} class extends {@link FighterPlane} and defines the behavior of a slow-moving, high-health enemy.
 * It has the ability to fire projectiles at a low rate but poses a significant challenge due to its durability.
 * </p>
 */
public class HeavyEnemy extends FighterPlane {

    /**
     * The name of the image file used to represent the heavy enemy.
     */
    private static final String IMAGE_NAME = "heavyEnemy.png";

    /**
     * The width of the heavy enemy's sprite in pixels.
     */
    private static final int IMAGE_WIDTH = 100;

    /**
     * The height of the heavy enemy's sprite in pixels.
     */
    private static final int IMAGE_HEIGHT = 100;

    /**
     * The horizontal velocity of the heavy enemy, representing its slow movement.
     * A negative value indicates movement from right to left.
     */
    private static final int HORIZONTAL_VELOCITY = -1;

    /**
     * The X-axis offset used to calculate the projectile's firing position relative to the heavy enemy.
     */
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;

    /**
     * The Y-axis offset used to calculate the projectile's firing position relative to the heavy enemy.
     */
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;

    /**
     * The initial health of the heavy enemy.
     * This enemy type is highly durable and starts with 5 health points.
     */
    private static final int INITIAL_HEALTH = 5;

    /**
     * The fire rate of the heavy enemy, determining the probability of firing projectiles each tick.
     * A value between 0 and 1, where smaller values mean less frequent firing.
     */
    private static final double FIRE_RATE = 0.0025;

    /**
     * Creates a new {@code HeavyEnemy} instance at the specified initial position.
     * <p>
     * This constructor sets up the heavy enemy with its sprite, size, position, and health.
     * It also defines the hitbox size based on the enemy's dimensions.
     * </p>
     *
     * @param initialXPos the initial X-coordinate of the heavy enemy.
     * @param initialYPos the initial Y-coordinate of the heavy enemy.
     */
    public HeavyEnemy(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    /**
     * Updates the position of the heavy enemy by moving it horizontally at a constant speed.
     * <p>
     * The enemy moves slowly from right to left, as determined by {@code HORIZONTAL_VELOCITY}.
     * This method also updates the hitbox to reflect the new position.
     * </p>
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        updateHitbox();
    }

    /**
     * Fires projectiles from the heavy enemy based on a random chance determined by {@code FIRE_RATE}.
     * <p>
     * Projectiles are created at positions offset from the enemy's current position.
     * The method uses random probability to determine whether to fire a projectile during each tick.
     * </p>
     *
     * @return A list of projectiles fired by the heavy enemy. If no projectile is fired, the list is empty.
     */
    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();

        // Fire projectiles at a random rate based on FIRE_RATE
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);

            projectiles.add(new HeavyEnemyProjectile(projectileXPosition, projectileYPosition));
        }

        return projectiles;
    }

    /**
     * Updates the state of the heavy enemy during each game tick.
     * <p>
     * This includes updating its position and refreshing its hitbox to ensure accurate collision detection.
     * </p>
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}