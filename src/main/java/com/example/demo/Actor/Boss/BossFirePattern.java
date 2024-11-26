package com.example.demo.Actor.Boss;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Boss.MutationBoss1.MutationBossProjectile1;
import com.example.demo.Actor.Boss.MutationBoss2.MutationBossProjectile2;
import com.example.demo.Actor.Boss.MutationBoss3.MutationBossProjectile3;
import com.example.demo.Actor.Boss.ParentBoss.Boss;
import com.example.demo.Actor.Boss.ParentBoss.BossProjectile;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code BossFirePattern} class controls the firing patterns of the boss in the game.
 * It handles the creation of projectiles for different attack types, such as straight, scatter, and directional shots.
 */
public class BossFirePattern {

    private static final double PROJECTILE_Y_POSITION_OFFSET = 30.0; // Vertical offset for projectile spawn
    private final Boss boss; // The boss object that fires projectiles

    /**
     * Constructs a {@code BossFirePattern} object that controls the firing patterns of the boss.
     *
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
     * The attack types are:
     * - 1 for straight shot
     * - 2 for scatter shot
     * - 3 for directional shot
     *
     * @return An integer representing the attack type (1, 2, or 3).
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

    /**
     * Creates two projectiles fired by the boss at slightly different vertical positions.
     *
     * @return A list containing two projectiles.
     */
    public List<ActiveActorDestructible> createTwoProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double[] yOffsets = {-50, 50};

        for (double yOffset : yOffsets) {
            double projectileYPosition = getBossYPosition() + yOffset;
            BossProjectile projectile = new MutationBossProjectile1(getBossXPosition(), projectileYPosition);
            projectile.setVelocity(-4, 0);

            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Creates a scatter shot of projectiles fired by the boss, spread out vertically.
     *
     * @return A list of projectiles spread out vertically.
     */
    public List<ActiveActorDestructible> createScatterProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();
        double[] yOffsets = {-50, 0, 50};

        for (double yOffset : yOffsets) {
            double projectileYPosition = getBossYPosition() + yOffset;
            BossProjectile projectile = new MutationBossProjectile2(getBossXPosition(), projectileYPosition);
            projectile.setVelocity(-3, 0);

            projectiles.add(projectile);
        }
        return projectiles;
    }

    /**
     * Creates a directional shot of projectiles fired by the boss in multiple directions: straight, up-left, and down-left.
     *
     * @return A list of projectiles fired in three directions.
     */
    public List<ActiveActorDestructible> createDirectionalProjectiles() {
        List<ActiveActorDestructible> projectiles = new ArrayList<>();

        double straightY = getBossYPosition() + PROJECTILE_Y_POSITION_OFFSET;
        double leftUpY = straightY - 50;
        double leftDownY = straightY + 50;

        BossProjectile straightProjectile = new MutationBossProjectile3(getBossXPosition(), straightY);
        straightProjectile.setVelocity(-4, 0);
        projectiles.add(straightProjectile);

        BossProjectile leftUpProjectile = new MutationBossProjectile3(getBossXPosition(), leftUpY);
        leftUpProjectile.setVelocity(-2.5, -2);
        projectiles.add(leftUpProjectile);

        BossProjectile leftDownProjectile = new MutationBossProjectile3(getBossXPosition(), leftDownY);
        leftDownProjectile.setVelocity(-2.5, 2);
        projectiles.add(leftDownProjectile);

        return projectiles;
    }
}