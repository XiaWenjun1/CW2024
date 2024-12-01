package com.example.demo.Object.Object;

import com.example.demo.Actor.ActiveActorDestructible;

/**
 * The {@code Heart} class represents a collectible heart object in the game.
 * It extends {@link ActiveActorDestructible} and moves horizontally while oscillating vertically.
 * The heart can be collected by the player to gain health or points.
 * <p>
 * The heart object moves across the screen with a horizontal velocity while oscillating up and down in a sinusoidal pattern.
 * </p>
 */
public class Heart extends ActiveActorDestructible {

    /**
     * The name of the image file for the heart.
     * <p>
     * This constant specifies the name of the image file that represents the heart. This image is used when the heart
     * is initialized and displayed in the game.
     * </p>
     */
    private static final String IMAGE_NAME = "heart.png";

    /**
     * The width of the heart image.
     * <p>
     * This constant defines the width of the heart when it is displayed on the screen. It is used to size the heart
     * image when rendering it in the game.
     * </p>
     */
    private static final int IMAGE_WIDTH = 30;

    /**
     * The height of the heart image.
     * <p>
     * This constant defines the height of the heart when it is displayed on the screen. It is used to size the heart
     * image when rendering it in the game.
     * </p>
     */
    private static final int IMAGE_HEIGHT = 30;

    /**
     * The horizontal velocity of the heart.
     * <p>
     * This constant controls the speed at which the heart moves horizontally across the screen. It is used to calculate
     * the heart's movement during the game.
     * </p>
     */
    private static final int HORIZONTAL_VELOCITY = -3;

    /**
     * The probability of the heart spawning in the game.
     * <p>
     * This constant determines the likelihood that a heart will appear in the game. The value is a decimal between 0 and 1,
     * where 0 means no hearts spawn and 1 means hearts will always spawn.
     * </p>
     */
    private static final double SPAWN_PROBABILITY = 0.005;

    /**
     * The maximum X position for the heart in the game.
     * <p>
     * This constant defines the furthest horizontal position the heart can reach in the game. Once the heart reaches
     * this position, it is considered off-screen or out of bounds.
     * </p>
     */
    private static final double MaximumXPosition = 1350;

    /**
     * The maximum Y position for the heart in the game.
     * <p>
     * This constant defines the furthest vertical position the heart can reach in the game. Once the heart reaches
     * this position, it is considered off-screen or out of bounds.
     * </p>
     */
    private static final double MaximumYPosition = 600;

    /**
     * The vertical amplitude for the heart's oscillation.
     * <p>
     * This constant controls how far the heart moves vertically as it oscillates. A higher value results in a wider
     * oscillation, making the heart move up and down over a greater distance.
     * </p>
     */
    private static final double VERTICAL_AMPLITUDE = 50;

    /**
     * The vertical velocity of the heart.
     * <p>
     * This constant controls how fast the heart moves vertically in the oscillating motion. A higher value makes the
     * heart oscillate faster up and down.
     * </p>
     */
    private static final double VERTICAL_VELOCITY = 0.05;

    /**
     * The time variable used for the heart's oscillation calculation.
     * <p>
     * This variable tracks the current time in the game, which is used to calculate the heart's vertical movement and
     * oscillation effect.
     * </p>
     */
    private double time = 0;

    /**
     * Constructs a new {@code Heart} object with the specified initial position.
     * <p>
     * The heart is initialized with an image, width, height, and starting position.
     * The heart will be displayed on the screen at the given initial position.
     * </p>
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
     * <p>
     * This method updates the horizontal position of the heart, moving it to the left.
     * </p>
     *
     * @param horizontalMove The distance to move the heart horizontally.
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(this.getTranslateX() + horizontalMove);
    }

    /**
     * Moves the heart vertically with a sinusoidal oscillation effect.
     * <p>
     * This method updates the vertical position of the heart, making it move up and down
     * based on a sine wave function to create an oscillating motion.
     * </p>
     */
    protected void moveVertically() {
        time += VERTICAL_VELOCITY;
        double offsetY = VERTICAL_AMPLITUDE * Math.sin(time);
        this.setTranslateY(this.getInitialY() + offsetY);
    }

    /**
     * Updates the position of the heart by moving it horizontally and vertically.
     * <p>
     * This method is called in each game frame to update the heart's position on the screen.
     * The heart's horizontal and vertical movements are handled in this method.
     * </p>
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        moveVertically();
    }

    /**
     * Updates the state of the heart, including its position and hitbox.
     * <p>
     * This method calls {@code updatePosition()} to move the heart and {@code updateHitbox()} to
     * update the heart's collision detection area.
     * </p>
     */
    @Override
    public void updateActor() {
        updatePosition();
        updateHitbox();
    }

    /**
     * Handles the behavior when the heart takes damage.
     * <p>
     * In this implementation, the heart does not take damage, so this method does nothing.
     * </p>
     */
    @Override
    public void takeDamage() {
        // Heart does not take damage
    }

    /**
     * Returns the initial Y position of the heart.
     * <p>
     * This method retrieves the starting vertical position of the heart, which is used for vertical oscillation.
     * </p>
     *
     * @return The initial Y position of the heart.
     */
    private double getInitialY() {
        return this.getLayoutY();
    }

    /**
     * Returns the probability of the heart spawning in the game.
     * <p>
     * This method provides the chance that a heart will appear in the game during gameplay.
     * </p>
     *
     * @return The spawn probability of the heart.
     */
    public static double getSpawnProbability() {
        return SPAWN_PROBABILITY;
    }

    /**
     * Returns the maximum X position for the heart in the game.
     * <p>
     * This method provides the maximum horizontal position the heart can move to within the game area.
     * </p>
     *
     * @return The maximum X position of the heart.
     */
    public static double getMaximumXPosition() {
        return MaximumXPosition;
    }

    /**
     * Returns the maximum Y position for the heart in the game.
     * <p>
     * This method provides the maximum vertical position the heart can move to within the game area.
     * </p>
     *
     * @return The maximum Y position of the heart.
     */
    public static double getMaximumYPosition() {
        return MaximumYPosition;
    }
}