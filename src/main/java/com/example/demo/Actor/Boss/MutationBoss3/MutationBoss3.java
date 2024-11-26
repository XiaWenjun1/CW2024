package com.example.demo.Actor.Boss.MutationBoss3;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Boss.BossFirePattern;
import com.example.demo.Actor.Boss.ParentBoss.Boss;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MutationBoss3} class represents a mutated version of a boss enemy in the game.
 * It extends the {@code Boss} class and overrides its behavior, such as fire patterns and image settings.
 */
public class MutationBoss3 extends Boss {

    private static final String MUTATION_BOSS_IMAGE_NAME = "mutation3.png"; // Image name for the mutated boss
    private static final int IMAGE_WIDTH = 250; // Width of the mutated boss image
    private static final int IMAGE_HEIGHT = 250; // Height of the mutated boss image
    private static final double BOSS_FIRE_RATE = 0.025; // Probability of firing a projectile in each frame

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
        setHitboxSize(IMAGE_WIDTH, IMAGE_HEIGHT); // Sets the size of the hitbox
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