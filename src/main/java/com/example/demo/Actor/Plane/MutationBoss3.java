package com.example.demo.Actor.Plane;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.Boss.BossFirePattern;
import com.example.demo.Actor.Plane.Boss.Boss;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MutationBoss3} class represents a mutated version of a boss enemy in the game.
 * It extends the {@code Boss} class and overrides its behavior, such as fire patterns and image settings.
 */
public class MutationBoss3 extends Boss {
    /**
     * The image file name for the mutated version of the boss.
     * This is used to customize the appearance of the MutationBoss3 in the game.
     */
    private static final String MUTATION_BOSS_IMAGE_NAME = "mutation3.png"; // Image name for the mutated boss

    /**
     * The width of the mutated boss image.
     */
    private static final int IMAGE_WIDTH = 250; // Width of the mutated boss image

    /**
     * The height of the mutated boss image.
     */
    private static final int IMAGE_HEIGHT = 250; // Height of the mutated boss image

    /**
     * The probability of the mutated boss firing a projectile in each frame.
     * This is used to determine the firing rate.
     */
    private static final double BOSS_FIRE_RATE = 0.025; // Probability of firing a projectile in each frame

    /**
     * The boss's fire pattern manager.
     * It is responsible for handling the different types of projectiles the boss can fire.
     */
    private final BossFirePattern firePattern; // The boss's fire pattern manager

    /**
     * Constructs a {@code MutationBoss3} object with the specified initial health.
     *
     * @param initialHealth The initial health of the mutated boss.
     */
    public MutationBoss3(int initialHealth) {
        super(initialHealth);
        this.firePattern = new BossFirePattern(this);
        setImage(MUTATION_BOSS_IMAGE_NAME); // Sets the image of the mutated boss
        setImageSize(IMAGE_WIDTH, IMAGE_HEIGHT); // Sets the size of the mutated boss image
    }

    /**
     * Sets the size of the boss image.
     *
     * @param width  The width of the boss image.
     * @param height The height of the boss image.
     */
    private void setImageSize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    /**
     * Fires projectiles based on the mutation boss's attack type.
     *
     * @return A list of projectiles fired by the mutated boss.
     */
    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        if (!bossFiresInCurrentFrame()) {
            return new ArrayList<>();
        }

        int attackType = firePattern.selectAttackType();
        return switch (attackType) {
            case 1 -> firePattern.createStraightProjectile(); // Fires a straight projectile
            case 2 -> firePattern.createTwoProjectiles(); // Fires two projectiles
            case 3 -> firePattern.createScatterProjectiles(); // Fires scattered projectiles
            case 4 -> firePattern.createDirectionalProjectiles(); // Fires directional projectiles
            default -> new ArrayList<>();
        };
    }

    /**
     * Determines if the mutated boss fires a projectile in the current frame.
     *
     * @return True if the mutated boss fires a projectile, false otherwise.
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE;
    }
}