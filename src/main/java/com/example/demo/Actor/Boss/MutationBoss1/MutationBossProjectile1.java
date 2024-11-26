package com.example.demo.Actor.Boss.MutationBoss1;

import com.example.demo.Actor.Boss.ParentBoss.BossProjectile;

/**
 * The {@code MutationBossProjectile1} class represents a projectile fired by {@link MutationBoss1}.
 * It extends the {@link BossProjectile} class and is responsible for managing the appearance
 * and behavior of the projectile specific to MutationBoss1.
 */
public class MutationBossProjectile1 extends BossProjectile {

    private static final String NEW_IMAGE_NAME = "mutationBossProjectile1.png"; // Image name for the mutation boss projectile

    /**
     * Constructs a {@code MutationBossProjectile1} with the specified initial position.
     * Sets the image for the projectile.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public MutationBossProjectile1(double initialXPos, double initialYPos) {
        super(initialXPos, initialYPos); // Call the parent class constructor with initial positions
        setImage(NEW_IMAGE_NAME); // Set the image specific to MutationBossProjectile1
    }
}