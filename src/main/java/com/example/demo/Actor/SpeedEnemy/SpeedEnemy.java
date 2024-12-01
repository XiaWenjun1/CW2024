package com.example.demo.Actor.SpeedEnemy;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a fast-moving enemy plane in the game.
 * <p>
 * The {@code SpeedEnemy} class extends {@link FighterPlane} and defines the behavior of a lightweight,
 * high-speed enemy aircraft. It has minimal health and moves quickly across the screen.
 * This enemy type does not fire projectiles.
 * </p>
 */
public class SpeedEnemy extends FighterPlane {

    /**
     * The name of the image file used to represent the speed enemy.
     */
    private static final String IMAGE_NAME = "speedEnemy.png";

    /**
     * The width of the speed enemy's sprite in pixels.
     */
    private static final int IMAGE_WIDTH = 70;

    /**
     * The height of the speed enemy's sprite in pixels.
     */
    private static final int IMAGE_HEIGHT = 70;

    /**
     * The horizontal velocity of the speed enemy, determining its fast movement.
     * A negative value indicates movement from right to left.
     */
    private static final int HORIZONTAL_VELOCITY = -5;

    /**
     * The initial health of the speed enemy.
     * This enemy type is fragile and starts with 1 health point.
     */
    private static final int INITIAL_HEALTH = 1;

    /**
     * Creates a new {@code SpeedEnemy} instance at the specified initial position.
     * <p>
     * This constructor sets up the speed enemy with its sprite, size, position, and health.
     * It also defines the hitbox size based on the enemy's dimensions.
     * </p>
     *
     * @param initialXPos the initial X-coordinate of the speed enemy.
     * @param initialYPos the initial Y-coordinate of the speed enemy.
     */
    public SpeedEnemy(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    /**
     * Updates the position of the speed enemy by moving it horizontally.
     * <p>
     * The enemy moves at a constant speed determined by {@code HORIZONTAL_VELOCITY}.
     * This method also updates the hitbox to match the enemy's new position.
     * </p>
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        updateHitbox();
    }

    /**
     * Returns an empty list as the speed enemy does not fire projectiles.
     * <p>
     * This method overrides {@link FighterPlane#fireProjectiles()} and ensures that
     * no projectiles are created by this enemy type.
     * </p>
     *
     * @return an empty list, indicating no projectiles are fired.
     */
    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        return new ArrayList<>();
    }

    /**
     * Updates the state of the speed enemy during each game tick.
     * <p>
     * This method updates the enemy's position and refreshes its hitbox to ensure
     * accurate collision detection.
     * </p>
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}