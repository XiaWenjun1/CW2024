package com.example.demo.Actor.Boss.MutationBoss1;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Boss.BossFirePattern;
import com.example.demo.Actor.Boss.ParentBoss.Boss;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MutationBoss1} class represents a specific type of mutated boss in the game.
 * It extends the {@link Boss} class and is responsible for managing the boss's behavior,
 * including firing projectiles in various patterns.
 */
public class MutationBoss1 extends Boss {

    /**
     * The image file name for the mutated boss 1.
     * This is used to customize the appearance of the mutated boss in the game.
     */
    private static final String MUTATION_BOSS_IMAGE_NAME = "mutation1.png"; // Image name for the mutated boss 1

    /**
     * The width of the mutated boss image.
     * This value is used to set the size of the boss image when rendered on the screen.
     */
    private static final int IMAGE_WIDTH = 250; // Width of the boss image

    /**
     * The height of the mutated boss image.
     * This value is used to set the size of the boss image when rendered on the screen.
     */
    private static final int IMAGE_HEIGHT = 250; // Height of the boss image

    /**
     * The fire rate of the mutated boss.
     * This value represents the probability of the boss firing a projectile during each frame update.
     */
    private static final double BOSS_FIRE_RATE = 0.05; // Probability of firing a projectile in each frame

    /**
     * The fire pattern manager for the mutated boss.
     * This object is responsible for defining and selecting the type of projectiles the boss fires.
     */
    private final BossFirePattern firePattern; // The boss's fire pattern manager

    /**
     * Constructs a {@code MutationBoss1} object with the specified initial health.
     * Sets up the fire pattern manager and assigns the appropriate image for the boss.
     *
     * @param initialHealth The initial health of the boss.
     */
    public MutationBoss1(int initialHealth) {
        super(initialHealth);
        this.firePattern = new BossFirePattern(this);
        setImage(MUTATION_BOSS_IMAGE_NAME); // Set the image for the mutated boss 1
        setImageSize(IMAGE_WIDTH, IMAGE_HEIGHT); // Set the size for the boss image
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT); // Set the hitbox size for the boss
    }

    /**
     * Sets the size of the boss's image.
     *
     * @param width  The width of the image.
     * @param height The height of the image.
     */
    private void setImageSize(double width, double height) {
        setFitWidth(width);
        setFitHeight(height);
    }

    /**
     * Fires projectiles based on the boss's current attack type.
     * The attack type is determined by the boss's fire pattern manager.
     *
     * @return A list of projectiles fired by the boss, depending on the selected attack type.
     */
    @Override
    public List<ActiveActorDestructible> fireProjectiles() {
        if (!bossFiresInCurrentFrame()) {
            return new ArrayList<>(); // No projectiles fired if the condition is not met
        }

        int attackType = firePattern.selectAttackType(); // Select attack type from the fire pattern manager
        return switch (attackType) {
            case 1 -> firePattern.createStraightProjectile(); // Create a straight projectile
            case 2 -> firePattern.createTwoProjectiles(); // Create two projectiles
            default -> new ArrayList<>(); // Default case (no projectiles)
        };
    }

    /**
     * Determines if the boss fires a projectile in the current frame based on the fire rate.
     *
     * @return True if the boss fires a projectile, false otherwise.
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE; // Random chance to fire a projectile
    }
}