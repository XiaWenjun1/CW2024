package com.example.demo.Object.Object;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The Heart class represents a collectible heart object in the game.
 * It extends {@link ActiveActorDestructible} and moves horizontally while oscillating vertically.
 * The heart can be collected by the player to gain health or points.
 */
public class Heart extends ActiveActorDestructible {

    // Constants for the heart's properties
    private static final String IMAGE_NAME = "heart.png";
    private static final int IMAGE_WIDTH = 30;
    private static final int IMAGE_HEIGHT = 30;
    private static final int HORIZONTAL_VELOCITY = -3;
    private static final double SPAWN_PROBABILITY = 0.005;
    private static final double MaximumXPosition = 1350;
    private static final double MaximumYPosition = 600;
    private static final double VERTICAL_AMPLITUDE = 50;
    private static final double VERTICAL_VELOCITY = 0.05;

    private double time = 0;

    /**
     * Constructs a new Heart object with the specified initial position.
     *
     * @param initialXPos The initial X position of the heart.
     * @param initialYPos The initial Y position of the heart.
     */
    public Heart(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_WIDTH, IMAGE_HEIGHT, initialXPos, initialYPos);
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        this.setTranslateX(initialXPos);
        this.setTranslateY(initialYPos);
    }

    /**
     * Moves the heart horizontally by the specified amount.
     *
     * @param horizontalMove The distance to move the heart horizontally.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    /**
     * Moves the heart vertically with a sinusoidal oscillation effect.
     * The heart moves up and down based on the sine wave function.
     */
    protected void moveVertically() {
        time += VERTICAL_VELOCITY;
        double offsetY = VERTICAL_AMPLITUDE * Math.sin(time);
        this.setTranslateY(this.getInitialY() + offsetY);
    }

    /**
     * Updates the position of the heart by moving it horizontally and vertically.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically();
    }

    /**
     * Updates the state of the heart, including its position and hitbox.
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    /**
     * Handles the behavior when the heart takes damage.
     * In this implementation, the heart does not take damage.
     */
    @Override
    public void takeDamage() {
        // Heart does not take damage
    }

    /**
     * Returns the initial Y position of the heart.
     *
     * @return The initial Y position of the heart.
     */
    private double getInitialY() {
        return this.getLayoutY();
    }

    /**
     * Returns the probability of the heart spawning in the game.
     *
     * @return The spawn probability of the heart.
     */
    public static double getSpawnProbability() {
        return SPAWN_PROBABILITY;
    }

    /**
     * Returns the maximum X position for the heart in the game.
     *
     * @return The maximum X position of the heart.
     */
    public static double getMaximumXPosition() {
        return MaximumXPosition;
    }

    /**
     * Returns the maximum Y position for the heart in the game.
     *
     * @return The maximum Y position of the heart.
     */
    public static double getMaximumYPosition() {
        return MaximumYPosition;
    }
}