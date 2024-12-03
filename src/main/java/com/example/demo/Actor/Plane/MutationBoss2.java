package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.Boss.BossFirePattern;
import com.example.demo.Actor.Plane.Boss.Boss;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MutationBoss2} class represents a specific mutation of the boss enemy in the game.
 * It extends the {@code Boss} class and customizes the appearance and firing rate of the boss.
 * This mutation of the boss fires projectiles in different patterns based on the attack type.
 */
public class MutationBoss2 extends Boss {

    /**
     * The image file name for the mutated version of the boss.
     * This is used to customize the appearance of the MutationBoss3 in the game.
     */
    private static final String MUTATION_BOSS_IMAGE_NAME = "mutation2.png"; // Image name for the mutated boss

    /**
     * The width of the mutated boss image.
     * This determines how wide the boss will appear in the game.
     */
    private static final int IMAGE_WIDTH = 250; // Width of the mutated boss image

    /**
     * The height of the mutated boss image.
     * This determines how tall the boss will appear in the game.
     */
    private static final int IMAGE_HEIGHT = 250; // Height of the mutated boss image

    /**
     * The probability of the mutated boss firing a projectile in each frame.
     * This controls the firing rate of the mutated boss, influencing how often it fires projectiles.
     */
    private static final double BOSS_FIRE_RATE = 0.04; // Probability of firing a projectile in each frame

    /**
     * The fire pattern manager for the mutated boss.
     * It is responsible for handling the different types of projectiles the boss can fire,
     * such as straight, spread, or directional projectiles.
     */
    private final BossFirePattern firePattern; // The fire pattern manager for the mutated boss

    /**
     * Constructs a {@code MutationBoss2} with the specified initial health.
     * Initializes the fire pattern and sets the image, size, and hitbox.
     *
     * @param initialHealth The initial health of the mutated boss.
     */
    public MutationBoss2(int initialHealth) {
        super(initialHealth);
        this.firePattern = new BossFirePattern(this);
        setImage(MUTATION_BOSS_IMAGE_NAME); // Set the boss's image to the mutated version
        setImageSize(IMAGE_WIDTH, IMAGE_HEIGHT); // Set the size of the boss image
    }

    /**
     * Sets the image size for the mutated boss.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     */
    private void setImageSize(double width, double height) {
        setFitWidth(width);  // Set the width of the image
        setFitHeight(height); // Set the height of the image
    }

    /**
     * Determines whether the boss fires projectiles in the current frame and selects an attack pattern.
     * Fires projectiles based on the selected attack type.
     *
     * @return A list of {@link ActiveActorDestructible} representing the projectiles fired by the boss.
     */
    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        if (!bossFiresInCurrentFrame()) {
            return new ArrayList<>(); // Return an empty list if the boss doesn't fire
        }

        int attackType = firePattern.selectAttackType(); // Select an attack type (1: Straight, 2: Two, 3: Scatter)
        return switch (attackType) {
            case 1 -> firePattern.createStraightProjectile(); // Straight projectile pattern
            case 2 -> firePattern.createTwoProjectiles();    // Two projectiles pattern
            case 3 -> firePattern.createScatterProjectiles(); // Scatter projectiles pattern
            default -> new ArrayList<>(); // Return an empty list if no valid attack type
        };
    }

    /**
     * Determines whether the boss fires projectiles in the current frame based on its firing rate.
     *
     * @return True if the boss fires projectiles, false otherwise.
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE; // Randomly decide if the boss fires based on the fire rate
    }
}