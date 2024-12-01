package com.example.demo.Actor.HeavyEnemy;

import com.example.demo.Actor.Projectile;

/**
 * Represents a projectile fired by a {@link HeavyEnemy} plane.
 * The {@code HeavyEnemyProjectile} class extends {@link Projectile} and defines the behavior of projectiles
 * fired by the heavy enemy planes. It moves horizontally across the screen and has a specific image and speed.
 */
public class HeavyEnemyProjectile extends Projectile {

    /**
     * The name of the image file used to represent the heavy enemy projectile.
     */
    private static final String IMAGE_NAME = "heavyEnemyProjectile.png";

    /**
     * The width of the heavy enemy projectile's sprite in pixels.
     */
    private static final int IMAGE_WIDTH = 50;

    /**
     * The height of the heavy enemy projectile's sprite in pixels.
     */
    private static final int IMAGE_HEIGHT = 50;

    /**
     * The horizontal velocity of the heavy enemy projectile, indicating its speed.
     * A negative value means the projectile moves from right to left across the screen.
     */
    private static final int HORIZONTAL_VELOCITY = -7;

    /**
     * Initializes a new {@code HeavyEnemyProjectile} at the specified position.
     * This constructor sets up the projectile with its image, size, initial position, and hitbox.
     *
     * @param initialXPos The initial X-coordinate of the projectile.
     * @param initialYPos The initial Y-coordinate of the projectile.
     */
    public HeavyEnemyProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    /**
     * Updates the position of the enemy projectile.
     * The projectile moves horizontally at a constant speed determined by {@code HORIZONTAL_VELOCITY}.
     * This method is called every frame to adjust the position of the projectile and update its hitbox.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        updateHitbox();
    }

    /**
     * Updates the state of the enemy projectile.
     * This method is called every game tick to update the projectile's position and hitbox for collision detection.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}