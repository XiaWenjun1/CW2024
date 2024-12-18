package com.example.demo.Actor.Projectile;

import com.example.demo.Actor.Plane.MutationBoss1;

/**
 * The {@code MutationBossProjectile1} class represents a projectile fired by {@link MutationBoss1}.
 * It extends the {@link BossProjectile} class and is responsible for managing the appearance
 * and behavior of the projectile specific to MutationBoss1.
 */
public class MutationBossProjectile1 extends BossProjectile {

    /**
     * The image file name for the mutated boss projectile.
     * This is used to customize the appearance of the projectile fired by the MutationBoss1.
     */
    protected static final String NEW_IMAGE_NAME = "mutationBossProjectile1.png"; // Image name for the mutation boss projectile

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