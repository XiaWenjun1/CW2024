package com.example.demo.Actor.Projectile;

/**
 * The {@code MutationBossProjectile2} class represents a specific type of projectile fired by the
 * mutated boss 2 in the game. It extends the {@link BossProjectile} class and uses a custom image
 * for the projectile.
 */
public class MutationBossProjectile2 extends BossProjectile {

    /**
     * The image file name for the mutated boss projectile.
     * This is used to customize the appearance of the projectile fired by the MutationBoss2.
     */
    protected static final String NEW_IMAGE_NAME = "mutationBossProjectile2.png"; // Image name for the mutated boss projectile

    /**
     * Constructs a {@code MutationBossProjectile2} with the specified initial position.
     * Sets the image to represent the mutated boss projectile.
     *
     * @param initialXPos The initial X-coordinate of the projectile.
     * @param initialYPos The initial Y-coordinate of the projectile.
     */
    public MutationBossProjectile2(double initialXPos, double initialYPos) {
        super(initialXPos, initialYPos); // Call the superclass constructor to set the initial position
        setImage(NEW_IMAGE_NAME); // Set the custom image for this type of projectile
    }
}