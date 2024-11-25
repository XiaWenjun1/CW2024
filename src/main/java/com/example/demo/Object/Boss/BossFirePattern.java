package com.example.demo.Object.Boss;

import com.example.demo.Actor.ActiveActorDestructible;

import java.util.ArrayList;
import java.util.List;

/**
 * The BossFirePattern class represents the firing patterns for the boss in the game.
 * It handles the generation of projectiles for different attack types, such as straight, scatter, and directional.
 */
public class BossFirePattern {

    private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0; // Vertical offset for projectile spawn
    private final Boss boss; // The boss object that fires projectiles

    /**
     * Constructs a BossFirePattern object that controls the firing patterns of the boss.
     * @param boss The boss object that will fire projectiles.
     */
    public BossFirePattern(Boss boss) {
        this.boss = boss;
    }

    /**
     * Retrieves the current X position of the boss.
     *
     * @return The boss's current X position.
     */
    private double getBossXPosition() {
        return boss.getLayoutX() + boss.getTranslateX();
    }

    /**
     * Retrieves the current Y position of the boss.
     *
     * @return The boss's current Y position.
     */
    private double getBossYPosition() {
        return boss.getLayoutY() + boss.getTranslateY();
    }

    /**
     * Randomly selects an attack type for the boss.
     *
     * @return An integer representing the attack type:
     *         1 for straight, 2 for scatter, 3 for directional.
     */
    public int selectAttackType() {
        return (int) (Math.random() * 4) + 1;
    }

    /**
     * Creates a straight line of projectiles fired by the boss.
     *
     * @return A list containing a single straight projectile.
     */
    public List<ActiveActorDestructible> createStraightProjectile() {
        double projectileYPosition = getBossYPosition() + PROJECTILE_Y_POSITION_OFFSET;
        ActiveActorDestructible projectile = new BossProjectile(getBossXPosition(), projectileYPosition);

        return List.of(projectile);
    }

    public List<ActiveActorDestructible> createTwoProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double[] yOffsets = {-50, 50};

        for (double yOffset : yOffsets) {
            double projectileYPosition = getBossYPosition() + yOffset;
            BossProjectile projectile = new BossProjectile(getBossXPosition(), projectileYPosition);
            projectile.setVelocity(-4, 0);

            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Creates a scatter shot of projectiles fired by the boss.
     *
     * @return A list of projectiles spread out vertically.
     */
    public List<ActiveActorDestructible> createScatterProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double[] yOffsets = {-50, 0, 50};

        for (double yOffset : yOffsets) {
            double projectileYPosition = getBossYPosition() + yOffset;
            BossProjectile projectile = new BossProjectile(getBossXPosition(), projectileYPosition);
            projectile.setVelocity(-3, 0);

            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Creates a directional shot of projectiles fired by the boss in multiple directions.
     *
     * @return A list of projectiles fired straight, up-left, and down-left.
     */
    public List<ActiveActorDestructible> createDirectionalProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();

        double straightY = getBossYPosition() + PROJECTILE_Y_POSITION_OFFSET;
        double leftUpY = straightY - 50;
        double leftDownY = straightY + 50;

        BossProjectile straightProjectile = new BossProjectile(getBossXPosition(), straightY);
        straightProjectile.setVelocity(-2, 0);
        projectiles.add(straightProjectile);

        BossProjectile leftUpProjectile = new BossProjectile(getBossXPosition(), leftUpY);
        leftUpProjectile.setVelocity(-2, -1);
        projectiles.add(leftUpProjectile);

        BossProjectile leftDownProjectile = new BossProjectile(getBossXPosition(), leftDownY);
        leftDownProjectile.setVelocity(-2, 1);
        projectiles.add(leftDownProjectile);

        return projectiles;
    }
}