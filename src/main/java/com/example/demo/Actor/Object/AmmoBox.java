package com.example.demo.Actor.Object;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The {@code AmmoBox} class represents an ammo box object in the game.
 * It extends {@link ActiveActorDestructible} and moves horizontally across the screen.
 * The ammo box can be collected by the player to replenish ammo or provide power-ups.
 * <p>
 * The ammo box moves with a constant horizontal velocity, and its appearance in the game
 * is based on a spawn probability.
 * </p>
 */
public class AmmoBox extends ActiveActorDestructible {

    /**
     * The name of the image file for the ammo box.
     * This constant is used to load the image of the ammo box during initialization.
     */
    private static final String IMAGE_NAME = "ammobox.png";

    /**
     * The width of the ammo box image.
     * This constant defines the width of the ammo box when it is displayed on the screen.
     */
    private static final int IMAGE_WIDTH = 40;

    /**
     * The height of the ammo box image.
     * This constant defines the height of the ammo box when it is displayed on the screen.
     */
    private static final int IMAGE_HEIGHT = 40;

    /**
     * The horizontal velocity of the ammo box.
     * This constant defines how fast the ammo box moves horizontally across the screen.
     */
    private static final int HORIZONTAL_VELOCITY = -6;

    /**
     * The probability of the ammo box spawning in the game.
     * This constant determines the chance that an ammo box will appear during gameplay.
     */
    private static final double SPAWN_PROBABILITY = 0.01;

    /**
     * The maximum X position for the ammo box in the game.
     * This constant defines the furthest horizontal position the ammo box can reach before disappearing.
     */
    private static final double MaximumXPosition = 1350;

    /**
     * The maximum Y position for the ammo box in the game.
     * This constant defines the maximum vertical position the ammo box can reach.
     */
    private static final double MaximumYPosition = 600;

    /**
     * Constructs a new {@code AmmoBox} object with the specified initial position.
     * <p>
     * The ammo box is initialized with an image, width, height, and the given initial position.
     * It is displayed on the screen at the specified starting position.
     * </p>
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
     * <p>
     * This method updates the horizontal position of the ammo box, moving it to the left across the screen.
     * </p>
     *
     * @param horizontalMove The distance to move the ammo box horizontally.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    /**
     * Updates the position of the ammo box by moving it horizontally.
     * <p>
     * This method is called in each game frame to update the ammo box's horizontal position on the screen.
     * </p>
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the state of the ammo box, including its position and hitbox.
     * <p>
     * This method calls {@code updatePosition()} to move the ammo box and {@code updateHitbox()} to
     * update the ammo box's collision detection area.
     * </p>
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    /**
     * Handles the behavior when the ammo box takes damage.
     * <p>
     * In this implementation, the ammo box does not take damage, so this method does nothing.
     * </p>
     */
    @Override
    public void takeDamage() {
        // Ammo box does not take damage
    }

    /**
     * Returns the probability of the ammo box spawning in the game.
     * <p>
     * This method provides the chance that an ammo box will appear in the game during gameplay.
     * </p>
     *
     * @return The spawn probability of the ammo box.
     */
    public static double getSpawnProbability() {
        return SPAWN_PROBABILITY;
    }

    /**
     * Returns the maximum X position for the ammo box in the game.
     * <p>
     * This method provides the maximum horizontal position the ammo box can reach within the game area.
     * </p>
     *
     * @return The maximum X position of the ammo box.
     */
    public static double getMaximumXPosition() {
        return MaximumXPosition;
    }

    /**
     * Returns the maximum Y position for the ammo box in the game.
     * <p>
     * This method provides the maximum vertical position the ammo box can reach within the game area.
     * </p>
     *
     * @return The maximum Y position of the ammo box.
     */
    public static double getMaximumYPosition() {
        return MaximumYPosition;
    }
}