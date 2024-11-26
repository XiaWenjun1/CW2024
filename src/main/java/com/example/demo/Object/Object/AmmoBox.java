package com.example.demo.Object.Object;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The AmmoBox class represents an ammo box object in the game.
 * It extends {@link ActiveActorDestructible} and moves horizontally across the screen.
 * The ammo box can be collected by the player, and its spawn probability is defined.
 */
public class AmmoBox extends ActiveActorDestructible {

    // Constants for the ammo box's properties
    private static final String IMAGE_NAME = "ammobox.png";
    private static final int IMAGE_WIDTH = 40;
    private static final int IMAGE_HEIGHT = 40;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final double SPAWN_PROBABILITY = 0.01;
    private static final double MaximumXPosition = 1350;
    private static final double MaximumYPosition = 600;

    /**
     * Constructs a new AmmoBox object with the specified initial position.
     *
     * @param initialXPos The initial X position of the ammo box.
     * @param initialYPos The initial Y position of the ammo box.
     */
    public AmmoBox(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.setTranslateX(initialXPos);
        this.setTranslateY(initialYPos);
    }

    /**
     * Moves the ammo box horizontally by the specified amount.
     *
     * @param horizontalMove The distance to move the ammo box horizontally.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    /**
     * Updates the position of the ammo box by moving it horizontally.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the ammo box, including its position and hitbox.
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    /**
     * Handles the behavior when the ammo box takes damage.
     * In this implementation, the ammo box does not take damage.
     */
    @Override
    public void takeDamage() {
        // Ammo box does not take damage
    }

    /**
     * Returns the probability of the ammo box spawning in the game.
     *
     * @return The spawn probability of the ammo box.
     */
    public static double getSpawnProbability() {
        return SPAWN_PROBABILITY;
    }

    /**
     * Returns the maximum X position for the ammo box in the game.
     *
     * @return The maximum X position of the ammo box.
     */
    public static double getMaximumXPosition() {
        return MaximumXPosition;
    }

    /**
     * Returns the maximum Y position for the ammo box in the game.
     *
     * @return The maximum Y position of the ammo box.
     */
    public static double getMaximumYPosition() {
        return MaximumYPosition;
    }
}