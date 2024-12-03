package com.example.demo.Actor.Projectile;

/**
 * The {@code MutationBossProjectile3} class represents a specialized projectile fired by the {@code MutationBoss3}.
 * It extends the {@code BossProjectile} class and customizes the appearance of the projectile.
 */
public class MutationBossProjectile3 extends BossProjectile {

    /**
     * The image file name for the mutated boss projectile.
     * This is the customized image for the MutationBossProjectile3.
     */
    protected static final String NEW_IMAGE_NAME = "mutationBossProjectile3.png"; // Image for the mutated boss projectile

    /**
     * Constructs a {@code MutationBossProjectile3} with the specified initial position.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public MutationBossProjectile3(double initialXPos, double initialYPos) {
        super(initialXPos, initialYPos); // Calls the constructor of the superclass (BossProjectile)
        setImage(NEW_IMAGE_NAME); // Sets the image for the mutated boss projectile
    }
}